package com.example.softproj_cc.entity;

import com.example.softproj_cc.entity.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// 1. User
@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @JsonIgnore // ✅ 클라이언트로 응답 시 이 필드는 포함되지 않음
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username; // 로그인에 사용될 ID

    @Column(nullable = false)
    private String email; // 이메일 인증, 아이디 찾기에 사용

    @JsonIgnore // ✅ 클라이언트로 응답 시 이 필드는 포함되지 않음
    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder.Default
    private Boolean isVerified = false;


    @JsonIgnore // ✅ 클라이언트로 응답 시 이 필드는 포함되지 않음
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @JsonIgnore // ✅ 클라이언트로 응답 시 이 필드는 포함되지 않음
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive;

    public void setVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
}
