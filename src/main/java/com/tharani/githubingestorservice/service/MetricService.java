package com.tharani.githubingestorservice.service;


import com.tharani.githubingestorservice.dto.*;
import com.tharani.githubingestorservice.repository.*;
import com.tharani.githubingestorservice.repository.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "[MetricService]")
public class MetricService {
    private final DeveloperIssuesRepository developerIssuesRepository;
    private final DeveloperCommitRepository developerCommitRepository;
    private final PullRequestRepository pullRequestRepository;
    private final PullRequestReviewRepository pullRequestReviewRepository;
    private final DeveloperRepository developerRepository;
    public AddDeveloperResponse addDeveloper(AddDeveloperRequest request) {
        val developer = developerRepository.findByGithubUserName(request.getGitHubUserName());
        if (developer != null) {
            log.error("Developer already exist with github user name : {}", request.getGitHubUserName());
            return AddDeveloperResponse.builder()
                    .status(false)
                    .message("Developer already exist")
                    .build();
        }
        JpaDeveloper jpaDeveloper = JpaDeveloper.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .githubUserName(request.getGitHubUserName().toLowerCase())
                .build();
        developerRepository.save(jpaDeveloper);
        return AddDeveloperResponse.builder()
                .status(true)
                .message("Developer added succesfully")
                .build();
    }

    public ListDeveloperResponse listDevelopers() {
        val developers = developerRepository.findAll();
        return mapToListDeveloperResponse(developers);
    }

    public DeveloperCommitsResponse getDeveloperCommits() {
        Map<JpaDeveloper, List<JpaDeveloperCommit>> developerToCommitMap = developerCommitRepository.findAll()
                .stream()
                .filter(dc -> dc.getJpaDeveloper() != null)
                .collect(Collectors.groupingBy(JpaDeveloperCommit::getJpaDeveloper, Collectors.toList()));
        val developerCommits = developerToCommitMap.entrySet()
                .stream()
                .map(entry -> DeveloperCommitsResponse.DeveloperCommit.builder()
                        .firstName(entry.getKey().getFirstName())
                        .lastName(entry.getKey().getLastName())
                        .gitHubUserName(entry.getKey().getGithubUserName())
                        .noOfCommits(entry.getValue().size())
                        .commits(entry.getValue().stream()
                                .map(commit -> DeveloperCommitsResponse.DeveloperCommit.Commit.builder()
                                        .url(commit.getUrl())
                                        .hash(commit.getCommitHash())
                                        .build()
                                ).collect(Collectors.toList())
                        ).build()
                ).toList();
        return DeveloperCommitsResponse.builder()
                .status(true)
                .developerCommitList(developerCommits)
                .build();

    }

    public DeveloperIssuesResponse getDeveloperIssues() {
        Map<JpaDeveloper, List<JpaDeveloperIssue>> developerToIssueMap = developerIssuesRepository.findAll()
                .stream()
                .filter(dc -> dc.getJpaDeveloper() != null)
                .collect(Collectors.groupingBy(JpaDeveloperIssue::getJpaDeveloper, Collectors.toList()));
        val developerIssues = developerToIssueMap.entrySet()
                .stream()
                .map(entry -> DeveloperIssuesResponse.DeveloperIssue.builder()
                        .firstName(entry.getKey().getFirstName())
                        .lastName(entry.getKey().getLastName())
                        .gitHubUserName(entry.getKey().getGithubUserName())
                        .noOfIssues(entry.getValue().size())
                        .issues(entry.getValue().stream()
                                .map(issue -> DeveloperIssuesResponse.DeveloperIssue.Issue.builder()
                                        .url(issue.getUrl())
                                        .state(issue.getState())
                                        .build()
                                ).collect(Collectors.toList())
                        ).build()
                ).toList();
        return DeveloperIssuesResponse.builder()
                .status(true)
                .developerIssues(developerIssues)
                .build();
    }

    public DeveloperPullRequestsResponse getDeveloperPullRequests() {
        Map<JpaDeveloper, List<JpaPullRequest>> authorToPullRequestMap = pullRequestRepository.findAll()
                .stream()
                .filter(dc -> dc.getAuthor() != null)
                .collect(Collectors.groupingBy(JpaPullRequest::getAuthor, Collectors.toList()));
        val developerPullRequests = authorToPullRequestMap.entrySet()
                .stream()
                .map(entry -> DeveloperPullRequestsResponse.DeveloperPullRequest.builder()
                        .firstName(entry.getKey().getFirstName())
                        .lastName(entry.getKey().getLastName())
                        .gitHubUserName(entry.getKey().getGithubUserName())
                        .noOfPullRequests(entry.getValue().size())
                        .pullRequests(entry.getValue().stream()
                                .map(pr -> DeveloperPullRequestsResponse.DeveloperPullRequest.PullRequest.builder()
                                        .url(pr.getUrl())
                                        .state(pr.getState())
                                        .title(pr.getTitle())
                                        .build()
                                ).collect(Collectors.toList())
                        ).build()
                ).toList();
        return DeveloperPullRequestsResponse.builder()
                .status(true)
                .developerPullRequests(developerPullRequests)
                .build();
    }

    public DeveloperPullRequestReviewResponse getDeveloperPullRequestReviews() {
        Map<JpaDeveloper, List<JpaPullRequestReview>> reviewerToPRReviewtMap = pullRequestReviewRepository.findAll()
                .stream()
                .filter(dc -> dc.getReviewer() != null)
                .collect(Collectors.groupingBy(JpaPullRequestReview::getReviewer, Collectors.toList()));
        val reviewerToPullRequestReviews = reviewerToPRReviewtMap.entrySet()
                .stream()
                .map(entry -> DeveloperPullRequestReviewResponse.DeveloperPullRequestReview.builder()
                        .firstName(entry.getKey().getFirstName())
                        .lastName(entry.getKey().getLastName())
                        .gitHubUserName(entry.getKey().getGithubUserName())
                        .noOfReviews(entry.getValue().size())
                        .reviews(entry.getValue().stream()
                                .map(pr -> DeveloperPullRequestReviewResponse.DeveloperPullRequestReview.Review.builder()
                                        .url(pr.getUrl())
                                        .state(pr.getState())
                                        .build()
                                ).collect(Collectors.toList())
                        ).build()
                ).toList();
        return DeveloperPullRequestReviewResponse.builder()
                .status(true)
                .developerPullRequestReviews(reviewerToPullRequestReviews)
                .build();
    }

    private ListDeveloperResponse mapToListDeveloperResponse(List<JpaDeveloper> developers) {
        if (CollectionUtils.isEmpty(developers)) {
            return ListDeveloperResponse.builder()
                    .status(true)
                    .build();
        }
        val developerList = developers.stream()
                .map(d -> ListDeveloperResponse.Developer.builder()
                        .firstName(d.getFirstName())
                        .lastName(d.getLastName())
                        .gitHubUserName(d.getGithubUserName())
                        .build())
                .toList();
        return ListDeveloperResponse.builder()
                .status(true)
                .developers(developerList)
                .build();
    }

}
