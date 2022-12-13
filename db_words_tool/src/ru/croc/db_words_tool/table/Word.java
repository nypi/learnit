package ru.croc.db_words_tool.table;

public final class Word {

    private final String word;

    private boolean isLearned;

    public Word(String word, boolean isLearned) {
        this.word = word;
        this.isLearned = isLearned;
    }

    public String word() {
        return word;
    }

    public boolean isLearned() {
        return isLearned;
    }

    public void setLearned(boolean learned) {
        isLearned = learned;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", studied=" + isLearned +
                '}';
    }
}