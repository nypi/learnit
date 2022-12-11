package ru.croc.wordimage.entity;

import java.io.File;

public record Question (int id, int numberCorrectAnswer, File imgPath) {}