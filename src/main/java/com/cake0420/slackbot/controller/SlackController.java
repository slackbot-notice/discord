package com.cake0420.slackbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/slack")
public class SlackController {

    private final static String DISCORD_WEBHOOK = "https://discord.com/api/webhooks/1412267202014482452/-GYZPWGGtQMEX4y8d4kCTMtGwcOkLMyTz8tPKb4o2iJ9ZhYoXIsPmJVRxhIy__5khFL1";

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/events")
    public ResponseEntity<?> handleSlackEvent(@RequestBody SlackEventPayload payload) {

        // 1. URL verification 처리
        if ("url_verification".equals(payload.type())) {
            return ResponseEntity.ok(payload.challenge());
        }

        // 2. 메시지 이벤트 처리
        SlackEvent event = payload.event();
        if (event != null && "message".equals(event.type()) && event.bot_id() == null) {
            String text = event.text();

            // Discord 전송
            Map<String, String> discordPayload = Map.of("content", "📢 Slack 메시지: " + text);
            log.info("discordPayload: {}", discordPayload);
            log.info("event id", event.channel);
            log.info("event team", event.team);
            restTemplate.postForEntity(DISCORD_WEBHOOK, discordPayload, String.class);
        }

        return ResponseEntity.ok("OK");
    }

    // Slack 이벤트 페이로드 전체
    public record SlackEventPayload(
            String type,
            String challenge,
            SlackEvent event
    ) {}

    // 실제 메시지 이벤트 부분
    public record SlackEvent(
            String type,
            String text,
            String bot_id,
            String team,      // team_id 추가
            String channel    // channel_id 추가
    ) {}

}



