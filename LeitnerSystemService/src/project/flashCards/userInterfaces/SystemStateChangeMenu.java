package project.flashCards.userInterfaces;

import project.flashCards.LeitnerSystem.Word;
import project.flashCards.iteractionWithDB.WordDAO;

import java.util.Scanner;

/**Класс, позволяющий пользователю влиять на множество слов, хранящихся в базе данных.*/
public class SystemStateChangeMenu {

    /**Метод выводит интерфейс взаимодействия с БД в консоль.*/
    public void printShowMenu(){
        String menuStr =
                """
                        1) Добавить новое слово
                        2) Удалить слово из системы
                        3) Удалить все слова из системы
                        0) Вернуться назад
                        """;
        System.out.println(menuStr);
    }

    /**Метод реализует интерфейс взаимодействия с БД*/
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
                case 1 -> addNewWord();
                case 2 -> deleteWord();
                case 3 -> new WordDAO().dropDB();
                case 0 -> checker = false;
            }
        }
        System.out.println("До свидания!");
    }

    /**Позволяет пользователю добавить слово в БД*/
    public void addNewWord(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите слово на русском");
        String russian = scanner.nextLine();

        System.out.println("Введите слово на английском");
        String english = scanner.nextLine();

        new WordDAO().addWordToDB(new Word(russian, english));
    }

    /**Позволяет пользователю удалить слово из БД*/
    public void deleteWord(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите слово на русском или на английском");
        String russianEnglishWord = scanner.nextLine();
        new WordDAO().deleteWordFromDB(russianEnglishWord);
    }
}
