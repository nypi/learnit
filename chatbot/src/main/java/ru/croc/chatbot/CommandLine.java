package ru.croc.chatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CommandLine {

    @Lazy
    @Autowired
    private LearnitTelegramBot learnitTelegramBot;

    @Autowired
    private DataSource dataSource;

    /**
     * Посмотреть статистику
     * @param chatId charID
     * @param userName Имя пользователя
     */
    public void getStats(long chatId, String userName)
            throws InstantiationException, IllegalAccessException, SQLException, TelegramApiException, ClassNotFoundException {
        int count = 0;
        double average = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM USERS u WHERE u.USERNAME = ?")) {
                statement.setString(1, userName);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        count = result.getInt("count");
                        average = result.getDouble("average");
                    }
                }
            }
        }
        if (count == 0) {
            sendMessage(chatId, "У вас еще нет статистики!\nПроверь себя - /quiz \uD83D\uDE09");
        } else {
            sendMessage(chatId, "Твоя статистика \uD83D\uDCC8:");
            sendMessage(chatId, "Всего пройдено тестов: \uD83D\uDD8B" + count);
            sendMessage(chatId, "Средний балл: \uD83D\uDCAF" + average);
        }
    }

    /**
     * Получить помощь
     * @param chatId chatID
     */
    public void getHelp(long chatId) throws TelegramApiException {
        sendMessage(chatId, "Я бот Learn It \uD83D\uDC23! Я помогу тебе в изучении английского языка!\n\n"
                + "Что я могу:\n/statistics - Моя статистика\n/quiz - Запустить тест\n/help - Помощь");
    }

    /**
     * Отправить сообщение
     * @param chatId chatID
     * @param message тест сообщения
     */
    private void sendMessage(long chatId, String message) throws TelegramApiException {
        learnitTelegramBot.sendMessage(chatId, message);
    }

}
