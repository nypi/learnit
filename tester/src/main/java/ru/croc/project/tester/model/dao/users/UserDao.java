package ru.croc.project.tester.model.dao.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.croc.project.tester.model.pojo.users.User;
import ru.croc.project.tester.model.pojo.users.UserTestInformation;

import java.sql.*;
import java.util.List;

public class UserDao {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:C:\\Users\\ninop\\IdeaProjects\\tester\\db";

    static final String USER = "sa";
    static final String PASS = "sa";

    ObjectMapper mapper = new ObjectMapper();

    public User isExists(String id) throws ClassNotFoundException, SQLException, JsonProcessingException {
        Class.forName(JDBC_DRIVER);
        String userId = null;
        String serialized = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM USERS WHERE ID = '" + id + "';";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet result = statement.executeQuery(sql)) {
                    while(result.next()) {
                        userId = result.getString("ID");
                        serialized = result.getString("TEST_INF");
                    }
                }
            }
        }
        if (userId != null && serialized != null) {
            List<UserTestInformation> information = mapper.readValue(serialized, new TypeReference<>(){});
            return new User(userId, information);
        }
        else return null;
    }

    public void updateInfo(String id, List<UserTestInformation> information) throws ClassNotFoundException, SQLException, JsonProcessingException {
        Class.forName(JDBC_DRIVER);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String serialized = mapper.writeValueAsString(information);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE USERS SET TEST_INF = '" + serialized + "'" + "WHERE ID = '" + id + "'";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        }
    }

}
