package com.tharani.githubingestorservice.repository;

import com.tharani.githubingestorservice.repository.model.JpaDeveloperCommit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DeveloperCommitRepository extends JpaRepository<JpaDeveloperCommit, Long> {

    public List<JpaDeveloperCommit> findAllByCommitHashIn(Collection<String> commitHash);
}
