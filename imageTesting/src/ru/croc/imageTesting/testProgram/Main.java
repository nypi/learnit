package ru.croc.imageTesting.testProgram;

import ru.croc.imageTesting.User;
import ru.croc.imageTesting.Window;

public class Main {
    public static void main(String[] args) {
        new Window(new User("resources/testInfo.txt")).setVisible(true);
    }
}
