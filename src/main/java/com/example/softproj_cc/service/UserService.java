package com.example.softproj_cc.service;

import com.example.softproj_cc.dto.PasswordChangeRequest;
import com.example.softproj_cc.dto.UserSignupRequest;
import com.example.softproj_cc.dto.UserProfileRequest;
import com.example.softproj_cc.entity.User;
import com.example.softproj_cc.entity.enums.Gender;
import com.example.softproj_cc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;

    // ✅ 회원가입
    public void signup(UserSignupRequest request) {
        if (!emailVerificationService.isEmailVerified(request.getEmail())) {
            throw new IllegalArgumentException("이메일 인증을 먼저 완료해주세요.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .gender(Gender.valueOf(request.getGender()))
                .isVerified(true) // 이미 인증된 상태로 회원가입
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        emailVerificationService.clearCode(request.getEmail());
    }


    // ✅ 로그인
    public User authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .filter(User::getIsVerified) // ✅ 인증된 사용자만 통과
                .orElse(null);
    }

    // ✅ username으로 유저 찾기
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

    // ✅ username으로 프로필 수정
    public void updateMyProfile(String username, UserProfileRequest request) {
        User user = findByUsername(username);

        if (request.getGender() != null) {
            user.setGender(Gender.valueOf(request.getGender()));
        }

        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    // ✅ 이메일 인증용 이메일 저장
    public void save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean changePassword(String username, PasswordChangeRequest request) {
        User user = findByUsername(username);

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

}
