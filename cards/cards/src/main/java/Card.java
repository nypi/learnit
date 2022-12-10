import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Card {
    private String russianWord;
    private int number;

    public Card(String russianWord, int number) {
        this.russianWord = russianWord;
        this.number = number;
    }

    private String translateToEnglish() throws IOException {
        Translate translate = new Translate();
        String translation = translate.translate(russianWord, "ru", "en");

        return translation.substring(19, translation.length() - 2);
    }

    public void displayCard() {
        int stringLength = 30 + russianWord.length();
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center("Карточка № " + number, stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center(" ", stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center("Слово: " + russianWord, stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
    }

    public void displayTranslatedCard() throws IOException {
        int stringLength = 30 + translateToEnglish().length();
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center("Карточка № " + number, stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center(" ", stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center("Слово на англиском: " + translateToEnglish(), stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
    }

}
