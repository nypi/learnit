package ru.croc.wordimage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        WordByImage wordByImage = new WordByImage();
        wordByImage.runTestWordByImage();
    }
}