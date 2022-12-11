package ru.croc.wordimage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    private static final String URL = "jdbc:h2:./Desktop/1my/JavaКурсCroc/learnit/wordimage";
    private static final String USER = "Kundovik";
    private static final String PASSWORD = "kundovik";

    /**
     * Получить connection в бд.
     *
     * @return - connection
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}