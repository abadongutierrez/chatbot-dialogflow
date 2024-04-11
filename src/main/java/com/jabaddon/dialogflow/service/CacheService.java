package com.jabaddon.dialogflow.service;

import allbegray.slack.type.Bot;

import java.util.function.Supplier;

public interface CacheService {
    Bot getBotInfo(String botId, Supplier<Bot> supplier);
}
