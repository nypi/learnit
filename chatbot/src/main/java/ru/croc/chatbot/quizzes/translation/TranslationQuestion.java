package ru.croc.chatbot.quizzes.translation;

import ru.croc.chatbot.quizzes.Question;

public class TranslationQuestion implements Question {

    private String question;
    private String first;
    private String second;
    private String right;

    public TranslationQuestion(String question, String first, String second, String right) {
        this.question = question;
        this.first = first;
        this.second = second;
        this.right = right;
    }

    public String getQuestion() {
        return question;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getRight() {
        return right;
    }
}
