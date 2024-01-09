package com.mychatgpt_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIProperties {

    @Value("${openai.url.completions}")
    private String openaiUrlCompletions;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${chatGpt.model}")
    private String chatGptModel;

    public String getOpenaiUrlCompletions() {
        return openaiUrlCompletions;
    }

    public void setOpenaiUrlCompletions(String openaiUrlCompletions) {
        this.openaiUrlCompletions = openaiUrlCompletions;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getChatGptModel() {
        return chatGptModel;
    }

    public void setChatGptModel(String chatGptModel) {
        this.chatGptModel = chatGptModel;
    }
}
