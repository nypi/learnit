package registration;

public class IncorrectData extends Exception {
    public IncorrectData() {
        System.err.println("Вы ввели некорректные данные три раза, попробуйте еще раз позже");
    }
}
