package com.example.softproj_cc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;  // ChatRoom과의 관계

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;  // User와의 관계

    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "is_read")
    private Boolean isRead;
}
