package com.linearalgebra.bot.generator;

import java.lang.Math;
import java.util.Random;

public class TaskGenerator {
    public TaskGenerator(){ }

    public double[][] generate(int n, int m) { //Создание и заполнение матрицы случайными числами
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = (int) (Math.random() * 21);
            }
        }
        return A;
    }

    public double[][] generate(char operation) { //Создание и заполнение матрицы случайными числами
        double[][] A;
        int n = generateSize();
        int m = generateSize();
        if(operation=='d'){
            A = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = (int) (Math.random() * 21);
                }
            }
        }
        else {
            A = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    A[i][j] = (int) (Math.random() * 21);
                }
            }

        }
        return A;
    }

    public int generateSize(){
        return 2 + Math.abs(new Random().nextInt())%3;
    }
}