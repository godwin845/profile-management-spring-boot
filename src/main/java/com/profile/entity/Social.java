package com.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String platform; // social name like "LinkedIn"

    @Column(nullable = false)
    private String link; // profile link

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
