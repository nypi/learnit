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
//        BufferedReader file = new BufferedReader(new FileReader("resourses/testInfo.txt"));
//        String line;
//        String[] info;
//        String name = "Ваня";
//        String currentName;
//        Word word;
//        List<Word> words = new ArrayList<>();
//        while((line = file.readLine()) != null){
//            info = line.split(";");
//            currentName = info[0];
//            word = new Word(info[1], info[2], info[3], Integer.parseInt(info[4]));
//            if(currentName.equals(name)){
//                words.add(word);
//            }
//        }
        new Window().setVisible(true);
    }
}
