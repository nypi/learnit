package ru.croc.db_words_tool.exception;


import ru.croc.db_words_tool.table.Word;

public class IllegalWordIsExistException extends Exception {

    private final Word word;

    public IllegalWordIsExistException(Word word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word \"" + word.word() + "\" is already exist in database";
    }
}
