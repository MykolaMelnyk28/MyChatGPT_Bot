package com.mychatgpt_bot;

import com.mychatgpt_bot.bot.TelegramBot;
import com.mychatgpt_bot.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class MyChatGptBotApplication {
	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(MyChatGptBotApplication.class, args);
		final AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(AppConfig.class);

		TelegramBot telegramBot = context.getBean(TelegramBot.class);

		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(telegramBot);
	}
}
