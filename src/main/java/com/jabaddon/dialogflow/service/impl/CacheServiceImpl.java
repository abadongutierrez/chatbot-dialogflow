package com.jabaddon.dialogflow.service.impl;

import allbegray.slack.type.Bot;
import com.jabaddon.dialogflow.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class CacheServiceImpl implements CacheService {

    private final Map<String, Bot> botIdBotMap = new HashMap<>();

    @Override
    public Bot getBotInfo(String botId, Supplier<Bot> supplier) {
        if (botIdBotMap.containsKey(botId)) {
            return botIdBotMap.get(botId);
        }
        Bot t = supplier.get();
        botIdBotMap.put(botId, t);
        return t;
    }
}
