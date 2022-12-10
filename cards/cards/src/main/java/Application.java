import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Привет! Давай выучим несколько новых английских слов!");
        Thread.sleep(2000);
        System.out.println("Выбери тему на которую ты хочешь выучить слова!\n1 - Супергерои\n2 - Еда\n3 - Базовые английские слова");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        Deck firstDeck = new Deck();

        switch (choice) {
            case (1):
                firstDeck.fillDeck(Path.of("src/main/resources/superherous.txt"));

            case (2):


            case (3):


            default:
                System.out.println("Ваш выбор записан! Начнем обучение");

        }

        for (Card card : firstDeck) {
            card.displayCard();
            System.out.println();
            System.out.println("1 - показать перевод");
            int getCardTranslation = scanner.nextInt();
            if (getCardTranslation == 1) {
                card.displayTranslatedCard();
            }

        }
    }

}
