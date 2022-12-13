package registration;


import registration.token.Token;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Start {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException, IncorrectData, IOException {
        Application.createTables();
        Token.checkFileToken();
        Application.startApplication();
    }
}
