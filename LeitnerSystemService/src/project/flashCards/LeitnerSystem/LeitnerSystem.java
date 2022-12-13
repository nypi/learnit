package project.flashCards.LeitnerSystem;

import project.flashCards.iteractionWithDB.WordDAO;

import java.util.Map;
import java.util.Scanner;

/**Класс реализует систему Лейтнера,
 * которая позволяет пользователю повторять перевод английских слов
 * с заранее заданной переодичнсоть.*/
public class LeitnerSystem {
    /**Метод позволяет повторить слова из конкретной группы*/
    public static void repeatGroupByNum(int groupNum){

        System.out.println("Введите '0', если хотите прекратить повторение группы №" + groupNum);

        WordDAO wordDAO = new WordDAO();
        Map<Integer, Word> words = wordDAO.getGroupByNum(groupNum);
        for (Map.Entry<Integer, Word> entry : words.entrySet()){
            Word word = entry.getValue();
            System.out.println("Введите перевод для " + word.getEnglish());
            String translatedWord = new Scanner(System.in).nextLine().toLowerCase();

            if (translatedWord.equals("0")){
                return;
            }

            if (translatedWord.equals(word.getRussian().toLowerCase())){
                System.out.println("Правильно!");
                wordDAO.incrementGroup(word.getId());
            } else{
                System.out.println("Неправильно!");
                wordDAO.setGroup(word.getId(), 1);
            }
        }
    }
}