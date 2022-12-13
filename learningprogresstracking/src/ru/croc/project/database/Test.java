package ru.croc.project.database;

import ru.croc.project.statistics.Stats;
import ru.croc.project.statistics.UserStatistics;

import java.sql.SQLException;

public class Test {
    public static void testGetUserStats(UserDatabaseController controller, String username) throws SQLException {
        UserStatistics  userStatistics = controller.getUserStatistics(username);
        if(userStatistics != null)
            System.out.println(userStatistics);
        else
            System.out.println("Not found");
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDatabaseController controller = new UserDatabaseController();
        controller.clearDatabase();
        controller.initDatabase();

        testGetUserStats(controller, "testuser");

        UserStatistics newUserStats = new UserStatistics("testuser");
        newUserStats.setIntegerStat(Stats.TestsCompleted, 1);
        newUserStats.setIntegerStat(Stats.WordsLearned, 2);
        newUserStats.setStringStat(Stats.ProblemWord, "Hello");
        newUserStats.setStringStat(Stats.FavouriteWord, "World");

        controller.setUserStatistics(newUserStats);
        testGetUserStats(controller,"testuser");

        newUserStats = controller.getUserStatistics("testuser");
        newUserStats.setStringStat(Stats.ProblemWord, "No");
        controller.setUserStatistics(newUserStats);
        testGetUserStats(controller,"testuser");
    }
}
