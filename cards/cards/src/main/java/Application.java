import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Application {
    private static boolean applicationIndex = true;
    static Deck firstDeck = new Deck();
    static Deck secondDeck = new Deck(1);
    static Deck thirdDeck = new Deck(2);

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Привет! Давай выучим несколько новых английских слов!");
        System.out.println("Выбери тему на которую ты хочешь выучить слова!\n1 - Супергерои\n2 - Еда\n3 - Базовые английские слова");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();


        switch (choice) {
            case (1):
                firstDeck.fillDeck(Path.of("src/main/resources/superherous.txt"));
                break;
            case (2):
                firstDeck.fillDeck(Path.of("src/main/resources/meal.txt"));
                break;
            case (3):
                firstDeck.fillDeck(Path.of("src/main/resources/basic.txt"));
                break;

            default:
                System.out.println("Ваш выбор записан! Начнем обучение");

        }

        while (applicationIndex) {
            for (Card card : firstDeck) {
                card.displayCard();
                System.out.println();
                System.out.println("1 - показать перевод");
                int getCardTranslation = scanner.nextInt();
                if (getCardTranslation == 1) {
                    card.displayTranslatedCard();
                }
                System.out.println("Вы правильно перевели это слово?\n" +
                        "yes - правильно перевел\n" +
                        "no - неправильно перевел\n" +
                        "delete - удалить эту карточку\n" +
                        "menu - перейти в меню");

                String action = scanner.next();
                switch (action) {
                    case ("yes"):
                        System.out.println("Отлично!");
                        secondDeck.addNewCard(card);
                        //firstDeck.deleteCard(card);
                        firstDeck.iterator().remove();

                        break;

                    case ("no"):
                        System.out.println("Не расстраивайтесь, у вас всё получится!");

                        break;
                    case ("delete"):
                        firstDeck.iterator().remove();
                        break;
                    case ("menu"):
                        menu(firstDeck);
                        break;


                }
            }
            System.out.println(secondDeck.isActive());

            for (Card card : secondDeck) {
                card.displayCard();
                System.out.println();
                System.out.println("1 - показать перевод");
                int getCardTranslation = scanner.nextInt();
                if (getCardTranslation == 1) {
                    card.displayTranslatedCard();
                }
                System.out.println("Вы правильно перевели это слово?\n" +
                        "yes - правильно перевел\n" +
                        "no - неправильно перевел\n" +
                        "delete - удалить эту карточку\n" +
                        "menu - перейти в меню");

                String action = scanner.next();
                switch (action) {
                    case ("yes"):
                        System.out.println("Отлично!");
                        thirdDeck.addNewCard(card);
                        //firstDeck.deleteCard(card);
                        secondDeck.iterator().remove();
                        break;

                    case ("no"):
                        System.out.println("Не расстраивайтесь, у вас всё получится!");
                        firstDeck.addNewCard(card);

                        break;
                    case ("delete"):
                        secondDeck.iterator().remove();
                        break;
                    case ("menu"):
                        menu(secondDeck);
                        break;

                    default:
                }
            }
            System.out.println(thirdDeck.isActive());
            for (Card card : thirdDeck) {

                card.displayCard();
                System.out.println();
                System.out.println("1 - показать перевод");
                int getCardTranslation = scanner.nextInt();
                if (getCardTranslation == 1) {
                    card.displayTranslatedCard();
                }
                System.out.println("Вы правильно перевели это слово?\n" +
                        "yes - правильно перевел\n" +
                        "no - неправильно перевел\n" +
                        "delete - удалить эту карточку\n" +
                        "menu - перейти в меню");

                String action = scanner.next();
                switch (action) {
                    case ("yes"):
                        System.out.println("Отлично!");

                        break;

                    case ("no"):
                        System.out.println("Не расстраивайтесь, у вас всё получится!");
                        secondDeck.addNewCard(card);
                        break;
                    case ("delete"):
                        thirdDeck.iterator().remove();
                        break;
                    case ("menu"):
                        menu(thirdDeck);
                        break;

                    default:
                }
            }


        }
    }

    private static void menu(Deck deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Меню:\n" +
                "add - добавить новую карточку\n" +
                "resume - продолжить учить слова\n" +
                "end - закончить изучение слов\n");
        String menuChoice = scanner.next();
        switch (menuChoice) {

            case ("add"):
                System.out.println("Какое слово вы хотете добавить?");
                String newCardWord = scanner.next();
                deck.addNewCard(newCardWord);
                break;
            case ("resume"):

                break;
            case ("end"):
                System.out.println("Поздравляем, теперь вы хорошо знаете " + thirdDeck.getWordQuantity() + " слов!\n" +
                        "До новых встреч!");
                applicationIndex = false;
            default:
        }
    }
}

