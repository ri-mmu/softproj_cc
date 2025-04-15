package com.example.softproj_cc.entity;

import jakarta.persistence.*;
import lombok.*;

// 2. UserProfile
@Entity
@Table(name = "user_profile")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    private Integer id; // = user_id

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;
    private Integer age;
    private String bio;
    private String department;

    @Column(name = "picture_url")
    private String pictureUrl;
}
