package com.tharani.githubingestorservice.controller;

import com.tharani.githubingestorservice.dto.AddDeveloperRequest;
import com.tharani.githubingestorservice.dto.AddDeveloperResponse;
import com.tharani.githubingestorservice.service.MetricService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "[MetricController]")
@RequestMapping("/api/v1/metric")
public class MetricController {

    private final MetricService metricService;

    @PostMapping("/addDeveloper")
    public AddDeveloperResponse addDeveloper(@RequestBody AddDeveloperRequest request) {
        log.info("request received : {}", request);
        return metricService.addDeveloper(request);
    }
}
