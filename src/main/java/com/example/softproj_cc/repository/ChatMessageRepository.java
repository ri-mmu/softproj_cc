package com.example.softproj_cc.repository;

import com.example.softproj_cc.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}
