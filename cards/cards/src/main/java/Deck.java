import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


public class Deck implements Iterable<Card> {
    private static int wordCounter = 0;//счетчик слов, нужен, чтобы задавать номера карточкам
    private long lastShowTime = 0L;//время последнего показа карточек из этой колоды
    private long interval;//интервал, через который будет повторение слов из этой колоды
    private final List<Card> cards = new ArrayList<>();//карты колоды
    private boolean delIndex;


    /**
     * Создаёт новый {@code Deck}
     *
     * @param minuteInterval - интервал, через который будет повторение слов из этой колоды в минутах
     */
    public Deck(long minuteInterval) {
        this.interval = minuteInterval * 60000;

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

    public int getWordQuantity() {
        return cards.size();
    }

    public void addNewCard(String newWord) {
        wordCounter += 1;
        cards.add(new Card(newWord, wordCounter));
    }

    public void addNewCard(Card newCard) {
        cards.add(newCard);
    }


    /**
     * Итератор, позволяет удобно проходится по колоде
     *
     * @return - итератор
     */
    @Override
    public Iterator<Card> iterator() {
        return new Iterator<>() {

            private static int currentIndex = 0;


            /**
             * Проверка
             * @return - есть ли ещё карта в колоде
             */
            @Override
            public boolean hasNext() {
                boolean hasNext = currentIndex < cards.size();
                if (!hasNext) {
                    lastShowTime = System.currentTimeMillis();
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

            public void remove() {

                if (currentIndex != 0) {
                    currentIndex -= 1;
                }
                cards.remove(currentIndex);


            }


        };
    }

    public boolean isActive() {
        return System.currentTimeMillis() > lastShowTime + interval;
    }
}
