package com.profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "skills", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // Link to user (assuming a User entity exists)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    // Store skills as a JSON string (MySQL 5.7+ supports JSON type) or as comma-separated
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "skill_id"))
    @Column(name = "skill")
    private List<String> skills;
    
    @ManyToOne
    private User user;
}
