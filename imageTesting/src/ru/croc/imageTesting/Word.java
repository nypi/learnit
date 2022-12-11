package ru.croc.imageTesting;

public class Word {
    private final String userName;
    private final String src; // image directory
    private final String russianWord; // word in Russian
    private final String englishWord; // word on English
    public int knowledgeDegree; // the degree of knowledge of the word

    public Word(String userName, String src, String englishWord, String russianWord, int knowledgeDegree) {
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void increaseKnowledgeDegree() {
        this.knowledgeDegree++;
    }

    @Override
    public String toString() {
        return "Word{" +
                "src='" + src + '\'' +
                ", russianWord='" + russianWord + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", knowledgeDegree=" + knowledgeDegree +
                '}';
    }
}
