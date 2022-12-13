package ru.croc.project.test;

import ru.croc.project.database.UserDatabaseController;
import ru.croc.project.statistics.Stats;
import ru.croc.project.statistics.UserStatistics;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class CommandLineInterface {
    private static final String[] actions = {
            "Выход",
            "Установить статистику пользователю",
            "Посмотреть статистику пользователя"
    };

    public static int getIntInput(Predicate<Integer> isValidInput, String tryAgainMessage, Scanner scanner) {
        boolean wrongInputFlag;
        int input;
        try {
            input = scanner.nextInt();
            wrongInputFlag = false;
        } catch (InputMismatchException e) {
            wrongInputFlag = true;
            scanner.next();
            input = 0;
        }

        while (!isValidInput.test(input) || wrongInputFlag) {
            System.out.println("Неверный ввод.");
            System.out.println(tryAgainMessage);
            try {
                input = scanner.nextInt();
                wrongInputFlag = false;
            } catch (InputMismatchException e) {
                wrongInputFlag = true;
                scanner.next();
                input = 0;
            }
        }
        return input;
    }

    public static String getStringInput(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.isEmpty())
            input = scanner.nextLine();
        return input;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        CommandLineInterface cli = new CommandLineInterface();
        cli.mainLoop();
    }

    private final UserDatabaseController controller;

    public CommandLineInterface() throws ClassNotFoundException, SQLException {
        controller = new UserDatabaseController();
        controller.clearDatabase();
        controller.initDatabase();
    }

    public void mainLoop() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Что хотите сделать?");
        for(int i = 0; i < actions.length; i++) {
            System.out.println(i + ") " + actions[i]);
        }
        int input = getIntInput((a) -> a >= 0 && a < actions.length,
                "Введите число от 0 до " + (actions.length - 1),
                scanner);

        while (input != 0) {
            switch (input) {
                case 1 -> setUser(createStatisticsFromInput(scanner));
                case 2 -> outUser(scanner);
            }

            System.out.println();
            System.out.println("Хотите ли сделать что-то ещё?");
            for(int i = 0; i < actions.length; i++) {
                System.out.println(i + ") " + actions[i]);
            }
            input = getIntInput((a) -> a >= 0 && a < actions.length,
                    "Введите число от 0 до " + (actions.length - 1),
                    scanner);
        }
    }

    private UserStatistics createStatisticsFromInput(Scanner scanner) {
        System.out.println("Введите имя пользователя:");
        String username = getStringInput(scanner);

        UserStatistics statistics = new UserStatistics(username);

        for (Stats stat : Stats.values()){
            System.out.printf("%s(%s): ", stat.getName(), stat.getType());
            switch (stat.getType()) {
                case Integer -> {
                    int value = getIntInput((a) -> true, "", scanner);
                    statistics.setIntegerStat(stat, value);
                }
                case String -> {
                    String value = getStringInput(scanner);
                    statistics.setStringStat(stat, value);
                }
            }
        }

        return statistics;
    }

    private void setUser(UserStatistics statistics) throws SQLException {
        controller.setUserStatistics(statistics);
    }

    private void outUser(Scanner scanner) throws SQLException {
        System.out.println("Введите имя пользователя:");
        String username = getStringInput(scanner);

        UserStatistics  userStatistics = controller.getUserStatistics(username);
        if(userStatistics != null)
            System.out.println(userStatistics);
        else
            System.out.println("Not found");
    }
}
