package com.tharani.githubingestorservice.controller;

import com.tharani.githubingestorservice.dto.*;
import com.tharani.githubingestorservice.service.MetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "[MetricController]")
@RequestMapping("/api/v1/metric")
public class MetricController {

    private final MetricService metricService;

    @PostMapping("/addDeveloper")
    public AddDeveloperResponse addDeveloper(@RequestBody AddDeveloperRequest request) {
        log.info("request received : {}", request);
        return metricService.addDeveloper(request);
    }

    @GetMapping("/listDevelopers")
    public ListDeveloperResponse addDeveloper() {
        return metricService.listDevelopers();
    }

    @GetMapping("/developer-commits")
    public DeveloperCommitsResponse getDeveloperCommits() {
        return metricService.getDeveloperCommits();
    }

    @GetMapping("/developer-issues")
    public DeveloperIssuesResponse getDeveloperIssues() {
        return metricService.getDeveloperIssues();
    }

    @GetMapping("/developer-pull-requests")
    public DeveloperPullRequestsResponse getDeveloperPullRequests() {
        return metricService.getDeveloperPullRequests();
    }

    @GetMapping("/developer-pull-request-reviews")
    public DeveloperPullRequestReviewResponse getDeveloperPullRequestReviews() {
        return metricService.getDeveloperPullRequestReviews();
    }

}
