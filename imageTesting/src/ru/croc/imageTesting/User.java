package ru.croc.imageTesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String src;
    private String username;
    List<Word> words = new ArrayList<>();

    public User(String src) {
        this.src = src;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void findUserWords() throws IOException {
        BufferedReader file = new BufferedReader(new FileReader("resources/testInfo.txt"));
        String line, currentName;
        String[] info;
        Word word;
        while((line = file.readLine()) != null){
            info = line.split(";");
            currentName = info[0];
            word = new Word(info[1], info[2], info[3], Integer.parseInt(info[4]));
            if(currentName.equals(username)){
                words.add(word);
            }
        }
    }

    public List<Word> getWords() {
        return words;
    }
}
