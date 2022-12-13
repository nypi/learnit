package ru.croc.chatbot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.croc.chatbot.quizzes.translation.TranslationQuestion;
import ru.croc.chatbot.quizzes.translation.TranslatorQuiz;

@Component
public class CommandLine {

    @Lazy
    @Autowired
    private LearnitTelegramBot learnitTelegramBot;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private TranslatorQuiz translatorQuiz;

    private final Map<Long, List<TranslationQuestion>> questions = new HashMap<>();
    private final Map<Long, TranslationQuestion> oldQuestion = new HashMap<>();

    /**
     * Получить тест
     * @param chatId chatID
     */
    public void getQuiz(long chatId)
            throws TelegramApiException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        questions.put(chatId, translatorQuiz.getQuestions());
        nextQuestion(chatId);
    }

    /**
     * Добавить правильный ответ в БД
     * @param chatId chatID
     */
    public void addRightAnswer(long chatId) throws SQLException {
        int rightAnswers = 0;
        long chat = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM USERS u WHERE u.chatId = ?")) {
                statement.setLong(1, chatId);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        rightAnswers = result.getInt("countRightAnswer");
                        chat = result.getLong("chatId");
                    }
                }
            }
            if (chat != chatId) {
                addNewUser(connection, chatId);
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE USERS SET countRightAnswer=? WHERE chatId=?;")) {
                statement.setInt(1, rightAnswers + 1);
                statement.setLong(2, chatId);
                statement.execute();
            }
        }
    }

    /**
     * Добавить пользователя в статистику
     * @param connection соединение
     * @param chatId chatID
     */
    public void addNewUser(Connection connection, long chatId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO USERS(chatId) VALUES (?);")) {
            statement.setLong(1, chatId);
            statement.execute();
        }
    }

    /**
     * Выдать вопрос
     * @param chatId chatID
     */
    public void nextQuestion(long chatId)
            throws TelegramApiException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (!questions.get(chatId).isEmpty()) {
            TranslationQuestion translationQuestion = questions.get(chatId).iterator().next();
            questions.get(chatId).remove(translationQuestion);
            oldQuestion.put(chatId, translationQuestion);
            translatorQuiz.start(chatId, translationQuestion);
        } else {
            oldQuestion.remove(chatId);
            questions.remove(chatId);
            int countQuiz = 0;
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM USERS u WHERE u.chatId = ?")) {
                    statement.setLong(1, chatId);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            countQuiz = result.getInt("countQuiz");
                        }
                    }
                }
                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE USERS SET countQuiz=? WHERE chatId=?;")) {
                    statement.setInt(1, countQuiz + 1);
                    statement.setLong(2, chatId);
                    statement.execute();
                }
            }
            getStats(chatId);
        }
    }

    /**
     * Проверить ответ
     * @param chatId chatID
     * @param answer ответ
     */
    public boolean checkAnswer(long chatId, String answer) {
        if (oldQuestion.get(chatId).getRight().equals(answer)) {
            return true;
        }
        return false;
    }

    /**
     * Посмотреть статистику
     * @param chatId charID
     */
    public void getStats(long chatId)
            throws InstantiationException, IllegalAccessException, SQLException, TelegramApiException, ClassNotFoundException {
        int count = 0;
        int rightAnswer = 0;
        double average = 0;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM USERS u WHERE u.chatId = ?")) {
                statement.setLong(1, chatId);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        count = result.getInt("countQuiz");
                        rightAnswer = result.getInt("countRightAnswer");
                    }
                }
            }
        }
        if (count == 0) {
            sendMessage(chatId, "*У вас еще нет статистики!*\nПроверь себя - /quiz \uD83D\uDE09");
        } else {
            average = rightAnswer / count;
            sendMessage(chatId, "*\uD83D\uDCC8 Твоя статистика:*\n\n"
                    + "Всего пройдено тестов: *" + count + "*\n"
                    + "Всего правильных ответов: *" + rightAnswer + "*\n"
                    + "Средний балл: *" + average + "*");
        }
    }

    /**
     * Получить помощь
     * @param chatId chatID
     */
    public void getHelp(long chatId) throws TelegramApiException {
        sendMessage(chatId, "*Я бот Learn It \uD83D\uDC23! Я помогу тебе в изучении английского языка!\uD83C\uDDEC\uD83C\uDDE7*\n\n"
                + "Что я могу:\n/statistics - Моя статистика\n/quiz - Запустить тест\n/help - Помощь");
    }

    /**
     * Отправить сообщение
     * @param chatId chatID
     * @param message тест сообщения
     */
    private void sendMessage(long chatId, String message) throws TelegramApiException {
        learnitTelegramBot.sendMessage(learnitTelegramBot.getMessage(), chatId, message);
    }
}
