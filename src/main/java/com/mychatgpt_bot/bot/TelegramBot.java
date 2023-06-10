package com.mychatgpt_bot.bot;

import com.mychatgpt_bot.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;
    private final ChatGPTService chatGpt;

    public TelegramBot(ChatGPTService chatGpt) {
        this.chatGpt = chatGpt;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
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
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendTypingAction(String chatId) {
        SendChatAction action = new SendChatAction();

        try {
            action.setChatId(chatId);
            action.setAction(ActionType.TYPING);
            execute(action);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
