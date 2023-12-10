package com.tharani.githubingestorservice.repository;

import com.tharani.githubingestorservice.repository.model.JpaDeveloperIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DeveloperIssuesRepository extends JpaRepository<JpaDeveloperIssue, Long> {

    List<JpaDeveloperIssue> findAllByExternalIdIn(Collection<String> externalIds);
}
