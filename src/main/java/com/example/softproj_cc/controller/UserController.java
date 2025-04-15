package com.example.softproj_cc.controller;

import com.example.softproj_cc.dto.LoginRequest;
import com.example.softproj_cc.dto.PasswordChangeRequest;
import com.example.softproj_cc.dto.UserProfileRequest;
import com.example.softproj_cc.dto.UserSignupRequest;
import com.example.softproj_cc.entity.User;
import com.example.softproj_cc.service.EmailVerificationService;
import com.example.softproj_cc.service.TokenService;
import com.example.softproj_cc.service.UserService;
import com.example.softproj_cc.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    // ✅ 회원가입: username, password, email, gender
    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        if (!emailVerificationService.isEmailVerified(request.getEmail())) {
            throw new IllegalArgumentException("이메일 인증을 먼저 완료해주세요.");
        }
        return ResponseEntity.ok("회원가입 성공!");
    }

    // ✅ 이메일 인증번호 요청
    @PostMapping("/email/verify-request")
    public ResponseEntity<?> sendVerification(@RequestParam String email) {
        if (!email.endsWith("@soongsil.ac.kr")) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "숭실대 이메일만 인증할 수 있습니다."));
        }

        emailVerificationService.generateAndSendCode(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "인증번호가 전송되었습니다."));
    }



    // ✅ 인증번호 확인
    @PostMapping("/email/verify-check")
    public ResponseEntity<?> checkVerification(@RequestParam String email,
                                               @RequestParam String code) {
        if (!emailVerificationService.verifyCode(email, code)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("인증번호가 올바르지 않습니다.");
        }

        return ResponseEntity.ok("이메일 인증 완료!");
    }


    // ✅ 로그인: username + password 기반
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getUsername(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "아이디 또는 비밀번호가 올바르지 않습니다."));
        }

        // ✅ 이메일 인증 여부 추가 확인
        if (!user.getIsVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "이메일 인증을 완료한 사용자만 로그인할 수 있습니다."));
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


    // ✅ 내 프로필 조회 (토큰 기반)
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@RequestParam String token) {
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String username = jwtUtil.extractUsername(token);
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    // ✅ 내 프로필 수정
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestParam String token,
                                             @RequestBody UserProfileRequest request) {
        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String username = tokenService.getUsernameByToken(token);
        userService.updateMyProfile(username, request);
        return ResponseEntity.ok("프로필이 수정되었습니다.");
    }

    // ✅비밀번호 변경 (기존, 새로운)
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestParam String token,
                                            @RequestBody PasswordChangeRequest request) {
        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String username = tokenService.getUsernameByToken(token);
        boolean success = userService.changePassword(username, request);

        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("기존 비밀번호가 올바르지 않습니다.");
        }

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

}
