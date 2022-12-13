package ru.croc.project.tester.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbProperties {
    private FileInputStream fis;
    private Properties properties = new Properties();

    private final String host;
    private final String login;
    private final String password;

    public DbProperties() throws IOException {

        fis = new FileInputStream("src/main/resources/ru/croc/project/tester/properties/config.properties");
        properties.load(fis);

        this.host = properties.getProperty("db.host");
        this.login = properties.getProperty("db.login");
        this.password = properties.getProperty("db.password");
    }

    public String getHost() {
        return host;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

