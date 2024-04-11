package com.jabaddon.dialogflow.service;

import java.util.Optional;

public interface DbService {
    void saveNewSlackInstallation(SlackInstallation slackInstallation);

    Optional<SlackInstallation> getSlackInstallation(String teamId);
}
