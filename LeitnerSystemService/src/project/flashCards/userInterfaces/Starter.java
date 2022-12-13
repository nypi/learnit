package project.flashCards.userInterfaces;

import project.flashCards.LeitnerSystem.Word;
import project.flashCards.iteractionWithDB.WordDAO;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, метод которого позволяет пользователю решить,
 * хочет ли он добавить новые слова из указанного файла*/
public class Starter {
    /**
     * Метод добавляет интерфейс, с помощью которого пользователь решает, будут ли добавлены слова.
     * @param pathToFileWithNewWords - путь к файлу, который содержит новые слова в формате "pencil; карандаш"
     * */
    public void addingNewWordsFromFile(String pathToFileWithNewWords){
        System.out.println("Хотите загрузить новые слова из файла?");
        String answer = new Scanner(System.in).nextLine();

        if (answer.equalsIgnoreCase("да") || answer.equalsIgnoreCase("yes")) {
            List<Word> words = Word.getNewWordsFromFile(pathToFileWithNewWords);

            WordDAO wordDAO = new WordDAO();

            for (Word word : words) {
                wordDAO.addWordToDB(word);
            }
            System.out.println("Слова были успешны добавлены!");
        } else {
            System.out.println("Слова не были добавлены");
        }
    }
}
