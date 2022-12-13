package project.flashCards.userInterfaces;

import project.flashCards.LeitnerSystem.LeitnerSystem;
import project.flashCards.recomendationSystem.RecommendationSystem;

import java.util.Scanner;

/**
 * Класс, соответствующий главному меню приложения.*/
public class MainMenu {

    /**Метод выводит интерфейс главного меню в консоль.*/
    public void printMenu(){
        String menuStr = """
                1) Посмотреть все слова в системе/какую-то конкретную группу
                2) Узнать рекомендацию на сегодня
                3) Повторить произвольную группу со словами
                4) Изменить набор слов в системе (добавить/удалить слова)
                0) Выйти из приложения
                """;
        System.out.println(menuStr);
    }

    /**Метод реализующий интерфейс главного меню*/
    public void menu(){

        Scanner scan = new Scanner(System.in);
        int x = 0;
        boolean checker = true;

        while (checker){
            printMenu();
            try {
                x = scan.nextInt();
            } catch (NumberFormatException e){
                System.out.println("Неверный ввод");
            }
            switch (x) {
                case 1 -> new WordsPrinterMenu().menu();
                case 2 -> RecommendationSystem.getRecommendation();
                case 3 -> {
                    System.out.println("Введите номер группы");
                    int group = scan.nextInt();
                    LeitnerSystem.repeatGroupByNum(group);
                }
                case 4 -> new SystemStateChangeMenu().menu();
                case 0 -> checker = false;
            }
        }
        System.out.println("До свидания!");
    }
}