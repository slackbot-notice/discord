package com.cake0420.slackbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        log.info("health");
        return ResponseEntity.ok().build();
    }
}
