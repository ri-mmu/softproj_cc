package com.example.softproj_cc.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 클라이언트가 "/app/chat" 경로로 메시지를 보내면
    @MessageMapping("/chat")
    public void sendMessage(String message) {
        // 클라이언트에게 "/topic/messages"로 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages", HtmlUtils.htmlEscape(message));
    }
}
