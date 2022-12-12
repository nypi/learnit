package ru.croc.wordimage;

import ru.croc.wordimage.dao.TestDAO;
import ru.croc.wordimage.entity.Answer;
import ru.croc.wordimage.entity.Question;
import ru.croc.wordimage.entity.Result;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class WordByImage {
    private final TestDAO testDAO = new TestDAO();

    /**
     * Запуск теста: по изображению выбрать правильное слово из списка вариантов.
     */
    public void runTestWordByImage() throws SQLException, ClassNotFoundException {
        try(Scanner in = new Scanner(System.in)) {
            System.out.print("Введите id: ");
            String idUser = in.next();

            testDAO.getAllTest() //вывод списка вопросов
                    .forEach(test -> System.out.println("Test № " + test.id() + ". " + test.name()));

            System.out.print("Введите номер теста, который хотите пройти: ");
            int numberTest = in.nextInt();
            List<Question> questions = testDAO.getQuestionList(numberTest);
            int result = testResult(questions, in);
            System.out.println("Результат: " + result + " из " + questions.size());

            testDAO.saveResult(new Result(idUser, numberTest, result, LocalDateTime.now()));
        }
    }

    /**
     * Получить результат теста.
     *
     * @param questions - список вопросов в тесте
     * @param in - Scanner для ввода ответов пользователя
     * @return - количество баллов за тест
     */
    private int testResult(List<Question> questions, Scanner in) throws SQLException, ClassNotFoundException {
        int result = 0;
        for(Question question : questions) {
            int number = 1;
            Map<Integer, Integer> numberId = new HashMap<>();

            System.out.println(question.imgPath()); //вывод изображения
            List<Answer> answers = testDAO.getAnswerList(question.id());

            for(Answer answer : answers) {
                System.out.println(number + ". " + answer.text());
                numberId.put(number, answer.id());
                number++;
            }

            System.out.print("Введите номер ответа: ");
            int userAnswer = numberId.get(in.nextInt());
            if(question.numberCorrectAnswer() == userAnswer) {
                result++;
            }
        }

        return result;
    }
}