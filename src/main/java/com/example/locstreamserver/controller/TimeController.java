package com.example.locstreamserver.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;

@RestController
public class TimeController {
    @GetMapping("/time")
    public String getCurrentTime() {
        System.out.println("Sending time to locator per request: " + Instant.now().toString());
        return Instant.now().toString(); // ISO 8601 format

    }
}

