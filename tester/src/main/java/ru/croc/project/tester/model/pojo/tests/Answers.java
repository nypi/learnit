package ru.croc.project.tester.model.pojo.tests;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answers {
    @JsonProperty(value = "answers")
    List<String> variants;
    public List<String> getVariants() {
        return variants;
    }
}
