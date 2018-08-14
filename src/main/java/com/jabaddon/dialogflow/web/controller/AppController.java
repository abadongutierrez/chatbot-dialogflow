package com.jabaddon.dialogflow.web.controller;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.OAuthAccessToken;
import allbegray.slack.webapi.SlackWebApiClient;
import com.jabaddon.dialogflow.service.DbService;
import com.jabaddon.dialogflow.service.EnvService;
import com.jabaddon.dialogflow.service.SlackInstallation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private EnvService envService;

    @Autowired
    private DbService dbService;

    @GetMapping("/")
    public ModelAndView index(Map<String, Object> model) {
        model.put("title", "Install Bot");
        model.put("client_id", envService.getCliendId());

        return new ModelAndView("index", model);
    }

    @GetMapping("/redirect")
    public ModelAndView redirect(Map<String, Object> model, @RequestParam String code) {
        model.put("title", "Install Bot");
        model.put("client_id", envService.getCliendId());
        LOGGER.debug("Slack code: {}", code);

        SlackWebApiClient webApiClient = SlackClientFactory.createWebApiClient(null);
        OAuthAccessToken oAuthAccessToken = webApiClient.accessOAuth(
                envService.getCliendId(), envService.getClientSecret(), code, null);

        LOGGER.debug("Access Token: {}", oAuthAccessToken);

        Optional<SlackInstallation> optionalSlackInstallation =
                dbService.getSlackInstallation(oAuthAccessToken.getTeam_id());
        if (!optionalSlackInstallation.isPresent()) {
            dbService.saveNewSlackInstallation(new SlackInstallation(
                    oAuthAccessToken.getTeam_id(),
                    oAuthAccessToken.getBot().getBot_user_id(),
                    oAuthAccessToken.getBot().getBot_access_token()
            ));
        }

        return new ModelAndView("index", model);
    }
}
