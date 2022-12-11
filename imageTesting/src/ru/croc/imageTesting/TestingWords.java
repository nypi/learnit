package ru.croc.imageTesting;

import java.util.*;

public class TestingWords {
    protected Word currentWord;
    protected final List<Word> testWords = new ArrayList<>();
    protected final Deque<Word> wordQueue = new ArrayDeque<>();
    protected final int numberOfTests;

    public TestingWords(List<Word> userWords, int numberOfTests) {
        this.numberOfTests = numberOfTests;
        choiceOfTests(userWords);
    }

    public Word next(){
        currentWord = wordQueue.poll();
        return currentWord;
    }

    public void addCurrentWordInQueue(){
        wordQueue.add(currentWord);
    }

    public Word getRandomWord(){
        Word result = testWords.get((int) (Math.random() * numberOfTests));
        while(result.getEnglishWord().equals(currentWord.getEnglishWord())){
            result = testWords.get((int) (Math.random() * numberOfTests));
        }
        return result;
    }

    private void choiceOfTests(List<Word> userWords){
        userWords.sort(Comparator.comparingInt(Word::getKnowledgeDegree));
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
