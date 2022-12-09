package ru.croc.chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LearnitTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String BOT_USERNAME;

    @Value("${bot.token}")
    private String BOT_TOKEN;

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("Test!");
            execute(message);
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }
}
