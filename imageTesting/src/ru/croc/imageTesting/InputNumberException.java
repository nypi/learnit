package ru.croc.imageTesting;

public class InputNumberException extends Exception{
    @Override
    public String getMessage() {
        return "Неправильный формат ввода числа, должно быть не меньше 3 и не больше кол-ва записей пользователя в файле";
    }
}
