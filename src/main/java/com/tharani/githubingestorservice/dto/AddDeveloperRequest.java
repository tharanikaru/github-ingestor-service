package com.tharani.githubingestorservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddDeveloperRequest {
    private String firstName;
    private String lastName;
    private String gitHubUserName;
}
