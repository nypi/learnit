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
    static final String DB_URL = "jdbc:h2:C:\\Users\\ninop\\IdeaProjects\\tester\\db";
    static final String USER = "sa";
    static final String PASS = "sa";

    ObjectMapper mapper = new ObjectMapper();

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
