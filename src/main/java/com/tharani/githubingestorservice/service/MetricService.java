package com.tharani.githubingestorservice.service;


import com.tharani.githubingestorservice.dto.AddDeveloperRequest;
import com.tharani.githubingestorservice.dto.AddDeveloperResponse;
import com.tharani.githubingestorservice.dto.TaskResponse;
import com.tharani.githubingestorservice.repository.DeveloperRepository;
import com.tharani.githubingestorservice.repository.model.JpaDeveloper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "[MetricService]")
public class MetricService {
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
}
