package ru.croc.finalProject;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class WorkWithAchievements {
    private PathToTheDB metricsOfLearning; // Данные для доступа к базе данных с метриками прогресса изучения
    private DataSetAchievements dataSetAchievements; // DataSet в котором хранятся нужные коллекции для работы программы

    private String url = "jdbc:h2:tcp://localhost/~/test"; // url для БД с достижениями
    private String user = "sa"; // user для БД с достижениями
    private String password = "123"; // passwords для БД с достижениями

    WorkWithAchievements(String path, String[] metricsOfLearning) throws IOException {
        this.metricsOfLearning = new PathToTheDB(metricsOfLearning);
        dataSetAchievements = new DataSetAchievements(path);
    }


    public void updateAchievements(int id, String metrics) throws SQLException {
        int metricsValue; // текущее значение метрики, которая есть у пользователя
        int lastMetrics;  // значение метрики последнего достижения, которое получил пользователь по заданной метрики
        boolean Flag = true;
        // Для начала подключимся к БД с метриками достижений и извелчём значение metricsValue
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
            // Для начала найдём последнее достижение пользователя по заданной метрики
            String request = "SELECT * FROM ACHIEVEMENTS WHERE ID = " + id + " AND METRICS =" + " \'" + metrics + "\'" + " ORDER BY IDPROCESS DESC LIMIT 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(request);
                if (resultSet.next()) {
                    // Сюда мы попадаем, если пользователь имеет какие-то достижения по заданной метрики
                    String lastAchievements = resultSet.getString("NAME");
                    lastMetrics = dataSetAchievements.getAuxiliaryMap().get(lastAchievements);
                    // По итогу благодяра мапе AuxiliaryMap получили численное значение за которое достаётся это достижение
                } else {
                    // если пользователь не имеет никаких достижений, то берём самое первое численно значение в мапе
                    // AchievementConditions , где хранятся метрики и array list значение граничных, которые определяют различные достижения
                    Flag = false;
                    lastMetrics = dataSetAchievements.getAchievementConditions().get(metrics).get(0);
                }
            }
            ArrayList<Integer> currentList = dataSetAchievements.getAchievementConditions().get(metrics);

            int index = currentList.indexOf(lastMetrics);
            // получаем index численного значения последнего полученного достижения
            // этот index будем использоваться для того, чтобы знать с какой численной метки проверять достижения
            String request1 = "INSERT INTO ACHIEVEMENTS(ID , METRICS, NAME) VALUES(?,?,?)";
            String reuqest2 = "SELECT FROM ACHIEVEMENTS WHERE ID = " + id;
            String reuqest3 = "UPDATE METRICSOFLEARNINGPROGRESS SET MEGAMIND = ? WHERE ID = ?";
            String request4 = "SELECT * FROM METRICSOFLEARNINGPROGRESS WHERE ID = " + id;
            // Разделение на Flag нужно так-как если пользователь не получал никакого достижения, то нужно проверять с нулевого индекса(то есть начиная с index) array list где хранятся условия для получения
            // Если пользователь получал достижения уже, то нужно проверять начиная с (index + 1)
            if (Flag) {
                for (int i = index + 1; i < currentList.size(); i++) {
                    if (metricsValue >= currentList.get(i)) {
                        // Сюда мы попадаем, если пользователь набрал нужное количество "единиц" для получения достижений
                        int lastIndex;
                        // Добавляем новое достижений в БД
                        // Новое достижение подбирается благодаря мапе NameOfAchievements, которая хранит ключ - метркиа+число, а значение - названия достижения
                        // Пример ( WORDS1000 - "Начало положено" )
                        try (PreparedStatement statement = connection.prepareStatement(request1)) {
                            statement.setInt(1, id);
                            statement.setString(2, metrics);
                            statement.setString(3, dataSetAchievements.getNameOfAchievements().get(metrics + currentList.get(i)));
                            statement.execute();
                            // Теперь добавим +1 достижения в колоночку megamind в БД с метриками прогресса изучения
                            try (Connection connection1 = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {
                                // Получаем последнее значение поля MEGAMIND
                                try (Statement statement1 = connection1.createStatement()) {
                                    ResultSet resultSet = statement1.executeQuery(request4);
                                    resultSet.next();
                                    lastIndex = resultSet.getInt("MEGAMIND");

                                }

                                // Обновляем значение поля MEGAMIND
                                try (PreparedStatement statement1 = connection1.prepareStatement(reuqest3)) {
                                    statement1.setInt(1, lastIndex + 1);
                                    statement1.setInt(2, id);
                                    statement1.execute();
                                }
                                connection1.close();
                            }


                        }
                        // проверка на присвоения достижения, которое даётся за получение всех достижений( достижение MEGAMIND)
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
                        // Сюда мы попадаем, если пользователь набрал нужное количество "единиц" для получения достижений
                        int lastIndex;
                        // Добавляем новое достижений в БД
                        // Новое достижение подбирается благодаря мапе NameOfAchievements, которая хранит ключ - метркиа+число, а значение - названия достижения
                        // Пример ( WORDS1000 - "Начало положено" )
                        try (PreparedStatement statement = connection.prepareStatement(request1)) {
                            statement.setInt(1, id);
                            statement.setString(2, metrics);
                            statement.setString(3, dataSetAchievements.getNameOfAchievements().get(metrics + currentList.get(i)));
                            statement.execute();
                            // Теперь добавим +1 достижения в колоночку megamind в БД с метриками прогресса изучения
                            try (Connection connection1 = DriverManager.getConnection(metricsOfLearning.getUrl(), metricsOfLearning.getUser(), metricsOfLearning.getPassword())) {
                                // Получаем последнее значение поля MEGAMIND
                                try (Statement statement1 = connection1.createStatement()) {
                                    ResultSet resultSet = statement1.executeQuery(request4);
                                    resultSet.next();
                                    lastIndex = resultSet.getInt("MEGAMIND");
                                }
                                // Обновляем значение поля MEGAMIND
                                try (PreparedStatement statement1 = connection1.prepareStatement(reuqest3)) {
                                    statement1.setInt(1, lastIndex + 1);
                                    statement1.setInt(2, id);
                                    statement1.execute();
                                }
                                connection1.close();
                            }
                        }
                        // проверка на присвоения достижения, которое даётся за получение всех достижений( достижение MEGAMIND
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
        // Для начала загрузим из БД с метриками прогресса изучения мапу с текущими значениями пользователя
        // Мапа будет иметь вид МЕТРИКА - ЗНАЧЕНИЕ
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
        // Собственно сам вывод прогресс-бара
        for (Map.Entry<String, Integer> entry : achivementsOfUser.entrySet()) {
            ArrayList<Integer> condition = dataSetAchievements.getAchievementConditions().get(entry.getKey());
            for (Integer x : condition) {
                // Если разность меньше нуля, то это означает что достижение ещё не получено, и для прогресс-бара достаточно вывести entry.getValue()

                if (entry.getValue() - x < 0) {
                    // Вывод конкретного достижения осуществляется за счёт мапы, где ключу МЕТРИКА+ЧИСЛО сопопставляется название достижения
                    // И как раз entry.getKey()+x это и есть метрика + число
                    System.out.println("Достижение " + dataSetAchievements.getNameOfAchievements().get(entry.getKey() + x) + " : " + entry.getValue() + "/" + x);
                } else {
                    //Если разность больше или равна нулю, значит достижение уже получено
                    System.out.println("Достижение " + dataSetAchievements.getNameOfAchievements().get(entry.getKey() + x) + " : " + x + "/" + x);
                }
            }
        }
        // ВАЖНОЕ ЗАМЕЧАНИЕ : мы здесь не обращались к БД с достижениями, но наличие всех достижений гарантируется
        // Дело в том, что когда другой микросервис обновляет какую-то метрику пользователя, то он сразу вызывает метод updateAchievements(int id, String metrics)
        // Который уже в свою очередь проверяет, нужно ли присваивать достижение
    }
}
