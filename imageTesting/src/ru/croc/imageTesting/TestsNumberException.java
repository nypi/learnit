package ru.croc.imageTesting;

/**
 * Класс исключения, выдающий ошибку, если на вход подано число тестов в неправильном формате (меньше 3 или больше кол-ва слов пользователя в файле)
 * @author Ермишова СМ
 */
public class TestsNumberException extends Exception{
    @Override
    public String getMessage() {
        return "Неправильный формат ввода числа, должно быть не меньше 3 и не больше кол-ва записей пользователя в файле";
    }
}
