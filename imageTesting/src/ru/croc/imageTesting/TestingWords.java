package ru.croc.imageTesting;

import java.util.*;

public class TestingWords {
    private Word currentWord;
    private final List<Word> testWords = new ArrayList<>();
    private final Deque<Word> wordQueue = new ArrayDeque<>();
    private final int numberOfTests;

    public TestingWords(Collection<Word> userWords, int numberOfTests) {
        this.numberOfTests = numberOfTests;
        choiceOfTests(userWords);
    }

    public Word getNext(){
        currentWord = wordQueue.poll();
        return currentWord;
    }

    public void addWordInQueue(Word lastWord){
        wordQueue.add(lastWord);
    }

    public Word getRandomWord(){
        Word result = testWords.get((int) (Math.random() * numberOfTests));
        while(result.getEnglishWord().equals(currentWord.getEnglishWord())){
            result = testWords.get((int) (Math.random() * numberOfTests));
        }
        return result;
    }

    private void choiceOfTests(Collection<Word> userWords){
        userWords = (Collection<Word>) userWords.stream().sorted(Comparator.comparingInt(Word::getKnowledgeDegree));
        int count = 0;
        for(Word word: userWords){
            if(count >= numberOfTests){
                break;
            }
            testWords.add(word);
            wordQueue.add(word);
            count++;
        }
    }
}
