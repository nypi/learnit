package testingsystem.data.dao;

import testingsystem.data.User;

import java.sql.*;

public class UsersDAO extends AttributesDAO{

    public UsersDAO() throws ClassNotFoundException {
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
                     CREATE TABLE IF NOT EXISTS users
                     (id IDENTITY NOT NULL PRIMARY KEY,
                      name VARCHAR2(255) NOT NULL,
                      lesson_id INTEGER NOT NULL
                      );
                  """;
            statement.executeUpdate(sql);

        }
    }

    /**
     * Находит пользователя по id
     * @param id user's id
     * @return User if found, null otherwise
     * @throws SQLException SQL error
     */
    public User findUser(int id) throws SQLException{
        User user = null;
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM users WHERE id = " + id + ";";

            ResultSet result = statement.executeQuery(sql);
            if (result.next())
                user = new User(result.getInt("id"), result.getString("name"),
                        result.getInt("lesson_id"));
        }
        return user;
    }


    /**
     * Добавляет нового пользователя
     * @param user данные нового пользователя
     */
    public void createUser(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO users (name, lesson_id) VALUES ('" + user.getUserName() + "', "
                            + user.getTestLessonId() + ");";
            statement.executeUpdate(sql);
        }
    }

    /**
     * Обновляет данные о пользователе
     * @param user пользователь с обновленными данными
     * @throws SQLException ошибка SQL
     */
    public void updateUser(User user) throws SQLException {
        try(Connection connection = DriverManager.getConnection(JDBC_URL,username,password);
            Statement statement = connection.createStatement()) {
            String sql = "UPDATE users SET name = '" + user.getUserName() + "', lesson_id = "
                    + user.getTestLessonId() + " WHERE id = " + user.getId() + ";";
            statement.executeUpdate(sql);
        }
    }

}
