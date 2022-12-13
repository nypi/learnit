package ru.croc.project.database.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database implements Database {
    private static final String connectionURL = "jdbc:h2:./TestDatabase/test";
    private static final String user = "sa";
    private static final String password = "";

    public H2Database() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, user, password);
    }
}
