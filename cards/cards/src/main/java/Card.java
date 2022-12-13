import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class Card {
    private final String russianWord;//слово карточки, которое надо выучить
    private final int number;//номер карточки, задаётся при первом заполнении первой колоды
    //создан для удобства пользователя, как в физических аналогах карт для запоминания

    /**
     * Создаёт  новый {@code Card}
     *
     * @param russianWord - слово карточки, которое надо выучить на английском языке
     * @param number      - номер карточки
     */
    public Card(String russianWord, int number) {
        this.russianWord = russianWord;
        this.number = number;
    }

    /**
     * Перевод слова на английский
     *
     * @return - слово на английском
     * @throws IOException - ошибка ввода/вывода
     */
    private String translateToEnglish() throws IOException {
        Translate translate = new Translate();
        String translation = translate.translate(russianWord, "ru", "en");
        return translation.substring(19, translation.length() - 2);
    }

    /**
     * Вывод карточки для консольного приложения
     */
    public void displayCard() {
        int stringLength = 30 + russianWord.length();
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center("Карточка № " + number,
                stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center(" ", stringLength - 2),
                stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center("Слово: " + russianWord,
                stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
    }

    /**
     * Вывод перевода, чтобы пользователь смог проверить себя
     *
     * @throws IOException - ошибка ввода/вывода
     */
    public void displayTranslatedCard() throws IOException {
        int stringLength = 30 + translateToEnglish().length();
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center("Карточка № " + number,
                stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center(" ", stringLength - 2),
                stringLength, "|"));
        System.out.println(StringUtils.center(StringUtils.center("Слово на англиском: " +
                translateToEnglish(), stringLength - 2), stringLength, "|"));
        System.out.println(StringUtils.rightPad("+", stringLength - 1, "-") + "+");
    }

}
