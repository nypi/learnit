package project.flashCards;

import project.flashCards.config.Config;
import project.flashCards.userInterfaces.MainMenu;
import project.flashCards.userInterfaces.Starter;

public class Main {
    public static void main(String[] args){
        String pathToFileWithNewWords = args[0];
        String pathToConfig = args[1];
        Config.getConfig(pathToConfig);

        new Starter().addingNewWordsFromFile(pathToFileWithNewWords);
        new MainMenu().menu();
    }
}