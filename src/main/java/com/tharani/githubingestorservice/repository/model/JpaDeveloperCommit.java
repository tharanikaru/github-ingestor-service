package com.tharani.githubingestorservice.repository.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "developer_commit")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JpaDeveloperCommit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "developer_id",
            foreignKey = @ForeignKey(name = "fk_developer_in_developer_commits")
    )
    private JpaDeveloper jpaDeveloper;

    private String commitHash;

    private String url;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;
}
