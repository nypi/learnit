package ru.croc.chatbot.quizzes.translation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.croc.chatbot.LearnitTelegramBot;
import ru.croc.chatbot.quizzes.Quiz;

@Component
public class TranslatorQuiz implements Quiz {

    @Lazy
    @Autowired
    private LearnitTelegramBot learnitTelegramBot;

    @Autowired
    private DataSource dataSource;

    @Value("${quiz.translator.count.question}")
    private int countQuestion;

    private int result = 0;
    private int countAnswer = 0;


    @Override
    public void start(long chatId, TranslationQuestion translationQuestion) throws TelegramApiException {
        SendMessage message = learnitTelegramBot.getMessage();
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        InlineKeyboardButton first = new InlineKeyboardButton();
        first.setText(translationQuestion.getFirst());
        first.setCallbackData(translationQuestion.getFirst());
        InlineKeyboardButton second = new InlineKeyboardButton();
        second.setText(translationQuestion.getSecond());
        second.setCallbackData(translationQuestion.getSecond());
        rowInLine.add(first);
        rowInLine.add(second);
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        sendMessage(message, chatId, "Как переводиться слово: *" + translationQuestion.getQuestion() + "*?");
    }

    /**
     * Выдать вопросы
     * @return пак вопросов
     */
    public List<TranslationQuestion> getQuestions() throws SQLException {
        List<TranslationQuestion> translationQuestions = new ArrayList<>();
        Set<Integer> questionId = new HashSet<>();
        String question = "";
        String first = "";
        String second = "";
        String right = "";
        while (questionId.size() < 5) {
            questionId.add((int) (Math.random() * countQuestion));
        }
        try (Connection connection = dataSource.getConnection()) {
            for (int id : questionId) {
                try (PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM questions q WHERE q.id = ?")) {
                    statement.setInt(1, id);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            question = result.getString("question");
                            first = result.getString("firstOption");
                            second = result.getString("secondOption");
                            right = result.getString("answer");
                        }
                    }
                }
                translationQuestions.add(new TranslationQuestion(question, first, second, right));
            }
        }
        return translationQuestions;
    }

    @Override
    public int getResult() {
        return result;
    }

    private void sendMessage(SendMessage message, long chatId, String text) throws TelegramApiException {
        learnitTelegramBot.sendMessage(message, chatId, text);
    }

}
