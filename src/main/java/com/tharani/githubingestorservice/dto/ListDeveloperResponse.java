package com.tharani.githubingestorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDeveloperResponse {
    private boolean status;
    private List<Developer> developers;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Developer {
        private String firstName;
        private String lastName;
        private String gitHubUserName;
    }
}
