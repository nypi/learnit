package registration;


import registration.database.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {

    /**
     * the main method of launching the application, in which the token is
     * initially verified, and then the user can register or authorize
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IncorrectData
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     */
    public static void startApplication() throws SQLException, NoSuchAlgorithmException, IncorrectData, IOException, IllegalArgumentException, ClassNotFoundException {
        Authorization.checkTokenAuthorization();

        Scanner scanner = new Scanner(System.in);
        int count = 0;
        System.out.println("Вы зарегистрированы в нашем приложение?(Необходимо ответить 'yes' или 'no')");
        String answer = scanner.nextLine().toLowerCase();
        while (count < 3 && !(answer.equals("yes") || answer.equals("no"))) {
            System.out.println("Вы некорректно ответили, необходимо ответить только 'yes' или 'no'");
            count++;
            answer = scanner.nextLine().toLowerCase();
        }
        if (answer.equals("yes")) {
            Authorization.windowAuthorization();
        } else if (answer.equals("no")) {
            Registration.windowRegistration();
        } else {
            throw new IncorrectData();
        }
    }


    /**
     * this method is a logout function, which implies that when
     * executing this command, the user exits his account, and his token is erased
     * @param login
     * @throws IncorrectData
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void logout(String login) throws IncorrectData, SQLException, NoSuchAlgorithmException, IOException, ClassNotFoundException {
        System.out.println("Чтобы выйти из аккаунта, напиши 'logout' ");
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        String answer = scanner.nextLine().toLowerCase();
        while (count < 3 && !answer.equals("logout")) {
            System.out.println("Вы некорректно ввели данные, чтобы выйти из аккаунта напишите 'выход'");
            count++;
            answer = scanner.nextLine().toLowerCase();
        }
        if (answer.equals("logout")) {
            TokenDAO.deleteTokenBeforeLogout(login);
            startApplication();
        } else {
            throw new IncorrectData();
        }
    }

    /**
     * a method for creating tables necessary for the application to work
     * @throws SQLException
     */
    public static void createTables() throws SQLException {
        DataDAO.createTableData();
        TokenDAO.createTableToken();
        ClientParameterDAO.createTableClientParameter();
        InfoDAO.createTableInfo();
    }
}
