package com.tharani.githubingestorservice.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pull_requests")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JpaPullRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",
            foreignKey = @ForeignKey(name = "fk_developer_in_pull_requests")
    )
    private JpaDeveloper author;

    private String externalId;

    private String title;

    private String url;

    private String state;

    private String number;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;
}
