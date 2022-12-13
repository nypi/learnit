package ru.croc.finalProject;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public void menu(WorkWithAchievements test) throws SQLException {
        int n;
        int id;
        Scanner f = new Scanner(System.in);
        while (true) {
            System.out.println("1.Обновить достижения");
            System.out.println("2.Посмотреть прогресс-бар участника");
            System.out.println("3.Выход");
            n = f.nextInt();

            switch (n) {
                case 1 -> {
                    String metrics;
                    System.out.println("Введите id пользователя");
                    id = f.nextInt();
                    System.out.println("Введите метрику");
                    metrics = f.next();
                    test.updateAchievements(id, metrics);
                }
                case 2 -> {
                    System.out.println("Введите id пользователя");
                    id = f.nextInt();
                    test.getAllInformation(id);
                }
                case 3 -> System.exit(0);
            }
        }

    }
}
