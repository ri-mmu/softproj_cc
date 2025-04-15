package com.example.softproj_cc.service;

import com.example.softproj_cc.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final JwtUtil jwtUtil;


    public TokenService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getUsernameByToken(String token) {
        return jwtUtil.extractUsername(token);
    }
}
