import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Deck implements Iterable<Card> {
    private int wordCounter = 0;
    private final List<Card> cards = new ArrayList<>();

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


    @Override
    public Iterator<Card> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < cards.size();
            }

            @Override
            public Card next() {
                return cards.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
