package ru.croc.wordmatcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SentenceDAO {
    private static final String SQL_FIND_SENTENCE = "SELECT * FROM SENTENCES WHERE ID = ?";
    private static final String SQL_FIND_SENTENCE_OF_TOPIC = "SELECT * FROM SENTENCES WHERE TOPIC = ?";


    public Sentence find(Integer id) {

        Sentence sentence = null;
        try (Connection connection = DatabaseWorker.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SENTENCE)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String topic = resultSet.getString(2);
                String firstSentence = resultSet.getString(3);
                String secondCorrect = resultSet.getString(4);
                sentence = new Sentence(id, topic,firstSentence,secondCorrect);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return sentence;
    }

    public List<Integer> getAllIdTopic(String topic){
        List<Integer> ids = new ArrayList<>();
        try (Connection connection = DatabaseWorker.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_SENTENCE_OF_TOPIC)) {
            statement.setString(1, topic);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ids;
    }


}
