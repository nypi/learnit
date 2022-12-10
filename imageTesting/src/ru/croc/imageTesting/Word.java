package ru.croc.imageTesting;

public class Word {
    private final String src; // image directory
    private final String russianWord; // word in Russian
    private final String englishWord; // word on English
    public int knowledgeDegree; // the degree of knowledge of the word

    public Word(String src, String russianWord, String englishWord, int knowledgeDegree) {
        this.src = src;
        this.russianWord = russianWord;
        this.englishWord = englishWord;
        this.knowledgeDegree = knowledgeDegree;
    }

    public int getKnowledgeDegree() {
        return knowledgeDegree;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public String getSrc() {
        return src;
    }

    public void increaseKnowledgeDegree() {
        this.knowledgeDegree++;
    }
}
