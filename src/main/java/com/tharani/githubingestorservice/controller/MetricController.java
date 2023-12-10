package com.tharani.githubingestorservice.controller;

import com.tharani.githubingestorservice.dto.AddDeveloperRequest;
import com.tharani.githubingestorservice.dto.AddDeveloperResponse;
import com.tharani.githubingestorservice.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/metric")
public class MetricController {

    private final MetricService metricService;

    @PostMapping("/addDeveloper")
    public AddDeveloperResponse addDeveloper(@RequestBody AddDeveloperRequest request) {
        return metricService.addDeveloper(request);
    }
}
