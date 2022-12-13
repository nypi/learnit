package ru.croc.imageTesting;

/**
 * Класс запуска приложения
 */
public class ImageTest {
    /**
     * Запуск
     * @param src ссылка на файл с данными о пользователях
     */
    public static void start(String src) {
        Window window = new Window(new User(src));
        window.setVisible(true);
    }
}
