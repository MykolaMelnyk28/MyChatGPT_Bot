package com.mychatgpt_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotProperties {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.trigger-prefix}")
    private String botTriggerPrefix;

    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getBotTriggerPrefix() {
        return botTriggerPrefix;
    }

    public void setBotTriggerPrefix(String botTriggerPrefix) {
        this.botTriggerPrefix = botTriggerPrefix;
    }
}
