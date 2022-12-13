package project.flashCards.userInterfaces;

import project.flashCards.LeitnerSystem.Word;
import project.flashCards.iteractionWithDB.WordDAO;

import java.util.Map;
import java.util.Scanner;

/**Класс реализующий вывод слов в консоль.*/
public class WordsPrinterMenu {

    /**Метод выводит интерфейс главного меню в консоль.*/
    public void printShowMenu(){
        String menuStr = """
                1) Вывести все слова, которые есть в системе
                2) Вывести все слова, которые соответствуют определенной группе
                0) Вернуться назад
                """;
        System.out.println(menuStr);
    }

    /**Метод реализует заданный интерфейс*/
    public void menu(){
        Scanner scan = new Scanner(System.in);
        int x = 0;

        boolean checker = true;

        while (checker){
            printShowMenu();
            try {
                x = scan.nextInt();
            } catch (NumberFormatException e){
                System.out.println("Неверный ввод");
            }
            switch (x) {
                case 1 -> printAllWords();
                case 2 -> {
                    System.out.println("Введите номер группы, которая Вас интересует");
                    Scanner in = new Scanner(System.in);
                    printCurrentGroup(in.nextInt());
                }
                case 0 -> checker = false;

            }
        }
    }

    /**Метод выводит в консоль все слова, которые есть в БД*/
    public void printAllWords(){
        Map<Integer, Word> words = new WordDAO().getAllWords();

        for (Map.Entry<Integer, Word> entry : words.entrySet()) {
            System.out.println(entry.getValue()+"\n");
        }
    }

    /**Метод выводит в консоль все слова, соответствующие заданной группе
     * @param groupNum - номер группы, слова которой будут выведены
     * */
    public void printCurrentGroup(int groupNum){
        Map<Integer, Word> words = new WordDAO().getGroupByNum(groupNum);
        for (Map.Entry<Integer, Word> entry : words.entrySet()) {
            System.out.println(entry.getValue()+"\n");
        }
    }
}