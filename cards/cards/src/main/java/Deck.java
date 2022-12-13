import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Deck implements Iterable<Card> {
    private static int wordCounter = 0;//счетчик слов, нужен, чтобы задавать номера карточкам
    private long lastShowTime = Long.MIN_VALUE;//время последнего показа карточек из этой колоды
    private long interval;//интервал, через который будет повторение слов из этой колоды
    private final List<Card> cards = new ArrayList<>();//карты колоды

    private final List<Card> removeCards = new ArrayList<>();//карточки, которые являются кандидатами на удаление


    /**
     * Создаёт новый {@code Deck}
     *
     * @param minuteInterval - интервал, через который будет повторение слов из этой колоды в минутах
     */
    public Deck(long minuteInterval) {
        this.interval = minuteInterval * 60000;//перевод в миллисекунды

    }

    /**
     * Создаёт новый {@code Deck}
     */
    public Deck() {
    }


    /**
     * Заполнение колоды картами
     *
     * @param path - путь до файла из которого нужно брать слова для изучения
     */
    public void fillDeck(Path path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String newWord;
            while ((newWord = bufferedReader.readLine()) != null) {
                wordCounter += 1;
                cards.add(new Card(newWord, wordCounter));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * получить размер колоды
     *
     * @return - размер колоды
     */
    public int getDeckQuantity() {
        return cards.size();
    }

    /**
     * Добавление новой карты в колоду по слову
     *
     * @param newWord
     */
    public void addNewCard(String newWord) {
        wordCounter += 1;
        cards.add(new Card(newWord, wordCounter));
    }

    /**
     * Добавление новой карты в колоду
     *
     * @param newCard
     */
    public void addNewCard(Card newCard) {
        cards.add(newCard);
    }

    /**
     * Подготовить карту к удалению
     *
     * @param card
     */
    public void prepareCardDeletion(Card card) {
        removeCards.add(card);
    }

    /**
     * удалить все отложенные карты из колоды
     */
    public void deleteUnnecessaryCards() {
        for (Card card : removeCards) {
            cards.remove(card);
        }
        removeCards.clear();
    }


    /**
     * Итератор, позволяет удобно проходится по колоде
     *
     * @return - итератор
     */
    @Override
    public Iterator<Card> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;


            /**
             * Проверка
             * @return - есть ли ещё карта в колоде
             */
            @Override
            public boolean hasNext() {

                boolean hasNext;

                hasNext = currentIndex < cards.size();

                if (!hasNext) {
                    lastShowTime = System.currentTimeMillis();//когда колода закончилась, фиксируем время
                }
                return hasNext;


            }

            /**
             * Получение следующей карты в колоде
             * @return - следующая карта
             */
            @Override
            public Card next() {
                return cards.get(currentIndex++);
            }
        };
    }

    /**
     * Пустая ли колода
     *
     * @return - булевая перенная пустоты
     */
    public boolean deckIsEmpty() {
        return (cards.isEmpty());
    }

    /**
     * Активна ли колода, или мы пока не будем её пользоваться
     *
     * @return - булевая переменная активности
     */
    public boolean isActive() {
        return System.currentTimeMillis() > lastShowTime + interval;
    }
}
