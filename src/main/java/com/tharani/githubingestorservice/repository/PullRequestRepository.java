package com.tharani.githubingestorservice.repository;

import com.tharani.githubingestorservice.repository.model.JpaPullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PullRequestRepository extends JpaRepository<JpaPullRequest, Long> {

    List<JpaPullRequest> findAllByExternalIdIn(Collection<String> externalIds);
}
