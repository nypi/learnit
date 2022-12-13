package ru.croc.project.database;

import ru.croc.project.database.access.Database;
import ru.croc.project.statistics.StatType;
import ru.croc.project.statistics.Stats;
import ru.croc.project.statistics.UserStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserStatisticsDAO {
    private final Database database;
    private final String userStatsTableName;

    public UserStatisticsDAO(Database database, String userStatsTableName) {
        this.database = database;
        this.userStatsTableName = userStatsTableName;
    }

    /**
     * Returns statistics of user in a database or null if user does not exist.
     * @param username of user to get statistics for
     * @return statistics of user
     * @throws SQLException if can't access database or stat columns don't exist
     */
    public UserStatistics getUserStatistics(String username) throws SQLException {
        String sql = "SELECT * FROM " + userStatsTableName + " WHERE username = ?";
        try (Connection connection = database.createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet result = statement.executeQuery();
                if(result.next()) {
                    Map<String, Integer> intStats = getIntegerStats(result);
                    Map<String, String> strStats = getStringStats(result);
                    return new UserStatistics(username, intStats, strStats);
                } else return null;
            }
        }
    }

    /**
     * Sets statistics for user in database if user statistics table contain a record for given user.
     * Creates statistics for user in database if user statistics table does not contain a record for given user.
     * @param statistics - user statistics to set
     * @throws SQLException - if can't connect to DB or userStatsTable does not exist
     */
    public void setUserStatistics(UserStatistics statistics) throws SQLException {
        String sql = "SELECT * FROM " + userStatsTableName + " WHERE username = ?";
        try (Connection connection = database.createConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, statistics.getUsername());
                ResultSet result = statement.executeQuery();
                if (!result.next()) {
                    // create stats if user stats does not exist in database
                    createStatsForUser(statistics.getUsername(), connection);
                }
                setStats(statistics, connection);
            }
        }
    }

    private void createStatsForUser(String username, Connection connection) throws SQLException {
        String sql = "INSERT INTO " + userStatsTableName + " (username) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }
    }

    //StatType methods, must be updated for every new StatType added

    private Integer getIntegerStat(ResultSet resultSet, Stats stat) throws SQLException {
        return resultSet.getInt(stat.getName().toUpperCase());
    }

    private String getStringStat(ResultSet resultSet, Stats stat) throws SQLException {
        return resultSet.getString(stat.getName().toUpperCase());
    }

    /**
     * Creates Map with every integer stat in Stats from given resultSet
     * @param resultSet - result set containing user to get stats for
     * @return Map with every integer stat in Stats for user in result set
     * @throws SQLException if resultSet is closed, can't access database or stat column does not exist
     */
    private Map<String, Integer> getIntegerStats(ResultSet resultSet) throws SQLException {
        Map<String, Integer> res = new HashMap<>();
        for (Stats stat : Stats.values()) {
            if(stat.getType() != StatType.Integer) continue;
            Integer value;
            value = getIntegerStat(resultSet, stat);
            res.put(stat.getName(), value);
        }
        return res;
    }

    /**
     * Creates Map with every string stat in Stats from given resultSet
     * @param resultSet - result set containing user to get stats for
     * @return Map with every string stat in Stats for user in result set
     * @throws SQLException if resultSet is closed, can't access database or stat column does not exist
     */
    private Map<String, String> getStringStats(ResultSet resultSet) throws SQLException {
        Map<String, String> res = new HashMap<>();
        for (Stats stat : Stats.values()) {
            if(stat.getType() != StatType.String) continue;
            String value;
            value = getStringStat(resultSet, stat);
            res.put(stat.getName(), value);
        }
        return res;
    }

    private void setIntegerStat(Integer value, String statName, String username, Connection connection) throws SQLException {
        String sql = "UPDATE " + userStatsTableName + " SET " + statName + " = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }

    private void setStringStat(String value, String statName, String username, Connection connection) throws SQLException {
        String sql = "UPDATE " + userStatsTableName + " SET " + statName + " = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            statement.setString(2, username);
            statement.executeUpdate();
        }
    }

    private void setStats(UserStatistics user, Connection connection) throws SQLException {
        for (Stats stat : Stats.values()) {
            switch (stat.getType()) {
                case Integer -> setIntegerStat(user.getIntegerStat(stat), stat.getName(), user.getUsername(), connection);
                case String -> setStringStat(user.getStringStat(stat), stat.getName(), user.getUsername(), connection);
            }
        }
    }
}
