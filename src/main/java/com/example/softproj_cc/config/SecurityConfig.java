package com.example.softproj_cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // API는 인증 없이 허용
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .formLogin(Customizer.withDefaults()); // 기본 로그인 폼 사용

        return http.build();
    }
}
