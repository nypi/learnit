package ru.croc.wordmatcher;

public class Sentence {
    private Integer id;
    private String topic;
    private String sentence;
    private String secondCorrect;

    public Sentence(Integer id, String topic, String sentence, String secondcorrect) {
        this.id = id;
        this.topic = topic;
        this.sentence = sentence;
        this.secondCorrect = secondcorrect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSecondCorrect() {
        return secondCorrect;
    }

    public void setSecondCorrect(String secondCorrect) {
        this.secondCorrect = secondCorrect;
    }
}
