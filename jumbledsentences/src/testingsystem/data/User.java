package testingsystem.data;

public class User {
    private final int id;
    private String userName;
    private int testLessonId;

    public User(int id, String userName, int testLessonId){
        this.id = id;
        this.userName = userName;
        this.testLessonId = testLessonId;
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
