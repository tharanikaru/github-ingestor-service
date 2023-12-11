package com.tharani.githubingestorservice.service;


import com.tharani.githubingestorservice.client.GitHubRestClient;
import com.tharani.githubingestorservice.dto.*;
import com.tharani.githubingestorservice.repository.*;
import com.tharani.githubingestorservice.repository.model.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubIntegrationService {

    private final GitHubRestClient gitHubRestClient;
    private final DeveloperCommitRepository developerCommitRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperIssuesRepository developerIssuesRepository;
    private final PullRequestRepository pullRequestRepository;
    private final PullRequestReviewRepository pullRequestReviewRepository;


    @Transactional
    public TaskResponse fetchAndUpdateCommits() {
        List<CommitDTO> commits = gitHubRestClient.fetchCommits();

        if (CollectionUtils.isEmpty(commits)) {
            return TaskResponse.builder()
                    .status(true)
                    .message("No commits found to update")
                    .build();
        }

        Map<String, JpaDeveloperCommit> hashIdToCommitMap = developerCommitRepository.findAllByCommitHashIn(commitHashes(commits))
                .stream()
                .collect(Collectors.toMap(JpaDeveloperCommit::getCommitHash, Function.identity()));
        Map<String, JpaDeveloper> userNameToDeveloperMap = developerRepository.findAllByGithubUserNameIn(githubUsersFromCommits(commits))
                .stream()
                .collect(Collectors.toMap(JpaDeveloper::getGithubUserName, Function.identity()));

        List<JpaDeveloperCommit> developerCommits = commits.stream()
                .map(commitDTO -> createOrUpdateCommit(userNameToDeveloperMap, hashIdToCommitMap, commitDTO))
                .collect(Collectors.toList());

        developerCommitRepository.saveAll(developerCommits);
        return TaskResponse.builder()
                .status(true)
                .message("Successfully updated commits.")
                .build();
    }

    private JpaDeveloperCommit createOrUpdateCommit(Map<String, JpaDeveloper> userNameToDeveloperMap,
                                                    Map<String, JpaDeveloperCommit> hashIdToCommitMap,
                                                    CommitDTO commitDTO) {
        val jpaCommit = hashIdToCommitMap.getOrDefault(commitDTO.getCommitHash(), new JpaDeveloperCommit());
        jpaCommit.setCommitHash(commitDTO.getCommitHash());
        jpaCommit.setJpaDeveloper(userNameToDeveloperMap.get(commitDTO.getCommitDetails().getCommitter().getUserName().toLowerCase()));
        jpaCommit.setUrl(commitDTO.getUrl());
        jpaCommit.setUpdatedAt(LocalDateTime.now());
        if (jpaCommit.getId() == null) {
            jpaCommit.setCreatedAt(LocalDateTime.now());
        }
        return jpaCommit;

    }

    private List<String> commitHashes(List<CommitDTO> commits) {
        return commits.stream()
                .map(CommitDTO::getCommitHash)
                .collect(Collectors.toList());
    }

    private List<String> githubUsersFromCommits(List<CommitDTO> commits) {
        return commits.stream()
                .filter(c -> c.getCommitDetails() != null && c.getCommitDetails().getCommitter() != null && c.getCommitDetails().getCommitter().getUserName() != null)
                .map(c -> c.getCommitDetails().getCommitter().getUserName().toLowerCase())
                .collect(Collectors.toList());
    }

    public TaskResponse fetchAndUpdateIssues() {
        List<IssueDTO> issues = gitHubRestClient.fetchIssues();
        if (CollectionUtils.isEmpty(issues)) {
            return TaskResponse.builder()
                    .status(true)
                    .message("No issues found to update")
                    .build();
        }

        Map<String, JpaDeveloperIssue> externalIdToIssueMap = developerIssuesRepository.findAllByExternalIdIn(issueExternalIds(issues))
                .stream()
                .collect(Collectors.toMap(JpaDeveloperIssue::getExternalId, Function.identity()));
        Map<String, JpaDeveloper> userNameToDeveloperMap = developerRepository.findAllByGithubUserNameIn(githubUsersFromIssues(issues))
                .stream()
                .collect(Collectors.toMap(JpaDeveloper::getGithubUserName, Function.identity()));

        List<JpaDeveloperIssue> developerIssues = issues.stream()
                .map(commitDTO -> createOrUpdateIssue(userNameToDeveloperMap, externalIdToIssueMap, commitDTO))
                .collect(Collectors.toList());

        developerIssuesRepository.saveAll(developerIssues);
        return TaskResponse.builder()
                .status(true)
                .message("Successfully updated issues.")
                .build();

    }

    private List<String> issueExternalIds(List<IssueDTO> issues) {
        return issues.stream()
                .map(IssueDTO::getId)
                .collect(Collectors.toList());
    }

    private List<String> githubUsersFromIssues(List<IssueDTO> issues) {
        return issues.stream()
                .filter(issue -> issue.getAssignee() != null && issue.getAssignee().getName() != null)
                .map(issue -> issue.getAssignee().getName().toLowerCase())
                .collect(Collectors.toList());
    }

    private JpaDeveloperIssue createOrUpdateIssue(Map<String, JpaDeveloper> userNameToDeveloperMap,
                                                  Map<String, JpaDeveloperIssue> idToIssueMap,
                                                  IssueDTO issueDTO) {
        val jpaIssue = idToIssueMap.getOrDefault(issueDTO.getId(), new JpaDeveloperIssue());
        jpaIssue.setTitle(issueDTO.getTitle());
        if (issueDTO.getAssignee() != null && issueDTO.getAssignee().getName() != null) {
            jpaIssue.setJpaDeveloper(userNameToDeveloperMap.get(issueDTO.getAssignee().getName().toLowerCase()));
        }
        jpaIssue.setUrl(issueDTO.getUrl());
        jpaIssue.setState(issueDTO.getState());
        jpaIssue.setExternalId(issueDTO.getId());
        jpaIssue.setUpdatedAt(LocalDateTime.now());
        if (jpaIssue.getId() == null) {
            jpaIssue.setCreatedAt(LocalDateTime.now());
        }
        return jpaIssue;
    }

    private JpaPullRequest createOrUpdatePR(Map<String, JpaDeveloper> userNameToDeveloperMap,
                                            Map<String, JpaPullRequest> idToPRMap,
                                            PullRequestDTO pullRequestDTO) {
        val jpaPullRequest = idToPRMap.getOrDefault(pullRequestDTO.getId(), new JpaPullRequest());
        jpaPullRequest.setTitle(pullRequestDTO.getTitle());
        jpaPullRequest.setAuthor(userNameToDeveloperMap.get(pullRequestDTO.getAuthor().getName().toLowerCase()));
        jpaPullRequest.setUrl(pullRequestDTO.getUrl());
        jpaPullRequest.setNumber(pullRequestDTO.getNumber());
        jpaPullRequest.setState(pullRequestDTO.getState());
        jpaPullRequest.setExternalId(pullRequestDTO.getId());
        jpaPullRequest.setUpdatedAt(LocalDateTime.now());
        if (jpaPullRequest.getId() == null) {
            jpaPullRequest.setCreatedAt(LocalDateTime.now());
        }
        return jpaPullRequest;
    }

    public TaskResponse fetchAndUpdatePullRequests() {
        val pullRequests = gitHubRestClient.fetchPullRequests();

        Map<String, JpaPullRequest> externalIdToPRMap = pullRequestRepository.findAllByExternalIdIn(pullRequestExternalIds(pullRequests))
                .stream()
                .collect(Collectors.toMap(JpaPullRequest::getExternalId, Function.identity()));
        Map<String, JpaDeveloper> userNameToDeveloperMap = developerRepository.findAllByGithubUserNameIn(githubUsersFromPRs(pullRequests))
                .stream()
                .collect(Collectors.toMap(JpaDeveloper::getGithubUserName, Function.identity()));

        List<JpaPullRequest> jpaPullRequests = pullRequests.stream()
                .map(pullRequestDTO -> createOrUpdatePR(userNameToDeveloperMap, externalIdToPRMap, pullRequestDTO))
                .collect(Collectors.toList());

        pullRequestRepository.saveAll(jpaPullRequests);

        return TaskResponse.builder()
                .status(true)
                .message("Successfully updated pull requests and reviews.")
                .build();
    }

    public TaskResponse fetchAndUpdatePRReviews() {
        fetchAndUpdatePullRequests();

        val externalIdToPullRequestMap = pullRequestRepository.findAll()
                .stream()
                .collect(Collectors.toMap(JpaPullRequest::getExternalId, Function.identity()));

        Map<String, JpaDeveloper> userNameToDeveloperMap = developerRepository.findAll()
                .stream()
                .collect(Collectors.toMap(JpaDeveloper::getGithubUserName, Function.identity()));

        Map<JpaPullRequest, List<PullRequestReviewDTO>> allReviews = new HashMap<>();
        for (JpaPullRequest jpaPullRequest : externalIdToPullRequestMap.values()) {
            val reviewsForPR = allReviews.getOrDefault(jpaPullRequest, new ArrayList<>());
            reviewsForPR.addAll(gitHubRestClient.fetchPRReviews(jpaPullRequest.getNumber()));
            allReviews.put(jpaPullRequest, reviewsForPR);
        }

        List<JpaPullRequestReview> jpaPullRequestReviews = new ArrayList<>();
        for (JpaPullRequest jpaPullRequest : allReviews.keySet()) {
            for (PullRequestReviewDTO prReview : allReviews.get(jpaPullRequest)) {
                if (!pullRequestReviewRepository.existsByExternalId(prReview.getId())) {
                    val jpaPRReview = JpaPullRequestReview.builder()
                            .externalId(prReview.getId())
                            .state(prReview.getState())
                            .url(prReview.getUrl())
                            .jpaPullRequest(jpaPullRequest)
                            .reviewer(userNameToDeveloperMap.get(prReview.getReviewer().getName().toLowerCase()))
                            .build();
                    jpaPullRequestReviews.add(jpaPRReview);
                }
            }
        }

        pullRequestReviewRepository.saveAll(jpaPullRequestReviews);

        return TaskResponse.builder()
                .status(true)
                .message("Successfully updated pull request reviews.")
                .build();
    }


    private List<String> pullRequestExternalIds(List<PullRequestDTO> pullRequests) {
        return pullRequests.stream()
                .map(PullRequestDTO::getId)
                .collect(Collectors.toList());
    }

    private List<String> githubUsersFromPRs(List<PullRequestDTO> pullRequests) {
        return pullRequests.stream()
                .map(pullRequest -> pullRequest.getAuthor().getName().toLowerCase())
                .collect(Collectors.toList());
    }
}
