package com.mychatgpt_bot.config;

import com.mychatgpt_bot.bot.TelegramBot;
import com.mychatgpt_bot.service.ChatGPTService;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public ChatGPTService chatGPTService() {
        return new ChatGPTService();
    }
    @Bean
    public TelegramBot telegramBot(ChatGPTService chatGPTService) {
        return new TelegramBot(chatGPTService);
    }
}