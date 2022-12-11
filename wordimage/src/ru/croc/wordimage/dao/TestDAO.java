package ru.croc.wordimage.dao;

import ru.croc.wordimage.entity.Answer;
import ru.croc.wordimage.entity.Question;
import ru.croc.wordimage.entity.Test;
import ru.croc.wordimage.util.Connections;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    /**
     * Получить список всех тестов в бд.
     *
     * @return - список тестов
     */
    public List<Test> getAllTest() throws SQLException, ClassNotFoundException {
        final String GET_TEST_QUERY_SQL = "SELECT * " +
                                          "FROM TEST;";
        List<Test> tests = new ArrayList<>();

        try(Connection connection = Connections.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(GET_TEST_QUERY_SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    tests.add(new Test(id,name));
                }

                return tests;
            }
        }
    }

    /**
     * Получить список вопросов определенного теста.
     *
     * @param numberTest - номер теста
     * @return - список вопросов
     */
    public List<Question> getQuestionList(int numberTest) throws SQLException, ClassNotFoundException {
        final String GET_QUESTIONS_QUERY_SQL =
                        "SELECT id, id_correct_answer, img_path " +
                        "FROM QUESTION " +
                        "WHERE ID_TEST = " + numberTest + ";";
        List<Question> questions = new ArrayList<>();

        try(Connection connection = Connections.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(GET_QUESTIONS_QUERY_SQL)) {
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
    }

    /**
     * Получить список ответов определенного вопроса.
     *
     * @param numberQuestion - номер вопроса
     * @return - список ответов
     */
    public List<Answer> getAnswerList(int numberQuestion) throws SQLException, ClassNotFoundException {
        final String GET_ANSWERS_QUERY_SQL =
                        "SELECT id, text " +
                        "FROM ANSWER\n" +
                        "WHERE id_question = " + numberQuestion + ";";
        List<Answer> answers = new ArrayList<>();

        try(Connection connection = Connections.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ANSWERS_QUERY_SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String text = resultSet.getString("text");

                    answers.add(new Answer(id, text));
                }

                return answers;
            }
        }
    }
}