package ru.croc.chatbot.quizzes;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.croc.chatbot.quizzes.translation.TranslationQuestion;

public interface Quiz {
    void start(long chatId, TranslationQuestion translationQuestion) throws TelegramApiException;
    int getResult();
}
