package com.tharani.githubingestorservice.repository;

import com.tharani.githubingestorservice.repository.model.JpaPullRequestReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestReviewRepository extends JpaRepository<JpaPullRequestReview, Long> {

    boolean existsByExternalId(String externalId);
}
