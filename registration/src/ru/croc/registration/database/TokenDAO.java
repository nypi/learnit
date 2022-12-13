package registration.database;

import java.sql.*;

public class TokenDAO implements WorkWithDatabase{
    private static final String SQL_INSERT_TOKEN = "INSERT INTO TOKEN (DATA_ID, TOKEN) VALUES (?,?)";

    public TokenDAO() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    /**
     * The method creates a table in which the user's authorization status is stored
     * @throws SQLException
     */
    public static void createTableToken() throws SQLException {

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS TOKEN " +
                    "(data_id INTEGER NOT NULL ," +
                    " token VARCHAR(300), " +
                    "FOREIGN KEY (data_id) REFERENCES DATA (id))";
            stmt.executeUpdate(sql);
        }
    }

    /**
     * The method creates a row in the TOKEN table during user registration
     * @param login this login client during registration
     * @throws SQLException
     */
    public static void addClientToken(String login) throws SQLException {

        int id = DataDAO.findIdClient(login);
        if (id == 0) {
            throw new RuntimeException("неверный запрос к БД");
        }

        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TOKEN)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "");

            preparedStatement.executeUpdate();
        }
    }

    /**
     * The method sets the value of the token passed to the user during authorization
     * @param login it's login client's during authorization
     * @param token it's a generated token, during user authorization
     * @throws SQLException
     */
    public static void updateTokenDuringAuthorization(String login, String token) throws SQLException {
        int id = DataDAO.findIdClient(login);
        String sql =
                String.format("UPDATE TOKEN SET TOKEN = ? WHERE DATA_ID = '%s'", id);


        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, token);
            statement.executeUpdate();
        }
    }

    /**
     * The method removes the user's token when he logs out
     * @param login it's login user's, that can logout
     * @throws SQLException
     */
    public static void deleteTokenBeforeLogout(String login) throws SQLException {
        int id = DataDAO.findIdClient(login);
        String sql =
                String.format("UPDATE TOKEN SET TOKEN = ? WHERE DATA_ID = '%s'", id);
        String freeToken = " ";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, freeToken);
            statement.executeUpdate();
        }
    }


    /**
     * The method checks for the presence of a token in the database
     * @param token this is a token that is stored on the user's computer
     * @return returns true if such a token exists in the database, false otherwise
     * @throws SQLException
     */
    public static boolean findStatusClient(String token) throws SQLException {
        String sql = "select * from TOKEN where TOKEN = ?";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, token);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }
}
