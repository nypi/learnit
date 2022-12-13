package registration.database;

import registration.Client;

import java.sql.*;

public class InfoDAO implements WorkWithDatabase{
    private static final String SQL_INSERT_INFO = "INSERT INTO INFO (PHONE_NUMBER, EMAIL, DATA_ID) VALUES (?,?,?)";

    public InfoDAO() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    /**
     * the method creates a table in which the user's phone and email are stored
     * @throws SQLException
     */
    public static void createTableInfo() throws SQLException {

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS INFO " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " phone_number VARCHAR(15) UNIQUE, " +
                    " email VARCHAR(40) UNIQUE ," +
                    "data_id INTEGER NOT NULL ," +
                    "FOREIGN KEY (data_id) REFERENCES DATA (id))";

            stmt.executeUpdate(sql);
        }
    }

    /**
     * The method returns the user's email by his id
     * @param id ID user
     * @return email user's
     * @throws SQLException
     */
    public static String getEmail(int id) throws SQLException {
        String sql = "select * from INFO where DATA_ID = ?";
        String email = "";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    email = result.getString("EMAIL");
                    return email;
                }
            }
        }
        return email;
    }

    /**
     * The method returns the user's phone number by its id
     * @param id ID user's
     * @return phone number user's
     * @throws SQLException
     */
    public static String getNumberPhone(int id) throws SQLException {
        String sql = "select * from INFO where DATA_ID = ?";
        String phoneNumber = "";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    phoneNumber = result.getString("PHONE_NUMBER");
                    return phoneNumber;
                }
            }
        }
        return phoneNumber;
    }

    /**
     * The method allows you to add the client's phone number and email to the database
     * @param client current user
     * @throws SQLException
     */
    public static void addClientInfo(Client client) throws SQLException {

        int id = DataDAO.findIdClient(client.getLogin());
        if (id == 0) {
            throw new RuntimeException("неверный запрос к БД");
        }

        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INFO)) {

            preparedStatement.setString(1, client.getNumberPhone());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

        }
    }
}
