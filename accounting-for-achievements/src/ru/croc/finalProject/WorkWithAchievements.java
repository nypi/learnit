package ru.croc.finalProject;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class WorkWithAchievements {
    private PathToTheDB metricsOfLearning; // Данные для доступа к базе данных с метриками прогресса изучения
    private DataSetAchievements dataSetAchievements; // DataSet в котором хранятся нужные коллекции для работы программы

    private String url = "jdbc:h2:tcp://localhost/~/test";
    private String user = "sa";
    private String password = "123";

    WorkWithAchievements(String path, String[] metricsOfLearning) throws IOException {
        this.metricsOfLearning = new PathToTheDB(metricsOfLearning);
        dataSetAchievements = new DataSetAchievements(path);
    }

    /*
    Данный метод будет использоваться микросервисом, который сохраняет метрики по пользователем
    Как только будет изменена метрика по пользователю, то вызывается данный метод, который проверяет
    Нужно ли присваивать новое достижение пользователю, и если да - то это достижение добавляется в БД
    На вход принимается ID пользователя и метрика, по которой произведено изменение
     */
    public void updateAchievements(int id, String metrics) throws SQLException {
        int metricsValue; // текущее значение метрики, которая есть у пользователя
        int lastMetrics;  // значение метрики последнего достижения
        boolean Flag = true;
        try (Connection connection = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {
            String request = "SELECT * FROM " + " METRICSOFLEARNINGPROGRESS WHERE ID = " + id;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(request);
                resultSet.next();
                metricsValue = resultSet.getInt(metrics);
            }
            connection.close();
        } catch (SQLException ex) {
            throw ex;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String request = "SELECT * FROM ACHIEVEMENTS WHERE ID = " + id + " AND METRICS =" + " \'" + metrics + "\'" + " ORDER BY IDPROCESS DESC LIMIT 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(request);
                if (resultSet.next()) {
                    String lastAchievements = resultSet.getString("NAME");
                    lastMetrics = dataSetAchievements.getAuxiliaryMap().get(lastAchievements);
                } else {
                    Flag = false;
                    lastMetrics = dataSetAchievements.getAchievementConditions().get(metrics).get(0);
                }
            }
            ArrayList<Integer> currentList = dataSetAchievements.getAchievementConditions().get(metrics);

            int index = currentList.indexOf(lastMetrics);
            String request1 = "INSERT INTO ACHIEVEMENTS(ID , METRICS, NAME) VALUES(?,?,?)";
            String reuqest2 = "SELECT FROM ACHIEVEMENTS WHERE ID = " + id;
            String reuqest3 = "UPDATE METRICSOFLEARNINGPROGRESS SET MEGAMIND = ? WHERE ID = ?";
            String request4 = "SELECT * FROM METRICSOFLEARNINGPROGRESS WHERE ID = " + id;
            if (Flag) {
                for (int i = index + 1; i < currentList.size(); i++) {
                    if (metricsValue >= currentList.get(i)) {
                        int lastIndex;
                        try (PreparedStatement statement = connection.prepareStatement(request1)) {
                            statement.setInt(1, id);
                            statement.setString(2, metrics);
                            statement.setString(3, dataSetAchievements.getNameOfAchievements().get(metrics + currentList.get(i)));
                            statement.execute();

                            //теперь добавим +1 достижения в колоночку megamind
                            try (Connection connection1 = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {

                                try (Statement statement1 = connection1.createStatement()) {
                                    ResultSet resultSet = statement1.executeQuery(request4);
                                    resultSet.next();
                                    lastIndex = resultSet.getInt("MEGAMIND");

                                }


                                try (PreparedStatement statement1 = connection1.prepareStatement(reuqest3)) {
                                    statement1.setInt(1, lastIndex + 1);
                                    statement1.setInt(2, id);
                                    statement1.execute();
                                }
                                connection1.close();
                            }


                        }
                        // проверка на присвоения достижения, которое даётся за получение всех достижений
                        if ((lastIndex + 1) == 19) {
                            try (PreparedStatement statement1 = connection.prepareStatement(request1)) {
                                statement1.setInt(1, id);
                                statement1.setString(2, "MEGAMIND");
                                statement1.setString(3, "Мегамозг");
                                statement1.execute();
                            }
                        }
                    }
                }
            } else {
                for (int i = index; i < currentList.size(); i++) {
                    if (metricsValue >= currentList.get(i)) {
                        int lastIndex;
                        try (PreparedStatement statement = connection.prepareStatement(request1)) {

                            statement.setInt(1, id);
                            statement.setString(2, metrics);
                            statement.setString(3, dataSetAchievements.getNameOfAchievements().get(metrics + currentList.get(i)));
                            statement.execute();
                            //теперь добавим +1 достижения в колоночку megamind
                            try (Connection connection1 = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {

                                try (Statement statement1 = connection1.createStatement()) {
                                    ResultSet resultSet = statement1.executeQuery(request4);
                                    resultSet.next();
                                    lastIndex = resultSet.getInt("MEGAMIND");
                                }


                                try (PreparedStatement statement1 = connection1.prepareStatement(reuqest3)) {
                                    statement1.setInt(1, lastIndex + 1);
                                    statement1.setInt(2, id);
                                    statement1.execute();
                                }
                                connection1.close();
                            }
                        }
                        if ((lastIndex + 1) == 19) {
                            try (PreparedStatement statement1 = connection.prepareStatement(request1)) {
                                statement1.setInt(1, id);
                                statement1.setString(2, "MEGAMIND");
                                statement1.setString(3, "Мегамозг");
                                statement1.execute();
                            }
                        }

                    }
                }
            }
            System.out.println("Данные по достижениям у пользователя с id = " + id + " обновлены");
            connection.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }


    /*
    Данный метод показывает по ID какие достижения есть у пользователя
    А также по тем достижениям , которых у пользователя нет , показывается прогресс бар, то есть сколько осталось до данного достижения
     */
    public void getAllInformation(int id) throws SQLException {
        Map<String, Integer> achivementsOfUser = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {
            String request = "SELECT * FROM METRICSOFLEARNINGPROGRESS WHERE ID = " + id;
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(request);
                resultSet.next();
                for (String s : dataSetAchievements.getAchievementConditions().keySet()) {
                    achivementsOfUser.put(s, resultSet.getInt(s));
                }
            }
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        for (Map.Entry<String, Integer> entry : achivementsOfUser.entrySet()) {
            ArrayList<Integer> condition = dataSetAchievements.getAchievementConditions().get(entry.getKey());
            for (Integer x : condition) {
                if (entry.getValue() - x < 0) {
                    System.out.println("Достижение " + dataSetAchievements.getNameOfAchievements().get(entry.getKey() + x) + " : " + entry.getValue() + "/" + x);
                } else {
                    System.out.println("Достижение " + dataSetAchievements.getNameOfAchievements().get(entry.getKey() + x) + " : " + x + "/" + x);
                }
            }
        }
    }
}
