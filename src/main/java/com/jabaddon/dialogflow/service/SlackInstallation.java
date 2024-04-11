package com.jabaddon.dialogflow.service;

public class SlackInstallation {
    private final String teamId;
    private final String botUserId;
    private final String botAccessToken;

    public SlackInstallation(String teamId, String botUserId, String botAccessToken) {
        this.teamId = teamId;
        this.botUserId = botUserId;
        this.botAccessToken = botAccessToken;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBotUserId() {
        return botUserId;
    }

    public String getBotAccessToken() {
        return botAccessToken;
    }
}
