package testingsystem.data.dao;

import testingsystem.data.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestsDAO extends AttributesDAO{

    public TestsDAO() throws ClassNotFoundException {
        Class.forName(JDBC_CLASSNAME);

    }

    /**
     * Создает таблицу с тестовыми вопросами, если ее не существует.
     * Структура полей таблицы:
     * id IDENTITY NOT NULL PRIMARY KEY,
     * lesson_id INTEGER NOT NULL,
     * eng_sentence VARCHAR2(255) NOT NULL,
     * ru_sentence VARCHAR2(255) NOT NULL
     * @throws SQLException Ошибка SQL
     */
    public void createTable() throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = """
                     CREATE TABLE IF NOT EXISTS TESTS
                     (id IDENTITY NOT NULL PRIMARY KEY,
                      lesson_id INTEGER NOT NULL,
                      eng_sentence VARCHAR2(255) NOT NULL,
                      ru_sentence VARCHAR2(255) NOT NULL
                      );
                  """;
            statement.executeUpdate(sql);

        }
    }

    /**
     * Получает все вопросы по заданной теме
     * @param lesson_id номер темы
     * @return Список (List) объектов testingsystem.data.Question
     * @throws SQLException Ошибка SQL
     */
    public List<Question> getQuestions(int lesson_id) throws SQLException {
        List<Question> testQuestions = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM TESTS WHERE lesson_id = " + lesson_id + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                testQuestions.add(new Question(resultSet.getInt("lesson_id"),
                                    resultSet.getString("eng_sentence"), resultSet.getString("ru_sentence")));
            }

        }
        return testQuestions;
    }

    /**
     * Добавляет в TESTS новый тест по заданной теме. Данные передаются в файле.
     * Файл должен представлять собой набор английских и русских предложений,
     * разделенных точкой.
     * Формат: Предложение на английском. Предложение на русском\n
     * @param path to the file
     */
    public void createNewTest(int lesson_id,
                              String path) throws SQLException, IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String currentLine;
            while ((currentLine = in.readLine()) != null){
                String[] values = currentLine.split("\\.");
                createQuestion(new Question(lesson_id, values[0].trim(), values[1].trim()));
            }
        }

    }


    /**
     * Добавляет новый вопрос (строку)
     * @param q Добавляемый вопрос.
     */
    public void createQuestion(Question q) throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO TESTS (lesson_id, eng_sentence, ru_sentence) VALUES (" + q.lesson_id()
                            + ", '" + q.engSentence() + "', '" + q.ruSentence() + "');";
            statement.executeUpdate(sql);
        }
    }

}
