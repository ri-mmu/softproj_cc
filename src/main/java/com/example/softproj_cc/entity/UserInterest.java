package com.example.softproj_cc.entity;

import jakarta.persistence.*;
import lombok.*;

// 4. UserInterest (중간 테이블)
@Entity
@Table(name = "user_interest")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInterest {
    @EmbeddedId
    private UserInterestId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("interestId")
    @JoinColumn(name = "interest_id")
    private Interest interest;
}
