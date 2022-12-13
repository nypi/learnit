package ru.croc.db_words_tool.exception;


import ru.croc.db_words_tool.table.Word;

public class IllegalWordNotFoundException extends Throwable {

    private final Word word;

    public IllegalWordNotFoundException(Word word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "The word \"" + word.word() + "\" was not found";
    }
}

