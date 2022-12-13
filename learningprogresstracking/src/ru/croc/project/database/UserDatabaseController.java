package ru.croc.project.database;

import ru.croc.project.database.access.*;
import ru.croc.project.statistics.Stats;
import ru.croc.project.statistics.UserStatistics;

import java.sql.*;

public class UserDatabaseController {
    private static final String userStatsTableName = "USERSTATS";

    private final Database database;
    private final UserStatisticsDAO userStatisticsDAO;

    public UserDatabaseController() throws ClassNotFoundException {
        database = new H2Database();
        userStatisticsDAO = new UserStatisticsDAO(database, userStatsTableName);
    }

    public UserDatabaseController(Database database) {
        this.database = database;
        userStatisticsDAO = new UserStatisticsDAO(database, userStatsTableName);
    }

    public void initDatabase() throws SQLException {
        try (Connection connection = database.createConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet userStatsTable = meta.getTables(null, null, userStatsTableName, null);
            if (!userStatsTable.next()) {
                // create product table if not exists
                try (Statement statement = connection.createStatement()) {
                    String sql = getCreateTableSqlForStats();
                    statement.executeUpdate(sql);
                }
            } else {
                // add columns for stats if needed
                addStatsColumns(connection, meta);
            }
        }
    }

    public void clearDatabase() throws SQLException {
        try (Connection connection = database.createConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet userStatsTable = meta.getTables(null, null, userStatsTableName, null);
            if (userStatsTable.next()) {
                String sql = "DROP TABLE " + userStatsTableName;
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                }
            }
        }
    }

    public UserStatistics getUserStatistics(String username) throws SQLException {
        return userStatisticsDAO.getUserStatistics(username);
    }

    public void setUserStatistics(UserStatistics statistics) throws SQLException {
        userStatisticsDAO.setUserStatistics(statistics);
    }

    public Database getDatabase() {
        return database;
    }

    private static String getCreateTableSqlForStats() {
        StringBuilder res = new StringBuilder("CREATE TABLE ");
        res.append(userStatsTableName);
        res.append("(id INT PRIMARY KEY AUTO_INCREMENT,username VARCHAR(255) NOT NULL");
        for(Stats stat : Stats.values()) {
            res.append(',');
            res.append(stat.getName()).append(" ");
            res.append(stat.getType().getSqlType());
        }
        res.append(");");
        return res.toString();
    }

    private void addStatsColumns(Connection connection, DatabaseMetaData meta) throws SQLException {
        for (Stats stat : Stats.values()) {
            String statName = stat.getName().toUpperCase();
            ResultSet statColumn = meta.getColumns(null, null,
                    userStatsTableName, statName);
            if (!statColumn.next()) {
                try (Statement statement = connection.createStatement()) {
                    String sql = "ALTER TABLE " + userStatsTableName +
                            " ADD " + statName + " " + stat.getType().getSqlType();
                    statement.executeUpdate(sql);
                }
            }
        }
    }
}
