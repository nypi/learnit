package ru.croc.wordimage.entity;

import java.time.LocalDateTime;

public record Result(String idUser, int numberTest, int score, LocalDateTime dateTime){
}
