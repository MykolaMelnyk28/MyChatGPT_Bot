package com.mychatgpt_bot.config;

import com.mychatgpt_bot.bot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public TelegramBot telegramBot(@Value("${bot.username}") String username,
                                   @Value("${bot.token}") String token) {
        return new TelegramBot(username, token);
    }
}
