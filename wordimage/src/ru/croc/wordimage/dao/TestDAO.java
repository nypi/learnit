package ru.croc.wordimage.dao;

import ru.croc.wordimage.entity.Answer;
import ru.croc.wordimage.entity.Question;
import ru.croc.wordimage.entity.Result;
import ru.croc.wordimage.entity.Test;
import ru.croc.wordimage.util.Connections;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    /**
     * Получить список всех тестов в бд.
     *
     * @return - список тестов
     */
    public List<Test> getAllTest() throws SQLException, ClassNotFoundException {
        List<Test> tests = new ArrayList<>();
        final String GET_TEST_QUERY_SQL = "SELECT * " +
                                          "FROM TEST;";
        try(
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_QUERY_SQL)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                tests.add(new Test(id,name));
            }

            return tests;
        }
    }

    /**
     * Получить список вопросов определенного теста.
     *
     * @param numberTest - номер теста
     * @return - список вопросов
     */
    public List<Question> getQuestionList(int numberTest) throws SQLException, ClassNotFoundException {
        List<Question> questions = new ArrayList<>();
        final String GET_QUESTIONS_QUERY_SQL =
                        "SELECT id, id_correct_answer, img_path " +
                        "FROM QUESTION " +
                        "WHERE ID_TEST = " + numberTest + ";";

        try(
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTIONS_QUERY_SQL)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int numberCorrectAnswer = resultSet.getInt("id_correct_answer");
                File imgPath = new File(resultSet.getString("img_path"));

                questions.add(new Question(id,numberCorrectAnswer, imgPath));
            }

            return questions;
        }
    }

    /**
     * Получить список ответов определенного вопроса.
     *
     * @param numberQuestion - номер вопроса
     * @return - список ответов
     */
    public List<Answer> getAnswerList(int numberQuestion) throws SQLException, ClassNotFoundException {
        List<Answer> answers = new ArrayList<>();
        final String GET_ANSWERS_QUERY_SQL =
                        "SELECT id, text " +
                        "FROM ANSWER\n" +
                        "WHERE id_question = " + numberQuestion + ";";

        try(
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWERS_QUERY_SQL)
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");

                answers.add(new Answer(id, text));
            }

            return answers;
        }
    }

    /**
     * Сохранение результата теста в бд.
     *
     * @param result - результат пользоваетеля
     */
    public void saveResult(Result result) throws SQLException, ClassNotFoundException {
        final String SAVE_RESULT_QUERY_SQL = "INSERT INTO RESULT(id_user, id_test, score, date_time) " +
                "VALUES(?, ?, ?, ?);";

        try(
            Connection connection = Connections.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_RESULT_QUERY_SQL)
        ) {
            preparedStatement.setString(1, result.idUser());
            preparedStatement.setInt(2, result.numberTest());
            preparedStatement.setInt(3, result.score());
            preparedStatement.setString(4, result.dateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedStatement.executeUpdate();
        }
    }
}