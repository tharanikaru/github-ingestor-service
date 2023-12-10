package com.tharani.githubingestorservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDTO {

    @JsonProperty("sha")
    private String commitHash;

    @JsonProperty("html_url")
    private String url;

    @JsonProperty("commit")
    private CommitDetails commitDetails;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitDetails {

        @JsonProperty("message")
        private String message;

        @JsonProperty("committer")
        private Committer committer;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Committer {
            @JsonProperty("name")
            private String userName;

        }
    }
}
