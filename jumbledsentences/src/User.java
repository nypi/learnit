class User {
    private int id;
    private String userName;
    private int testThemeId;

    User(int id, String userName, int testThemeId){
        this.id = id;
        this.userName = userName;
        this.testThemeId = testThemeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTestThemeId() {
        return testThemeId;
    }

    public void setTestThemeId(int testThemeId) {
        this.testThemeId = testThemeId;
    }
}
