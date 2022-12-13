package ru.croc.project.statistics;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing user statistics.
 * Must have a Map for every type in the StatType enum.
 * Must have a getter for every type in the StatType enum.
 * Must have a setter for every type in the StatType enum.
 * May have a getter for Stat in the Stats enum.
 */
public class UserStatistics {
    private final String username;
    private final Map<String, Integer> integerStats;
    private final Map<String, String> stringStats;

    public UserStatistics(String username) {
        this.username = username;
        integerStats = new HashMap<>();
        stringStats = new HashMap<>();
    }

    public UserStatistics(String username, Map<String, Integer> integerStats, Map<String, String> stringStats) {
        this.username = username;
        this.integerStats = integerStats;
        this.stringStats = stringStats;
    }

    public String getUsername() {
        return username;
    }

    public int getIntegerStat(Stats stat) {
        return integerStats.get(stat.getName());
    }

    public String getStringStat(Stats stat) {
        return stringStats.get(stat.getName());
    }

    public void setIntegerStat(Stats stat, int value) {
        if(stat.getType() != StatType.Integer) return;
        integerStats.put(stat.getName(), value);
    }

    public void setStringStat(Stats stat, String value) {
        if(stat.getType() != StatType.String) return;
        stringStats.put(stat.getName(), value);
    }

    public int getWordsLearned() {
        return integerStats.get(Stats.WordsLearned.getName());
    }

    public int getTestsCompleted() {
        return integerStats.get(Stats.TestsCompleted.getName());
    }

    public String getFavouriteWord() {
        return stringStats.get(Stats.FavouriteWord.getName());
    }

    public String getProblemWord() {
        return stringStats.get(Stats.ProblemWord.getName());
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(username + ":");
        for (Stats stat : Stats.values()){
            res.append("\n");
            String str = "  %s(%s): ".formatted(stat.getName(), stat.getType());
            res.append(str);
            switch (stat.getType()) {
                case Integer -> res.append(getIntegerStat(stat));
                case String -> res.append(getStringStat(stat));
            }
        }
        return res.toString();
    }
}
