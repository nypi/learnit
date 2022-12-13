package registration.database;

import registration.Client;

import java.sql.*;

public class ClientParameterDAO implements WorkWithDatabase{
    private static final String SQL_INSERT_PARAMETERS = "INSERT INTO CLIENT_PARAMETER (NAME, SURNAME, DATA_ID) VALUES (?,?,?)";

    public ClientParameterDAO() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    /**
     * The method creates a table that stores the user's first and last name
     * @throws SQLException
     */
    public static void createTableClientParameter() throws SQLException {

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS CLIENT_PARAMETER " +
                    "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    " name VARCHAR(50) NOT NULL, " +
                    " surname VARCHAR(50) NOT NULL," +
                    "data_id INTEGER NOT NULL ," +
                    "FOREIGN KEY (data_id) REFERENCES DATA (id))";

            stmt.executeUpdate(sql);
        }
    }

    /**
     * This method adds the user's first and last name to the database
     * @param client
     * @throws SQLException
     */
    public static void addClientParameters(Client client) throws SQLException {

        int id = DataDAO.findIdClient(client.getLogin());
        if (id == 0) {
            throw new RuntimeException("неверный запрос к БД");
        }

        try (Connection connection = DriverManager
                .getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PARAMETERS)) {

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getSurname());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

        }
    }

    /**
     * The method by user id returns his last name from the clientParameters table
     * @param id
     * @return surname current user's
     * @throws SQLException
     */
    public static String getSurname(int id) throws SQLException {
        String sql = "select * from CLIENT_PARAMETER where DATA_ID = ?";
        String surname = "";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    surname = result.getString("SURNAME");
                    return surname;
                }
            }
        }
        return surname;
    }

    /**
     * The method returns the user name corresponding to his ID
     * @param id ID user
     * @return name user
     * @throws SQLException
     */
    public static String getName(int id) throws SQLException {
        String sql = "select * from CLIENT_PARAMETER where DATA_ID = ?";
        String name = "";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    name = result.getString("NAME");
                    return name;
                }
            }
        }
        return name;
    }
}
