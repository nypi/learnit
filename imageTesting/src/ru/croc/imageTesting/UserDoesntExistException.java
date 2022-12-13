package ru.croc.imageTesting;

/**
 * Класс исключения, выдающий ошибку, если не нашел пользователя в базе данных
 */
public class UserDoesntExistException extends Exception {
    private final String username;

    /**
     * @param username имя пользователя, которого не удалось найти
     */
    public UserDoesntExistException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Пользователя с ником " + username + " не существует";
    }
}
