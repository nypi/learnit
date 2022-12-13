package ru.croc.project.tester.model.pojo.users;

import java.util.List;

public class User {
    private final String id;
    private final List<UserTestInformation> testsInformation;

    public User() {
        id = "default";
        testsInformation = null;
    }

    public User(String id, List<UserTestInformation> testsInformation) {
        this.id = id;
        this.testsInformation = testsInformation;
    }

    public String getId() {
        return id;
    }

    public List<UserTestInformation> getTestsInformation() {
        return testsInformation;
    }

    public UserTestInformation findTestInformationByTestName(String testName) {
        for (UserTestInformation information : testsInformation) {
            if (information.getTestName().equals(testName)) {
                return information;
            }
        }
        return null;
    }
}
