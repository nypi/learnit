package ru.croc.db_words_tool.example;

import java.sql.DriverManager;
import java.sql.Connection;

import ru.croc.db_words_tool.dao.WordDAO;
import ru.croc.db_words_tool.exception.IllegalWordIsExistException;
import ru.croc.db_words_tool.table.Word;

import ru.croc.db_words_tool.exception.IllegalWordNotFoundException;

import java.sql.SQLException;

public class Example {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DATABASE_URL = "jdbc:h2:tcp://localhost/~/croc";

    private static final String USERNAME = "max";
    private static final String PASSWORD = "java";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalWordNotFoundException, IllegalWordIsExistException {

        Class.forName(JDBC_DRIVER);

        String[] users = {"max", "alex", "roma"};
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {

            WordDAO.createTableWord(connection);

            WordDAO wordDAOMax = new WordDAO(connection, users[0]);
            wordDAOMax.insertWord(new Word("java", true));
            wordDAOMax.insertWord(new Word("tree", false));
            wordDAOMax.insertWord(new Word("apple", true));
            System.out.println(wordDAOMax.getWords(true));


            WordDAO wordDAOAlex = new WordDAO(connection, users[1]);
            Word w1 = new Word("table", true);
            wordDAOAlex.insertWord(w1);
            wordDAOAlex.insertWord(new Word("build", false));
            wordDAOAlex.deleteWord(w1);


            WordDAO wordDAORoma = new WordDAO(connection, users[2]);
            Word fields = new Word("fields", false);
            wordDAORoma.insertWord(new Word("tea", false));
            wordDAORoma.insertWord(fields);
            fields.setLearned(true);
            wordDAORoma.updateStatus(fields);
        }
    }
}
