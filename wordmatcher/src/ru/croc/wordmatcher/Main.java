package ru.croc.wordmatcher;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String dataBaseDriver = "org.h2.Driver";
        Class.forName(dataBaseDriver);

        System.out.println("WordMatcher start");
        WordMatcher wordMatcher = new WordMatcher("Ctacek");
        wordMatcher.start();
        System.out.println("Goodbye ^_^");

    }
}