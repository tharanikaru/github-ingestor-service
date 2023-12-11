package com.tharani.githubingestorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperCommitsResponse {
    private boolean status;
    private List<DeveloperCommit> developerCommitList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeveloperCommit {
        private String firstName;
        private String lastName;
        private String gitHubUserName;
        private Integer noOfCommits;
        private List<Commit> commits;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Commit {
            private String url;
            private String hash;
        }
    }
}
