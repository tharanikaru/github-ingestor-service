package com.tharani.githubingestorservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestReviewDTO {
    private String id;
    private String state;

    @JsonProperty("user")
    private Reviewer reviewer;

    @JsonProperty("html_url")
    private String url;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reviewer {
        @JsonProperty("login")
        private String name;
    }

}
