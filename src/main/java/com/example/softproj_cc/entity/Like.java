package com.example.softproj_cc.entity;

import com.example.softproj_cc.entity.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 6. Like
@Entity
@Table(name = "likes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "receiver_id")
    private Integer receiverId;
}