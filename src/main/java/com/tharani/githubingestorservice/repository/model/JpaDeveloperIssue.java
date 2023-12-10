package com.tharani.githubingestorservice.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "developer_issue")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JpaDeveloperIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id",
            foreignKey = @ForeignKey(name = "fk_developer_in_developer_issues")
    )
    private JpaDeveloper jpaDeveloper;

    private String externalId;

    private String title;

    private String url;

    private String state;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;
}
