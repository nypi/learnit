import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Application {
    static Deck firstDeck = new Deck();//первая колода с новыми или плохоизученными словами
    static Deck secondDeck = new Deck(1);//вторая колода со средне изученными словами
    static Deck thirdDeck = new Deck(2);//третья колода с хорошо изученными словами
    //текст меню
    private static final String actions = """
            Вы правильно перевели это слово?
            yes - правильно перевел
            no - неправильно перевел
            delete - удалить эту карточку
            menu - перейти в меню""";

    //основная функция приложения
    public static void main(String[] args) throws IOException {
        System.out.println("Привет! Давай выучим несколько новых английских слов!");
        System.out.println("Выбери тему на которую ты хочешь выучить слова!\n1 - Супергерои\n2 - Еда\n3 - Базовые английские слова");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();//выбор одной группы слов по темам, группы созданы для примера работы


        switch (choice) {
            case (1) -> firstDeck.fillDeck(Path.of("src/main/resources/superherous.txt"));
            case (2) -> firstDeck.fillDeck(Path.of("src/main/resources/meal.txt"));
            case (3) -> firstDeck.fillDeck(Path.of("src/main/resources/basic.txt"));
            default -> System.out.println("Ваш выбор записан! Начнем обучение");
        }


        while (true) {//основной цикл работы приложения
            for (Card card : firstDeck) {//первая колода
                card.displayCard();
                System.out.println();
                System.out.println("1 - показать перевод");
                int getCardTranslation = scanner.nextInt();
                if (getCardTranslation == 1) {
                    card.displayTranslatedCard();
                }
                System.out.println(actions);

                String action = scanner.next();
                switch (action) {
                    case ("yes") -> {
                        System.out.println("Отлично!");
                        secondDeck.addNewCard(card);//перемещаем карту во вторую колоду
                        firstDeck.prepareCardDeletion(card);
                    }
                    case ("no") -> System.out.println("Не расстраивайтесь, у вас всё получится!");//оставляем карту
                    case ("delete") -> firstDeck.prepareCardDeletion(card);//удаляем карту безвозвратно
                    case ("menu") -> menu();//переход в меню
                }

            }
            if (!firstDeck.deckIsEmpty() && !thirdDeck.isActive() && !secondDeck.isActive()) {
                System.out.println("Можете пока отдохнуть");//сообщение на случай прохождения первой колоды и неактивности других
            }
            firstDeck.deleteUnnecessaryCards();//удаление отложенных карт

            if (secondDeck.isActive()) {
                for (Card card : secondDeck) {
                    System.out.println();
                    card.displayCard();
                    System.out.println();
                    System.out.println("1 - показать перевод");
                    int getCardTranslation = scanner.nextInt();
                    if (getCardTranslation == 1) {
                        card.displayTranslatedCard();
                    }
                    System.out.println(actions);

                    String action = scanner.next();
                    switch (action) {
                        case ("yes") -> {
                            System.out.println("Отлично!");
                            thirdDeck.addNewCard(card);
                            //firstDeck.deleteCard(card);
                            secondDeck.prepareCardDeletion(card);
                        }
                        case ("no") -> {
                            System.out.println("Не расстраивайтесь, у вас всё получится!");
                            firstDeck.addNewCard(card);
                        }
                        case ("delete") -> secondDeck.prepareCardDeletion(card);
                        case ("menu") -> menu();
                        default -> {
                        }
                    }

                }
                secondDeck.deleteUnnecessaryCards();
            }
            if (thirdDeck.isActive()) {
                for (Card card : thirdDeck) {

                    card.displayCard();
                    System.out.println();
                    System.out.println("1 - показать перевод");
                    int getCardTranslation = scanner.nextInt();
                    if (getCardTranslation == 1) {
                        card.displayTranslatedCard();
                    }
                    System.out.println(actions);

                    String action = scanner.next();
                    switch (action) {
                        case ("yes") -> System.out.println("Отлично!");
                        case ("no") -> {
                            System.out.println("Не расстраивайтесь, у вас всё получится!");
                            secondDeck.addNewCard(card);
                        }
                        case ("delete") -> thirdDeck.prepareCardDeletion(card);
                        case ("menu") -> menu();
                        default -> {
                        }
                    }

                }
                thirdDeck.deleteUnnecessaryCards();

            }
        }
    }

    /**
     * Меню приложения
     */
    private static void menu() {
        Scanner scanner = new Scanner(System.in);
        //выбор пункута меню
        System.out.println("""
                Меню:
                add - добавить новую карточку
                resume - продолжить учить слова
                end - закончить изучение слов
                """);
        String menuChoice = scanner.next();
        switch (menuChoice) {

            case ("add"):
                System.out.println("Какое слово вы хотете добавить?");//добавление карты в первую колоду
                String newCardWord = scanner.next();
                firstDeck.addNewCard(newCardWord);
                break;
            case ("resume")://продолжаем учить слова

                break;
            case ("end")://заканчиваем учить слова, смотрим, сколько из них мы выучили хорошо
                System.out.println("Поздравляем, теперь вы хорошо знаете " + thirdDeck.getDeckQuantity() + " слов!\n" +
                        "До новых встреч!");
                System.exit(0);
            default:
        }
    }
}

