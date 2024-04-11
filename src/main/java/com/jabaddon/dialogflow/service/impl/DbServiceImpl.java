package com.jabaddon.dialogflow.service.impl;

import com.jabaddon.dialogflow.service.DbService;
import com.jabaddon.dialogflow.service.SlackInstallation;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DbServiceImpl implements DbService {

    private final Map<String, SlackInstallation> slackInstallationMap = new HashMap<>();

    @Override
    public void saveNewSlackInstallation(SlackInstallation slackInstallation) {
        if (!slackInstallationMap.containsKey(slackInstallation.getTeamId())) {
            slackInstallationMap.put(slackInstallation.getTeamId(), slackInstallation);
        }
    }

    @Override
    public Optional<SlackInstallation> getSlackInstallation(String teamId) {
        return Optional.ofNullable(slackInstallationMap.get(teamId));
    }
}
