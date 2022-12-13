package ru.croc.wordmatcher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseWorker {
    private static final String dataBaseDriver = "org.postgresql.Driver";
    private static final String connectionUrl = "jdbc:h2:/Users/ctacek/IdeaProjects/wordmatcher/resources/sentences";
    private static final String user = "sa";
    private static final String password = "";


    public DatabaseWorker() throws SQLException, ClassNotFoundException {
        Class.forName(dataBaseDriver);

    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, user, password);
    }





}