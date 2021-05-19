package com.linearalgebra.bot.handler;

import com.linearalgebra.bot.config.BotState;

import java.util.Arrays;
import java.util.List;

public class TheoryHandler {

    public static List<String> handle(String data){
        switch(data){
            case "Theory_det": return Arrays.asList("src/main/resources/images/determinant.png", "Определитель матрицы:");
            case "Theory_add": return Arrays.asList("src/main/resources/images/addition.png", "Сложение матриц:");
            case "Theory_sub": return Arrays.asList("src/main/resources/images/subtraction.png", "Вычитание матриц:");
            case "Theory_mul": return Arrays.asList("src/main/resources/images/multiplication.jpg", "Умножение матриц:");
            case "Theory_inv": return Arrays.asList("src/main/resources/images/inverse.jpg", "Обратная матрица:");
            default:  throw new IllegalArgumentException("photo not found");
        }
    }
}
