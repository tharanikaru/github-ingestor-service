package com.tharani.githubingestorservice.repository;

import com.tharani.githubingestorservice.repository.model.JpaDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<JpaDeveloper, Long> {

    List<JpaDeveloper> findAllByGithubUserNameIn (Collection<String> githubUserName);

    JpaDeveloper findByGithubUserName(String userName);

}
