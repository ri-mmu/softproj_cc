package com.example.softproj_cc.service;

import com.example.softproj_cc.entity.ChatMessage;
import com.example.softproj_cc.entity.ChatRoom;
import com.example.softproj_cc.entity.Match;
import com.example.softproj_cc.entity.User;
import com.example.softproj_cc.repository.ChatRoomRepository;
import com.example.softproj_cc.repository.ChatMessageRepository;
import com.example.softproj_cc.repository.MatchRepository;
import com.example.softproj_cc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    // 채팅방 생성 (매칭된 유저의 채팅방)
    public void createChatRoom(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("매칭된 데이터가 없습니다."));

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setMatch(match);  // Match 엔티티를 설정
        chatRoom.setCreatedAt(LocalDateTime.now());
        chatRoom.setUpdatedAt(LocalDateTime.now());
        chatRoomRepository.save(chatRoom);
    }

    // 메시지 전송 (chatId, senderId, content)
    public void sendMessage(Integer chatId, Integer senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        // senderId를 통해 User 객체를 조회
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        ChatMessage message = new ChatMessage();
        message.setChatRoom(chatRoom);  // ChatRoom과 연결
        message.setSender(sender);  // User와 연결
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setIsRead(false);  // 기본값은 '읽지 않음'
        chatMessageRepository.save(message);
    }
}
