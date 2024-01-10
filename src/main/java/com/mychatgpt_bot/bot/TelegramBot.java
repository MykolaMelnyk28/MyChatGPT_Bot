package com.mychatgpt_bot.bot;

import com.mychatgpt_bot.config.BotProperties;
import com.mychatgpt_bot.service.ChatGPTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private final BotProperties botProperties;
    private final ChatGPTService chatGpt;

    public TelegramBot(BotProperties botProperties, ChatGPTService chatGpt) {
        this.botProperties = botProperties;
        this.chatGpt = chatGpt;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            SendMessage sendMessage = new SendMessage();
            String chatId = update.getMessage().getChatId().toString();

            sendTypingAction(chatId);

            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                askChatGpt(sendMessage, chatId, text);
            } else {
                send(chatId, ChatGPTService.RESPONSE_TO_MEDIA_CONTENT);
            }
        }
    }

    private void askChatGpt(SendMessage sendMessage, String chatId, String text) {
        String response = chatGpt.askChatGPTText(text);
        send(chatId, response);
    }

    private void send(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        try {
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);
            execute(sendMessage);
            logger.info("Send response");
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendTypingAction(String chatId) {
        SendChatAction action = new SendChatAction();
        try {
            action.setChatId(chatId);
            action.setAction(ActionType.TYPING);
            execute(action);
            logger.info("Send typing action");
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}
