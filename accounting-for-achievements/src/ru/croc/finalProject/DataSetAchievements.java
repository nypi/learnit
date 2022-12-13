package ru.croc.finalProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSetAchievements {
    private Map<String, ArrayList<Integer>> achievementConditions = new HashMap<>(); // структура мапы : МЕТРИКА - лист с граничными достиженями  для этой метрики
    private Map<String, String> nameOfAchievements = new HashMap<>(); // структура мапы : МЕТРИКА+ГРАНИЧНОЕ_ЗНАЧЕНИЕ - Название достижения
    private Map<String, Integer> auxiliaryMap = new HashMap<>(); // структура мапы : Название_достижения - граничено значение для его получения

    public DataSetAchievements(String path) throws IOException {
        Path pathToTheAchievements = Paths.get(path);
        try {
            List<String> allLinesInFile = Files.readAllLines(pathToTheAchievements);
            for (String s : allLinesInFile) {
                String[] currentLine = s.split(",");
                if (achievementConditions.containsKey(currentLine[0])) {
                    achievementConditions.get(currentLine[0]).add(Integer.parseInt(currentLine[1]));
                } else {
                    achievementConditions.put(currentLine[0], new ArrayList<>(List.of(Integer.parseInt(currentLine[1]))));
                }
                auxiliaryMap.put(currentLine[2], Integer.parseInt(currentLine[1]));
                nameOfAchievements.put(currentLine[0] + currentLine[1], currentLine[2]);
            }
        } catch (IOException ex) {
            throw ex;
        }
    }

    public Map<String, ArrayList<Integer>> getAchievementConditions() {
        return achievementConditions;
    }

    public Map<String, String> getNameOfAchievements() {
        return nameOfAchievements;
    }

    public Map<String, Integer> getAuxiliaryMap() {
        return auxiliaryMap;
    }
}
