package com.example.githubingestorservice.repository;

import com.example.githubingestorservice.repository.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
