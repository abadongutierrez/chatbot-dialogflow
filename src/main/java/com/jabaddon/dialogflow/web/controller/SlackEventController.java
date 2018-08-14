package com.jabaddon.dialogflow.web.controller;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.Bot;
import allbegray.slack.webapi.SlackWebApiClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jabaddon.dialogflow.service.CacheService;
import com.jabaddon.dialogflow.service.DbService;
import com.jabaddon.dialogflow.service.SlackInstallation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class SlackEventController {

    @Autowired
    private DbService dbService;

    @Autowired
    private CacheService cacheService;

    @PostMapping(value = "/action-endpoint", produces = MediaType.TEXT_PLAIN_VALUE)
    public String slack(@RequestBody String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        JsonNode challengeNode = root.path("challenge");
        JsonNode typeNode = root.path("type");
        if (challengeNode != null && typeNode != null && "url_verification".equals(typeNode.asText())) {
            return challengeNode.asText();
        }

        JsonNode eventNode = root.get("event");
        if (eventNode != null) {
            Optional<SlackInstallation> optionalSlackInstallation =
                    dbService.getSlackInstallation(root.path("team_id").asText());
            optionalSlackInstallation.ifPresent(slackInstallation -> {
                SlackWebApiClient webApiClient =
                        SlackClientFactory.createWebApiClient(slackInstallation.getBotAccessToken());

                String channelId = eventNode.path("channel").asText();
                JsonNode botIdNode = eventNode.get("bot_id");
                if (botIdNode != null) {
                    String botId = botIdNode.asText();
                    Bot bot = cacheService.getBotInfo(botId, () -> webApiClient.getBotInfo(botId));
                    if (!slackInstallation.getBotUserId().equals(bot.getUser_id())) {
                        webApiClient.postMessage(channelId, "Hi there!");
                    }
                } else {
                    webApiClient.postMessage(channelId, "Hi there!");
                }
            });
        }

        return "Ok";
    }
}
