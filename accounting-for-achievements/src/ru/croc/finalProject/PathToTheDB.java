package ru.croc.finalProject;

public class PathToTheDB {
    private String url;
    private String password;
    private String user;

    public PathToTheDB(String[] pathToTheDB) {
        url = pathToTheDB[0];
        user = pathToTheDB[1];
        password = pathToTheDB[2];
    }

    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }
}
