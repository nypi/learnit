import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Deck implements Iterable<Card> {
    private int wordCounter = 0;//счетчик слов, нужен, чтобы задавать номера карточкам
    private long lastShowTime;//время последнего показа карточек из этой колоды
    private long interval;//интервал, через который будет повторение слов из этой колоды
    private final List<Card> cards = new ArrayList<>();//карты колоды

    /**
     * Создаёт новый {@code Deck}
     * @param minuteInterval - интервал, через который будет повторение слов из этой колоды в минутах
     */
    public Deck(long minuteInterval) {
        this.interval = minuteInterval*60000;

    }

    /**
     *  Создаёт новый {@code Deck}
     */
    public Deck() {
    }


    /**
     *  Заполнение колоды картами
     * @param path - путь до файла из которого нужно брать слова для изучения
     */
    public void fillDeck(Path path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String newWord;
            while ((newWord = bufferedReader.readLine()) != null) {
                wordCounter += 1;
                cards.add(new Card(newWord, wordCounter));
            bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Итератор, позволяет удобно проходится по колоде
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
                return currentIndex < cards.size();
            }

            /**
             * Получение следующей карты в колоде
             * @return - следующая карта
             */
            @Override
            public Card next() {
                lastShowTime = Long.parseLong(null);
                return cards.get(currentIndex++);

            }


        };
    }
}
