package com.example.softproj_cc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserSignupRequest {
    private String username;         // ✅ 아이디 (로그인용)
    private String password;         // 비밀번호
    private String email;
    private String gender;           // 성별

}