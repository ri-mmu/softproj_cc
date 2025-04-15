package com.example.softproj_cc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic"으로 시작하는 주소를 메시지 브로커로 보내도록 설정
        config.enableSimpleBroker("/topic");
        // 클라이언트에게 "/app"으로 메시지를 보낼 수 있도록 설정
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // "/chat" 엔드포인트로 WebSocket 연결을 활성화
        registry.addEndpoint("/chat").withSockJS();
    }
}
