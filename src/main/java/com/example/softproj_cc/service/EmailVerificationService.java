package com.example.softproj_cc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class EmailVerificationService {

    private final JavaMailSender mailSender;
    private final String TITLE = "[야 너도 cc할 수 있어] 이메일 인증번호입니다.";
    private final String CONTENT_TEMPLATE = "안녕하세요.\n요청하신 인증번호는 [%s] 입니다.\n3분 내에 입력해주세요.";

    private final Map<String, VerificationInfo> codeStore = new ConcurrentHashMap<>();
    private final Map<String, Boolean> verifiedStatus = new ConcurrentHashMap<>();
    private final Random random = new Random();

    // ✅ 인증번호 생성 + 전송
    public String generateAndSendCode(String email) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(3);
        codeStore.put(email, new VerificationInfo(code, expiresAt));
        verifiedStatus.put(email, false); // 초기화

        String content = String.format(CONTENT_TEMPLATE, code);
        sendMail(email, TITLE, content);
        return code;
    }

    // ✅ 인증번호 확인 + 인증 완료 처리
    public boolean verifyCode(String email, String inputCode) {
        VerificationInfo info = codeStore.get(email);
        if (info == null) return false;
        if (LocalDateTime.now().isAfter(info.expiresAt())) return false;

        boolean match = info.code().equals(inputCode);
        if (match) {
            verifiedStatus.put(email, true); // 인증 성공 표시
        }
        return match;
    }

    // ✅ 인증 여부 확인
    public boolean isEmailVerified(String email) {
        return verifiedStatus.getOrDefault(email, false);
    }

    // ✅ 인증번호 삭제
    public void clearCode(String email) {
        codeStore.remove(email);
        verifiedStatus.remove(email); // 인증 상태도 초기화
    }

    private void sendMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    private record VerificationInfo(String code, LocalDateTime expiresAt) {}
}
