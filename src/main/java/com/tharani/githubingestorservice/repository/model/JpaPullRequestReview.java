package com.tharani.githubingestorservice.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pull_request_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JpaPullRequestReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "pull_request_id",
            foreignKey = @ForeignKey(name = "fk_pull_request_in_pull_request_review")
    )
    private JpaPullRequest jpaPullRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id",
            foreignKey = @ForeignKey(name = "fk_reviewer_in_pull_request_review")
    )
    private JpaDeveloper reviewer;

    private String externalId;
    private String state;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
