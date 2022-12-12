package testingsystem;

import testingsystem.data.dao.TestsDAO;
import testingsystem.data.User;
import testingsystem.data.dao.UsersDAO;

import java.io.IOException;
import java.sql.SQLException;

public class TestAPI {

    /**
     * Провести тест по теме. В случае успешного прохождения теста
     * пользователь переходит к следующей теме.
     * @param userId        id пользователя
     * @return              true, если тест пройден успешно
     */
    public static boolean startTest(int userId) {
        try {
            User student = User.getUserFromDB(userId);
            if(student == null){
                System.out.println("Такого студента не нашлось.");
                return false;
            }
            TestingSystem.jumbledSentencesTestConsole(student);
            UsersDAO usersDAO = new UsersDAO();
            usersDAO.updateUser(student);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Сервис временно недоступен.");
            return false;
        }
        return true;
    }

    /**
     * Добавить в систему новый тест
     * @param lesson_id Номер урока
     * @param path Путь до файла со предложениями.
     *             Формат файла:
     *             Файл должен представлять собой набор английских и
     *             русских предложений, разделенных точкой.
     *             Апострофы должны дублироваться
     * @return     true/false Успешно ли добавлено значение
     * @throws IOException      Возникли проблемы с доступом к файлу
     * @throws SQLException     Возникли проблемы с SQL. Проверьте форматирование файла
     */
    public static boolean putNewTest(int lesson_id,
                                     String path) throws IOException, SQLException {
        try {
            TestsDAO testsDAO = new TestsDAO();
            testsDAO.createNewTest(lesson_id, path);
        } catch (SQLException sqlExc) {
           throw new SQLException("Пожалуйста, проверьте правильность форматирования файла", sqlExc);
        } catch (ClassNotFoundException e) {
            System.out.println("Сервис временно недоступен.");
            return false;
        }
        return true;
    }

}
