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
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                long chatId = update.getMessage().getChatId();
                String userText = update.getMessage().getText();
                switch (userText) {
                    case "/start":
                        sendMessage(getMessage(), chatId, "*Привет! Я бот Learn It! \uD83D\uDC23*\nВведите /help - для помощи");
                        break;
                    case "/statistics":
                        commandLine.getStats(chatId);
                        break;
                    case "/quiz":
                        sendMessage(getMessage(), chatId, "Данный тест позволяет проверить свой словарный запас!\n"
                                        + "Вам будут выпадать разный слова, а вы должны указать их перевод.\n"
                                        + "Удачи! Чтобы начать пиши /ready \uD83D\uDE09");
                        break;
                    case "/help":
                        commandLine.getHelp(chatId);
                        break;
                    case "/ready":
                        commandLine.getQuiz(chatId);
                        break;
                    default:
                        sendMessage(getMessage(), chatId, "*\uD83D\uDE22 Я не понимаю эту команду*\nВведите /help - для помощи");
                        break;
                }
            } catch (TelegramApiException | InstantiationException | IllegalAccessException |
                     SQLException | ClassNotFoundException exception) {
                throw new RuntimeException(exception);
            }
        } else if (update.hasCallbackQuery()) {
            try {
                String callBackData = update.getCallbackQuery().getData();
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                if (commandLine.checkAnswer(chatId, callBackData)) {
                    commandLine.addRightAnswer(chatId);
                }
                commandLine.nextQuestion(chatId);
            } catch (SQLException | TelegramApiException | ClassNotFoundException |
                     InstantiationException | IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
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
     * @param message SendMessage
     * @param chatId chatID
     * @param textMessage текст сообщения
     */
    public void sendMessage(SendMessage message, long chatId, String textMessage)
            throws TelegramApiException {
        message.setChatId(chatId);
        message.setText(textMessage);
        message.enableMarkdown(true);
        execute(message);
    }

    /**
     * Получить SendMessage
     * @return SendMessage
     */
    public SendMessage getMessage() {
        return new SendMessage();
    }
}
