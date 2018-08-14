package com.jabaddon.dialogflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvService {

    @Value("${CLIENT_ID}")
    private String cliendId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    public EnvService() {
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCliendId() {
        return cliendId;
    }
}
