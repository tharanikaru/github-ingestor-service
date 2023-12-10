package com.tharani.githubingestorservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tharani.githubingestorservice.dto.CommitDTO;
import com.tharani.githubingestorservice.dto.IssueDTO;
import com.tharani.githubingestorservice.dto.PullRequestReviewDTO;
import com.tharani.githubingestorservice.dto.PullRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@Slf4j(topic = "[GitHubRestClient]")
public class GitHubRestClient {

    private final String API_END_POINT = "https://api.github.com";

    @Value("${github.token}")
    private String gitHubToken;

    @Value("${github.owner}")
    private String repoOwner;

    @Value("${github.repository}")
    private String repository;

    public List<CommitDTO> fetchCommits() {
        try {
            URI uri = URI.create(API_END_POINT + "/repos/" + repoOwner + "/" + repository + "/commits");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", "Bearer " + gitHubToken)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapToCommitDto(response);
        } catch (Exception e) {
            log.error("Error occurred while fetching commits: ", e);
            throw new RuntimeException("Failed to get commits");
        }
    }

    public List<IssueDTO> fetchIssues() {
        try {
            URI uri = URI.create(API_END_POINT + "/repos/" + repoOwner + "/" + repository + "/issues");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", "Bearer " + gitHubToken)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapToIssuesDto(response);
        } catch (Exception e) {
            log.error("Error occurred while fetching issues: ", e);
            throw new RuntimeException("Failed to get issues");
        }
    }

    public List<PullRequestDTO> fetchPullRequests() {
        try {
            URI uri = URI.create(API_END_POINT + "/repos/" + repoOwner + "/" + repository + "/pulls?state=all");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", "Bearer " + gitHubToken)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapToPullRequestDTO(response);
        } catch (Exception e) {
            log.error("Error occurred while fetching issues: ", e);
            throw new RuntimeException("Failed to get issues");
        }
    }

    public List<PullRequestReviewDTO> fetchPRReviews(String pullNumber) {
        try {
            URI uri = URI.create(API_END_POINT + "/repos/" + repoOwner + "/" + repository + "/pulls/" + pullNumber + "/reviews");
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Authorization", "Bearer " + gitHubToken)
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return mapToPRReviewDTO(response);
        } catch (Exception e) {
            log.error("Error occurred while fetching issues: ", e);
            throw new RuntimeException("Failed to get issues");
        }
    }

    private List<CommitDTO> mapToCommitDto(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    private List<IssueDTO> mapToIssuesDto(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    private List<PullRequestDTO> mapToPullRequestDTO(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    private List<PullRequestReviewDTO> mapToPRReviewDTO(HttpResponse<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

}
