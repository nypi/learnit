package ru.croc.imageTesting;

public class UserDoesntExistException extends Exception {
    private final String username;

    public UserDoesntExistException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Пользователя с ником " + username + " не существует";
    }
}
