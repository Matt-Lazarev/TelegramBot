package com.linearalgebra.bot.handler;

import com.linearalgebra.bot.config.BotState;

import java.util.Arrays;
import java.util.List;

public class CallbackHandler {

    public static List<String> handleTheory(String data){
        switch(data){
            case "Theory_det": return Arrays.asList("src/main/resources/images/determinant.png", "Определитель матрицы:");
            case "Theory_add": return Arrays.asList("src/main/resources/images/addition.png", "Сложение матриц:");
            case "Theory_sub": return Arrays.asList("src/main/resources/images/subtraction.png", "Вычитание матриц:");
            case "Theory_mul": return Arrays.asList("src/main/resources/images/multiplication.jpg", "Умножение матриц:");
            case "Theory_inv": return Arrays.asList("src/main/resources/images/inverse.jpg", "Обратная матрица:");
            default:  throw new IllegalArgumentException("photo not found");
        }
    }

    public static BotState handleCalculator(String data){
        BotState state;
        switch(data){
            case "Calculator_det":
                state = BotState.SizeCheck;
                state.setOperation('d');
                return state;
            case "Calculator_add":
                state = BotState.SizeCheck;
                state.setOperation('+');
                return state;
            case "Calculator_sub":
                state = BotState.SizeCheck;
                state.setOperation('-');
                return state;
            case "Calculator_mul":
                state = BotState.SizeCheck;
                state.setOperation('*');
                return state;
            default:  throw new IllegalArgumentException("operation not found");
        }
    }

    public static BotState handleGenerator(String data){
        BotState state;
        switch(data){
            case "Generator_det":
                state = BotState.AnswerCheck;
                state.setOperation('d');
                return state;
            case "Generator_add":
                state = BotState.AnswerCheck;
                state.setOperation('+');
                return state;
            case "Generator_sub":
                state = BotState.AnswerCheck;
                state.setOperation('-');
                return state;
            case "Generator_mul":
                state = BotState.AnswerCheck;
                state.setOperation('*');
                return state;
            default:  throw new IllegalArgumentException("operation not found");
        }
    }
}
