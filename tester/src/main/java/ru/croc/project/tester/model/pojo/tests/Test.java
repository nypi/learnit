package ru.croc.project.tester.model.pojo.tests;

import java.util.ArrayList;
import java.util.List;

public class Test {
    String name;
    List<Question> questions;
    int questionCount;

    public String getName() {
        return name;
    }

    public List<String> getQuestionTexts() {
        List<String> questionTexts = new ArrayList<>();
        for (Question question : questions) {
            questionTexts.add(question.getQuestionText());
        }
        return questionTexts;
    }

    public List<List<String>> getAnswersVariants() {
        List<List<String>> answerVariants = new ArrayList<>();
        for (Question question : questions) {
            answerVariants.add(question.getAnswers().getVariants());
        }
        return answerVariants;
    }

    public List<String> getRightAnswers() {
        List<String> rightAnswers = new ArrayList<>();
        for (Question question : questions) {
            rightAnswers.add(question.getRightAnswer());
        }
        return rightAnswers;
    }

    public Test(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.questionCount = questions.size();
    }
}
