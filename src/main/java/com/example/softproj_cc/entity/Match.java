package com.example.softproj_cc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 5. Match
@Entity
@Table(name = "matches")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "matched_at")
    private LocalDateTime matchedAt;
}