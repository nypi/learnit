package registration.validators;


import registration.database.WorkWithDatabase;

import java.sql.*;

public class Validation implements WorkWithDatabase {

    public static boolean isCheckerNameSurname(String name) {
        if (name.length() > 49 || name.equals("")) {
            System.out.println("Данное поле не может быть пустым");
            return false;
        }
        if (name.length() != name.replaceFirst("[^а-яА-Яa-zA-Z ]", "").length()) {
            System.out.println("Вы ввели некорректные символы");
            return false;
        }
        return true;
    }

    public static boolean isCheckerPhone(String name) throws SQLException {

        if (isCheckPhoneNumberInDB(name)) {
            System.out.println("Аккаунт с данным номером телефона, уже зарегистрирован");
            return false;
        }

        if (name.length() > 15 || name.equals("")) {
            System.out.println("Вы не можете ввести более 15 цифр");
            return false;
        }
        if (name.length() != name.replaceFirst("[\\D]", "").length()) {
            System.out.println("Вы ввели некорректные символы");
            return false;
        }
        return true;
    }

    public static boolean isCheckerEmail(String email) throws SQLException {

        if (isCheckEmailInDB(email)) {
            System.out.println("Аккаунт с данной почтой, уже зарегистрирован");
            return false;
        }

        if (email.length() > 49 || email.length() < 4) {
            System.out.println("Вы ввели некорректную почту");
            return false;
        }
        if (!email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            System.out.println("Вы ввели некорректные данные");
            return false;
        }
        return true;
    }

    public static boolean isCheckerPassword(String password) {
        if (password.length() > 49 || password.length() < 8) {
            System.out.println("Длина пароля должны быть больше 8 символов, но не должна превышать 50");
            return false;
        }
        if (password.length() != password.replaceFirst("[а-яА-Я ]", "").length()) {
            System.out.println("Вам следует использовать только латинский алфавит");
            return false;
        }
        return true;
    }

    public static boolean isCheckerLogin(String login) throws SQLException {

        if (isCheckLoginInDB(login)) {
            System.out.println("Данный логин уже существует, введите другой");
            return false;
        }
        if (login.length() < 4) {
            System.out.println("Ваш логин слишком короткий");
            return false;
        }
        return login.matches("\\w+");
    }

    /**
     * this method checks whether there is such mail in the database
     * @param email
     * @return the method returns true if such mail exists in the database, false otherwise
     * @throws SQLException
     */
    private static boolean isCheckEmailInDB(String email) throws SQLException {
        String sql = "select * from INFO where EMAIL = ?";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    /**
     * this method checks whether there is such a phone number in the database
     * @param phoneNumber
     * @return the method returns true if such phone number exists in the database, false otherwise
     * @throws SQLException
     */
    private static boolean isCheckPhoneNumberInDB(String phoneNumber) throws SQLException {
        String sql = "select * from INFO where PHONE_NUMBER = ?";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    /**
     * this method checks whether there is such a login in the database
     * @param login
     * @return the method returns true if such login exists in the database, false otherwise
     * @throws SQLException
     */
    private static boolean isCheckLoginInDB(String login) throws SQLException {
        String sql = "select * from DATA where login = ?";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }
}
