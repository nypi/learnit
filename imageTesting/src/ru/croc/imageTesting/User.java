package ru.croc.imageTesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс пользователя
 *
 * @author Ермишова СМ
 */
public class User {
    private final String src;
    private String username = "";
    List<Word> words = new ArrayList<>();

    /**
     * @param src ссылка на файл с данными о пользователе
     */
    public User(String src) {
        this.src = src;
    }

    /**
     * Установить имя пользователя
     *
     * @param username новое имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Нахождение всех слов пользователя из файла
     *
     * @throws IOException              выбрасывается, если файл не найден
     * @throws UserDoesntExistException выбрасывается, если пользователя нет в файле
     */
    public void findUserWords() throws IOException, UserDoesntExistException {
        BufferedReader file = new BufferedReader(new FileReader(src));
        String line, currentName;
        words.clear();
        String[] info;
        Word word;
        while ((line = file.readLine()) != null) {
            info = line.split(";");
            currentName = info[0];
            word = new Word(info[0], info[1], info[2], info[3], Integer.parseInt(info[4]));
            if (currentName.equals(username)) {
                words.add(word);
            }
        }
        if (words.isEmpty()) {
            throw new UserDoesntExistException(username);
        }
    }

    /**
     * Получение всех слов пользователя
     *
     * @return список элементов типа Word
     */
    public List<Word> getWords() {
        return words;
    }
}
