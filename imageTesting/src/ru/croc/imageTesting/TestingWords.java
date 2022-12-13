package ru.croc.imageTesting;

import java.util.*;

/**
 * Класс для выборки слов на тестирование
 *
 * @author Ермишова СМ
 */
public class TestingWords {
    protected Word currentWord;
    protected final List<Word> testWords = new ArrayList<>();
    protected final Deque<Word> wordQueue = new ArrayDeque<>();
    protected final int numberOfTests;

    /**
     * @param userWords     слова пользователя
     * @param numberOfTests количество тестов
     * @throws TestsNumberException исключение неправильного колва тестов
     */
    public TestingWords(List<Word> userWords, int numberOfTests) throws TestsNumberException {
        if (userWords.size() < numberOfTests || numberOfTests < 3) {
            throw new TestsNumberException();
        }
        this.numberOfTests = numberOfTests;
        choiceOfTests(userWords);
    }

    /**
     * Берет следующее слово в очереди тестов и возвращает его
     *
     * @return следующее слово
     */
    public Word next() {
        currentWord = wordQueue.poll();
        return currentWord;
    }

    /**
     * Добавляет в очередь тестов текущее слово
     */
    public void addCurrentWordInQueue() {
        wordQueue.add(currentWord);
    }

    /**
     * Возвращает любое слово, кроме текущего, из слов пользователя
     *
     * @return любое слова из списка пользователя, кроме текущего
     */
    public Word getRandomWord() {
        Word result = testWords.get((int) (Math.random() * numberOfTests));
        while (result.getEnglishWord().equals(currentWord.getEnglishWord())) {
            result = testWords.get((int) (Math.random() * numberOfTests));
        }
        return result;
    }

    /**
     * Формирование тестов исходя из всех слов пользователя (в приоритете попадают наименее изученные слова пользователя)
     *
     * @param userWords все слова пользователя
     */
    private void choiceOfTests(List<Word> userWords) {
        userWords.sort(Comparator.comparingInt(Word::getKnowledgeDegree));
        int count = 0;
        for (Word word : userWords) {
            if (count >= numberOfTests) {
                break;
            }
            testWords.add(word);
            wordQueue.add(word);
            count++;
        }
    }
}
