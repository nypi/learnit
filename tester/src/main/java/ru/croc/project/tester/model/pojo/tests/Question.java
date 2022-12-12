package ru.croc.project.tester.model.pojo.tests;

public class Question {
    private String questionText;
    private Answers answers;
    private String rightAnswer;

    public Question(String questionText, Answers answers, String rightAnswer) {
        this.questionText = questionText;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public Question() {
        this.questionText = "";
        this.answers = null;
        this.rightAnswer = "";
    }

    public String getQuestionText() {
        return questionText;
    }

    public Answers getAnswers() {
        return answers;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }
}
