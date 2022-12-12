package testingsystem;

import testingsystem.data.TestsDAO;
import testingsystem.data.User;

import java.io.IOException;
import java.sql.SQLException;

public class TestAPI {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        startTest(1, "Xen", 12);
    }

    public static boolean startTest(int userId,
                                    String userName,
                                    int testLessonId) {
        User student = new User(userId, userName, testLessonId);
        try {
            TestingSystem.jumbledSentencesTestConsole(student);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Сервис временно недоступен.");
            return false;
        }
        return true;
    }

    public static boolean putNewTest(int lesson_id,
                                     String path) throws IOException, SQLException {
        try {
            TestsDAO testsDAO = new TestsDAO();
            testsDAO.createNewTest(lesson_id, path);
        } catch (SQLException sqlExc) {
           throw new SQLException("A SQL error occurred. Please, check your file's formatting", sqlExc);
        } catch (ClassNotFoundException e) {
            System.out.println("Сервис временно недоступен.");
            return false;
        }
        return true;
    }

}
