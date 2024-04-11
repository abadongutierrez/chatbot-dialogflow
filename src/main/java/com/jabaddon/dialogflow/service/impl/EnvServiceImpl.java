package com.jabaddon.dialogflow.service.impl;

import com.jabaddon.dialogflow.service.EnvService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EnvServiceImpl implements EnvService {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    public EnvServiceImpl() {
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getClientId() {
        return clientId;
    }
}
