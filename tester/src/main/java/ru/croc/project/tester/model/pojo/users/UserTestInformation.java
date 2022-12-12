package ru.croc.project.tester.model.pojo.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTestInformation {
    @JsonProperty("test_name")
    String testName;
    @JsonProperty("right_ans")
    int rightAnswers;
    @JsonProperty("ans_count")
    int ansCount;
    public String getTestName() {
        return testName;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public int getAnsCount() {
        return ansCount;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
}