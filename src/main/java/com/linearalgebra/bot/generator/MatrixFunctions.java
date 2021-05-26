package com.linearalgebra.bot.generator;

public class MatrixFunctions {
    static public double[][] sum(double A[][], double B[][]) {
        double[][] matrix = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                matrix[i][j] = A[i][j] + B[i][j];
        return matrix;
    }

    static public double[][] sub(double A[][], double B[][]) {
        double[][] matrix = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                matrix[i][j] = A[i][j] - B[i][j];
        return matrix;
    }

    static public double[][] mul(double[][] A, double[][] B) {
        int m = A.length;
        int n = B[0].length;
        int o = B.length;
        double[][] matrix = new double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    matrix[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return matrix;
    }

    static public double determinant(double A[][]) {
        int n = A.length;
        if (n == 1) return A[0][0];
        double det = 0;
        double B[][] = new double[n - 1][n - 1];
        int l = 1;
        for (int i = 0; i < n; ++i) {
            int x = 0, y = 0;
            for (int j = 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    if (i == k) continue;
                    B[x][y] = A[j][k];
                    ++y;
                    if (y == n - 1) {
                        y = 0;
                        ++x;
                    }
                }
            }
            det += l * A[0][i] * determinant(B);
            l *= (-1);
        }
        return det;
    }
    static public String matrixToString(double[][] matrix){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                str.append(String.format("%1$-4s", (int) matrix[i][j]));
            }
            if(i!=matrix.length-1)
                str.append("\n");
        }
         return str.toString();
    }

    public static boolean equals(double[][] m1, double[][] m2){
        for(int i=0; i<m1.length; i++){
            for(int j=0; j<m1[0].length; j++){
                if(m1[i][j]!=m2[i][j])
                    return false;
            }
        }
        return true;
    }
}