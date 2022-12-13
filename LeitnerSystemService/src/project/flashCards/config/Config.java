package project.flashCards.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс хранящий конфигурацию системы Лейтнера*/
public class Config {

    /**Уникальный идентификатор пользователя*/
    private static String USER_LOGIN;

    /**Ссылка на используемую базу данных*/
    private static String DB_URL;

    /**Имя пользователя, через которого осуществляется управление БД*/
    private static String USER;

    /**Пароль от учётной записи БД*/
    private static String PASSWORD;

    /**Количество групп */ //это надо сделать
    private static int GROUP_NUMBER;

    /**Частота, с которой надо повторять каждую из групп*/
    private static List<Integer> FREQUENCY;

    /**
     * Метод получающий конфигурацию системы Лейтнера из файла
     * @param path - путь к файлу, который содержит параметры системы
     * */
    public static void getConfig(String path){
        try (FileReader fr = new FileReader(path);
             BufferedReader reader = new BufferedReader(fr)) {

            USER_LOGIN = reader.readLine();
            DB_URL = reader.readLine();
            USER = reader.readLine();
            PASSWORD = reader.readLine();

            String line = reader.readLine();
            int groupNum = Integer.parseInt(line);

            List<Integer> frequency = new ArrayList<>();
            line = reader.readLine();
            String[] arr = line.split(" ");
            for (String elem : arr) {
                frequency.add(Integer.parseInt(elem));
            }

            GROUP_NUMBER = groupNum;
            FREQUENCY = frequency;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**Геттеры*/

    public static String getUserLogin() {
        return USER_LOGIN;
    }

    public static String getDBURL() {
        return DB_URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static int getGroupNumber() {
        return GROUP_NUMBER;
    }

    public static List<Integer> getFREQUENCY() {
        return FREQUENCY;
    }

}
