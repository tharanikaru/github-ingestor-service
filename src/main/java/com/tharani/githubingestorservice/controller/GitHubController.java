package com.tharani.githubingestorservice.controller;

import com.tharani.githubingestorservice.dto.TaskResponse;
import com.tharani.githubingestorservice.service.GitHubIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/git")
public class GitHubController {

    private final GitHubIntegrationService gitHubIntegrationService;

    @GetMapping("/commits")
    public TaskResponse fetchCommits() {
        return gitHubIntegrationService.fetchAndUpdateCommits();
    }

    @GetMapping("/issues")
    public TaskResponse fetchIssues() {
        return gitHubIntegrationService.fetchAndUpdateIssues();
    }

    @GetMapping("/pull-requests")
    public TaskResponse fetchPullRequests() {
        return gitHubIntegrationService.fetchAndUpdatePullRequests();
    }

    @GetMapping("/pull-request-reviews")
    public TaskResponse fetchPullRequestReviews() {
        return gitHubIntegrationService.fetchAndUpdatePRReviews();
    }
}
