package ru.croc.wordmatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordMatcher {
    private boolean mainFlag = true;
    private final String userName;
    private final Integer countTry = 3;

    public WordMatcher(String userName) {
        this.userName = userName;
    }

    SentenceDAO sentenceDAO = new SentenceDAO();
    void start(){

        System.out.println("Hello, " + userName + ". You use service of word matcher!");
        Scanner scanner = new Scanner(System.in);
        while (mainFlag) {
            System.out.println("MENU:\n1. Start\n2. End");

            int chooseUser = scanner.nextInt();
            switch (chooseUser) {
                case 1 -> getSentanse();
                case 2 -> mainFlag = false;
                default -> System.out.println("WTF!");
            }
        }
        scanner.close();
    }

    private boolean containWord(List<String> list, String word){
        for (String s : list) {
            if (s.equals(word)) return true;
        }
        return false;
    }

    public void getSentanse(){
        System.out.println("Write one of the topic's names: eat, it, sport, weather");
        List<String> topics = new ArrayList<String>(List.of("eat", "it", "sport", "weather"));

        Scanner scanner2 = new Scanner(System.in);
        String userChooseOfTopic = "";

        while (true) {
            userChooseOfTopic = scanner2.nextLine().toLowerCase();
            if (containWord(topics, userChooseOfTopic)) break;
            else System.out.println("Try again enter topic");
        }

        List<Integer> ids = sentenceDAO.getAllIdTopic(userChooseOfTopic);

        Random random = new Random();
        Sentence sentence = sentenceDAO.find(ids.get(random.nextInt(ids.size())));

        printString(randomizeString(sentence.getSentence()));

        int counter = 0;
        boolean flag = false;

        while (counter < countTry){
            String userAnswer = scanner2.nextLine();
            if (sentence.getSentence().equals(userAnswer)) {
                flag = true;
                break;
            }
            else {
                if (countTry - counter - 1 > 0) System.out.println("Wrong, try again! You have " + (countTry - counter - 1) + " attempts");
            }
            counter++;
        }
        if (flag) System.out.println("\nCongratulations, that's right");
        else System.out.println("\nToday is not your day :c\n");

    }

    public String[] randomizeString(String sentence){

        String[] str = sentence.split(" ");

        String[] newArray = new String[str.length];
        List<Integer> indexes = new ArrayList<>(str.length);
        Random random = new Random();
        int count = 0;
        do {
            int var = random.nextInt(str.length);
            if (!indexes.contains(var)) {
                indexes.add(var);
                newArray[var] = str[count++];
            }
        } while (count != str.length);
        return newArray;
    }

    public void printString(String[] strings){
        for (String string : strings) {
            System.out.print("[" + string + "] ");
        }
        System.out.println();
    }


}
