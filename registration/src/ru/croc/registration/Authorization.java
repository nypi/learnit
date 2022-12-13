package registration;

import registration.database.ClientParameterDAO;
import registration.database.DataDAO;
import registration.database.InfoDAO;
import registration.database.TokenDAO;
import registration.token.JWebToken;
import registration.token.Token;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class Authorization {

    /**
     * when the application is launched, the method checks for the correspondence
     * of the token stored on his computer with the token stored in the database,
     * and if the tokens are equal, the user performs authorization without a login and password
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     * @throws IncorrectData
     * @throws ClassNotFoundException
     */
    public static void checkTokenAuthorization() throws IOException, NoSuchAlgorithmException, SQLException, IncorrectData, ClassNotFoundException {
        JWebToken incomingToken = new JWebToken(Token.readToken());
        if (incomingToken.isValid() && TokenDAO.findStatusClient(String.valueOf(incomingToken))) {
            String login = DataDAO.getLoginById(Integer.parseInt(incomingToken.getSubject()));
            printClient(Integer.parseInt(incomingToken.getSubject()));
            Application.logout(login);
        }
    }

    /**
     * the method of the authorization window, when it is successfully completed,
     * the user logs in and sees his data, otherwise the user will have the
     * opportunity to register or re-log in
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IncorrectData
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void windowAuthorization() throws SQLException, NoSuchAlgorithmException, IncorrectData, IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваш логин");
        String login = scanner.nextLine();
        System.out.println("Введите ваш пароль ");
        String password = scanner.nextLine();
        if (DataDAO.checkPassword(login, password)) {
            windowBeforeAuthorization(login);
            Application.logout(login);
            return;
        }
        System.out.println("Вы ввели некорректный логин или пароль, если вы ранее не пользовались нашим сервисом,");
        System.out.println("то вы можете пройти регистрацию, иначе проверьте корректность введенных данных");
        Application.startApplication();
    }

    /**
     * after successful registration, this method generates
     * a token and outputs user data
     * @param login
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws IncorrectData
     */
    public static void windowBeforeAuthorization(String login) throws SQLException, NoSuchAlgorithmException, IOException, IncorrectData {
        System.out.println("Авторизация выполнена");
        System.out.println("Ваши данные:");
        printClient(login);
        String token = Token.createToken(String.valueOf(DataDAO.findIdClient(login)));
        TokenDAO.updateTokenDuringAuthorization(login, token);
    }

    /**
     * method for user data output
     * @param login
     * @throws SQLException
     */
    private static void printClient(String login) throws SQLException {
        int id = DataDAO.findIdClient(login);
        System.out.println("Имя: " + ClientParameterDAO.getName(id));
        System.out.println("Фамилия: " + ClientParameterDAO.getSurname(id));
        System.out.println("Номер телефон: " + InfoDAO.getNumberPhone(id));
        System.out.println("Электронная почта: " + InfoDAO.getEmail(id));
    }

    /**
     * method for user data output
     * @param id
     * @throws SQLException
     */
    private static void printClient(int id) throws SQLException {
        System.out.println("Имя: " + ClientParameterDAO.getName(id));
        System.out.println("Фамилия: " + ClientParameterDAO.getSurname(id));
        System.out.println("Номер телефон: " + InfoDAO.getNumberPhone(id));
        System.out.println("Электронная почта: " + InfoDAO.getEmail(id));
    }
}
