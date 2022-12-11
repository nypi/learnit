package ru.croc.imageTesting.testProgram;

import ru.croc.imageTesting.User;
import ru.croc.imageTesting.Window;
import ru.croc.imageTesting.Word;

public class Main {
    public static void main(String[] args) {
        Window window = new Window(new User("resources/testInfo.txt"));
        window.setVisible(true);
        for (Word word : window.getLearnedWords()) {
            System.out.println(word);
        }
    }
}
