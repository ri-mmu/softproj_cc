package com.example.softproj_cc.entity;

import jakarta.persistence.*;
import lombok.*;

// 3. Interest
@Entity
@Table(name = "interest")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "case_name")
    private String caseName;
}
