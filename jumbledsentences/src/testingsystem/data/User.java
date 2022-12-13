package testingsystem.data;

import testingsystem.data.dao.UsersDAO;

import java.sql.SQLException;

public class User {
    private final int id;
    private String userName;
    private int testLessonId;

    public User(int id, String userName, int testLessonId){
        this.id = id;
        this.userName = userName;
        this.testLessonId = testLessonId;
    }

    /**
     * Возвращает пользователя из базы данных. Если такого
     * пользователя нет, возвращает null
     * @param id идентификатор пользователя
     * @return объект класса User найденного пользователя
     * @throws ClassNotFoundException Проблемы с доступом к БД
     * @throws SQLException Ошибка SQL
     */
    static public User getUserFromDB(int id) throws ClassNotFoundException, SQLException {
        UsersDAO usersDAO = new UsersDAO();
        return usersDAO.findUser(id);
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTestLessonId() {
        return testLessonId;
    }

    public void setTestLessonId(int testLessonId) {
        this.testLessonId = testLessonId;
    }
}
