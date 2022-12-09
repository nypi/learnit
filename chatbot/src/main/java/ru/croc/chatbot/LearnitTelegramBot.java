package ru.croc.chatbot;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class LearnitTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private CommandLine commandLine;

    @Value("${bot.username}")
    private String BOT_USERNAME;
    @Value("${bot.token}")
    private String BOT_TOKEN;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            long chatId = update.getMessage().getChatId();
            String userText = update.getMessage().getText();
            switch (userText) {
                case "/start":
                    sendMessage(chatId, "Привет!");
                    break;
                case "/statistics":
                    commandLine.getStats(chatId, update.getMessage().getFrom().getUserName());
                    break;
                case "/quiz":
                    sendMessage(chatId, "Опрос");
                    break;
                case "/help":
                    commandLine.getHelp(chatId);
                    break;
                default:
                    sendMessage(chatId, "Я не понимаю эту команду \uD83D\uDE22\nВведите /help - для помощи");
                    break;
            }
        } catch (TelegramApiException | InstantiationException | IllegalAccessException |
                 SQLException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Отправить сообщение
     * @param chatId chatID
     * @param textMessage текст сообщения
     */
    public void sendMessage(long chatId, String textMessage) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textMessage);
        execute(message);
    }
}
