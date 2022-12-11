package ru.croc.imageTesting.testProgram;

import ru.croc.imageTesting.Window;
import ru.croc.imageTesting.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new Window("resources/testInfo.txt").setVisible(true);
    }
}
