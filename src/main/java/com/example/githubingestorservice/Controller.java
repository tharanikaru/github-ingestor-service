package com.example.githubingestorservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/welcome12")
    public String welcome() {
        return "welcome";
    }
}
