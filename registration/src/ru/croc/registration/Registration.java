package registration;


import registration.database.*;
import registration.validators.Validation;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

import static registration.Application.startApplication;


public class Registration {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * the registration window method, upon its successful
     * completion, writes user data to the database
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws IncorrectData
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void windowRegistration() throws SQLException, NoSuchAlgorithmException, IncorrectData, IOException, ClassNotFoundException {
        System.out.println("Введите ваше имя");
        String name = inputName();

        System.out.println("Введите вашу фамилию");
        String surname = inputSurname();

        System.out.println("Введите номер вашего телефона");
        String phoneNumber = inputPhoneNumber();


        System.out.println("Введите вашу электронную почту");
        String email = inputEmail();


        System.out.println("Введите логин. Логин может состоять только из латинских символов, цифр или символа '_', ");
        System.out.println("длина логина не менее 5 символов");
        String login = inputLogin();

        System.out.println("Введите ваш пароль");
        String password = Hash.hashPassword(inputPassword());

        // если все данные были введены правильно, то создается объект класса Client
        // который в дальнейшем передается для записи в таблицах
        Client user = new Client(name, surname, phoneNumber, email, login, password);

        DataDAO.addClientData(user);
        TokenDAO.addClientToken(login);
        ClientParameterDAO.addClientParameters(user);
        InfoDAO.addClientInfo(user);
        startApplication();
    }

    private static String inputName() throws IncorrectData {
        String name;
        int count = 0;

        while (count < 3) {
            name = scanner.nextLine();
            if (Validation.isCheckerNameSurname(name)) {
                return name;
            }
            System.out.println("Попробуйте ввести имя еще раз");
            count++;
        }
        throw new IncorrectData();
    }


    private static String inputSurname() throws IncorrectData {
        String surname;
        int count = 0;

        while (count < 3) {
            surname = scanner.nextLine();
            if (Validation.isCheckerNameSurname(surname)) {
                return surname;
            }
            System.out.println("Попробуйте ввести фамилию еще раз");
            count++;
        }
        throw new IncorrectData();
    }

    private static String inputPhoneNumber() throws IncorrectData, SQLException {
        String phoneNumber;
        int count = 0;

        while (count < 3) {
            phoneNumber = scanner.nextLine();
            if (Validation.isCheckerPhone(phoneNumber)) {
                return phoneNumber;
            }
            System.out.println("Попробуйте ввести номер телефона еще раз");
            count++;
        }
        throw new IncorrectData();
    }

    private static String inputEmail() throws IncorrectData, SQLException {
        String email;
        int count = 0;

        while (count < 3) {
            email = scanner.nextLine();
            if (Validation.isCheckerEmail(email)) {
                return email;
            }
            System.out.println("Попробуйте ввести email еще раз");
            count++;
        }
        throw new IncorrectData();
    }

    private static String inputPassword() throws IncorrectData, SQLException {
        String password;
        int count = 0;

        while (count < 3) {
            password = scanner.nextLine();
            if (Validation.isCheckerPassword(password)) {
                return password;
            }
            System.out.println("Попробуйте ввести пароль еще раз");
            count++;
        }
        throw new IncorrectData();
    }


    private static String inputLogin() throws SQLException, IncorrectData {
        String login;
        int count = 0;

        while (count < 3) {
            login = scanner.nextLine();
            if (Validation.isCheckerLogin(login)) {
                return login;
            }
            System.out.println("Попробуйте ввести логин еще раз");
            count++;
        }
        throw new IncorrectData();
    }
}
