package ru.croc.project.tester.model.dao.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.croc.project.tester.model.pojo.tests.Answers;
import ru.croc.project.tester.model.pojo.tests.Question;
import ru.croc.project.tester.model.pojo.tests.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDao {
    DbProperties properties = new DbProperties();
    private String DB_URL;
    private String USER;
    private String PASS;

    ObjectMapper mapper = new ObjectMapper();

    public TestDao() throws IOException {
        this.DB_URL = properties.getHost();
        this.USER = properties.getLogin();
        this.PASS = properties.getPassword();
    }

    public Test getTestByName(String name) throws SQLException, JsonProcessingException {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM TESTS WHERE NAME = '" + name + "'";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery(sql)) {
                    while (result.next()) {
                        String questionText = result.getString("QUESTION");
                        Answers answers = mapper.readValue(result.getString("ANSWERS"), new TypeReference<>(){});
                        String rightAnswer = result.getString("RIGHT_ANS");
                        questions.add(new Question(questionText, answers, rightAnswer));
                    }
                }
            }
        }
        return new Test(name, questions);
    }
}
