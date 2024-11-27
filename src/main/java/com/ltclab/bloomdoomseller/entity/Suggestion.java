package com.ltclab.bloomdoomseller.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    private String subcategory;
    @Column(name = "suggestion_type")
    private String type;
    @Column(nullable = false, length = 1000, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_edited_at")
    private LocalDateTime lastEditedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
