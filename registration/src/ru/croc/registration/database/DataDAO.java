package registration.database;

import registration.Client;
import registration.Hash;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DataDAO implements WorkWithDatabase{
    private static final String SQL_INSERT_DATA = "INSERT INTO DATA (LOGIN, PASSWORD_HASH) VALUES (?,?)";

    public DataDAO() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    /**
     * This method creates a table in which the user's login and password are stored
     * @throws SQLException
     */
    public static void createTableData() throws SQLException {

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS DATA " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " login VARCHAR(50) UNIQUE NOT NULL, " +
                    " password_hash VARCHAR(100) NOT NULL)";

            stmt.executeUpdate(sql);
        }
    }

    /**
     * The method uses the user id to find his login in the table
     * @param id id client
     * @return login client
     * @throws SQLException
     */
    public static String getLoginById(int id) throws SQLException {
        String sql = "select * from DATA where ID = ?";
        String login = "";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    login = result.getString("LOGIN");
                    return login;
                }
            }
        }
        return login;
    }

    /**
     * The method accepts the user's login and records his username and password
     * @param client current client when he register
     * @return client
     * @throws SQLException
     */
    public static Client addClientData(Client client) throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_DATA)) {

            preparedStatement.setString(1, client.getLogin());
            preparedStatement.setString(2, client.getPassword());

            preparedStatement.executeUpdate();

            return client;
        }
    }

    /**
     * The method accepts the user's login and returns his id
     * @param login current client
     * @return id current client
     * @throws SQLException
     */
    public static int findIdClient(String login) throws SQLException {
        String sql = "select * from DATA where login = ?";
        int id = 0;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    id = result.getInt("ID");
                    return id;
                }
            }
        }
        return id;
    }

    /**
     * the method checks for compliance with the current login, its password from the database
     * @param login user login during authorization
     * @param currentPassword user password during authorization
     * @return returns whether the login corresponds to the current user
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkPassword(String login, String currentPassword) throws SQLException, NoSuchAlgorithmException {
        String sql = "select * from DATA where login = ?";
        String passwordFromDB = "";
        currentPassword = Hash.hashPassword(currentPassword);

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    passwordFromDB = result.getString("PASSWORD_HASH");
                    return passwordFromDB.equals(currentPassword);
                }
            }
        }
        return false;
    }
}
