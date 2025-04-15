package com.example.softproj_cc.controller;

import com.example.softproj_cc.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 생성 API
    @PostMapping("/room/{matchId}")
    public ResponseEntity<String> createChatRoom(@PathVariable Integer matchId) {
        chatRoomService.createChatRoom(matchId);
        return ResponseEntity.ok("채팅방 생성 성공");
    }

    // 메시지 전송 API
    @PostMapping("/message/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam Integer chatId,
            @RequestParam Integer senderId,
            @RequestParam String content) {
        chatRoomService.sendMessage(chatId, senderId, content);
        return ResponseEntity.ok("메시지 전송 성공");
    }
}
