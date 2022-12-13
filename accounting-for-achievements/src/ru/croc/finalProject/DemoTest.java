package ru.croc.finalProject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class DemoTest {
    public static void main(String[] args) throws IOException, SQLException {
        /*
        В аргументе командной строки хранятся данные для доступа к БД, где располагаются метрики прогресса изучения
        БД с метриками прогресса изучения заполняется другим микросервисом
        Данные подаются в командной строке в  следующем порядке
        1. URL до БД
        2. Password
        3. User
         */

        if (args.length < 3) {
            throw new IllegalArgumentException("Некорректно введены данные для доступа к БД");
        }
        WorkWithAchievements test = new WorkWithAchievements("C:/Users/stepa/IdeaProjects/Croc/src/ru/croc/finalProject/Achievements.txt", args);
        Menu test2 = new Menu();
        test2.menu(test);
    }
}
