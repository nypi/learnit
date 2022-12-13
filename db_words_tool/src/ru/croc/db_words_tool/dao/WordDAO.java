package ru.croc.db_words_tool.dao;

import ru.croc.db_words_tool.table.Word;

import ru.croc.db_words_tool.exception.IllegalWordIsExistException;
import ru.croc.db_words_tool.exception.IllegalWordNotFoundException;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class WordDAO {

    private final Connection connection;
    private final String tenantID;

    public WordDAO(Connection connection, String tenantID) {
        this.connection = connection;
        this.tenantID = tenantID;
    }

    public static void createTableWord(Connection connection) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            String word = "CREATE TABLE WORD(" +
                    "ID MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "TENANT_ID VARCHAR(255) NOT NULL," +
                    "NAME VARCHAR(255) NOT NULL," +
                    "LEARNED BOOL NOT NULL," +
                    "PRIMARY KEY (ID));";

            statement.executeUpdate(word);
        }
    }

    public void dropTableWord() throws SQLException {
        String sql = "DROP TABLE WORD";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public boolean isExistIn(Word word) throws SQLException {
        boolean isWordExist = false;

        String sql = "SELECT COUNT(*) FROM WORD WHERE (NAME,TENANT_ID) = (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, word.word());
            statement.setString(2, tenantID);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    isWordExist = result.getInt(1) > 0;
                }
            }
        }

        return isWordExist;
    }

    public void insertWord(Word word) throws SQLException, IllegalWordIsExistException {
        if (isExistIn(word)) {
            throw new IllegalWordIsExistException(word);
        }

        String sql = "INSERT INTO WORD (NAME,LEARNED,TENANT_ID) VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, word.word());
            statement.setBoolean(2, word.isLearned());
            statement.setString(3, tenantID);
            statement.executeUpdate();
        }
    }

    public void deleteWord(Word word) throws SQLException, IllegalWordNotFoundException {
        if (!isExistIn(word)) {
            throw new IllegalWordNotFoundException(word);
        }

        String sql = "DELETE WORD WHERE (NAME,TENANT_ID) = (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, word.word());
            statement.setString(2, tenantID);
            statement.executeUpdate();
        }
    }

    public void updateStatus(Word word) throws SQLException {

        String sql = "UPDATE WORD SET LEARNED = ? WHERE (NAME,TENANT_ID) = (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, word.isLearned());
            statement.setString(2, word.word());
            statement.setString(3, tenantID);
            statement.executeUpdate();
        }
    }

    public List<Word> getWords(boolean isLearned) throws SQLException {
        List<Word> foundedWords = new ArrayList<>();

        String sql = "SELECT * FROM WORD WHERE (LEARNED,TENANT_ID) = (?,?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, isLearned);
            statement.setString(2, tenantID);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    foundedWords.add(new Word(
                            result.getString("NAME"),
                            result.getBoolean("LEARNED")));
                }
            }
        }

        return foundedWords;
    }
}
