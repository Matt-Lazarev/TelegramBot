import java.util.Scanner;
import java.lang.Math;

public class TaskGenerator {
    final int SUM = 0;
    final int SUB = 1;
    final int MUL = 2;
    final int DET = 3;
    MatrixAllFunc m;

    public void Training(int task) { //Основная функция обработки операций
        double[][] A, B;
        if (task == SUM) {
            int n = (int) (2 + Math.random() * 3);
            A = MatrixGenerator(n, n);
            B = MatrixGenerator(n, n);
            m = new MatrixAllFunc(A, B);
            MatrixOutput(A, false);
            System.out.print("\n+");
            MatrixOutput(B, true);
            System.out.print("\nInput result:\n");
            System.out.print(Compare(m.sum(A, B), false));
        } else if (task == SUB) {
            int n = (int) (2 + Math.random() * 3);
            A = MatrixGenerator(n, n);
            B = MatrixGenerator(n, n);
            m = new MatrixAllFunc(A, B);
            MatrixOutput(A, false);
            System.out.print("\n-");
            MatrixOutput(B, true);
            System.out.print("\nInput result:\n");
            System.out.print(Compare(m.sub(A, B), false));
        } else if (task == MUL) {
            int n = (int) (2 + Math.random() * 2);
            int k = (int) (2 + Math.random() * 2);
            A = MatrixGenerator(k, n);
            k = (int) (2 + Math.random() * 2);
            B = MatrixGenerator(n, k);
            m = new MatrixAllFunc(A, B);
            MulOutput(A);
            System.out.print("*\n");
            MulOutput(B);
            System.out.print("Input result:\n");
            System.out.print(Compare(m.mul(A, B), true));
        } else if (task == DET) {
            int n = (int) (2 + Math.random() * 3);
            A = MatrixGenerator(n, n);
            m = new MatrixAllFunc(A, A);
            MatrixOutput(A, false);
            System.out.print("\nInput result:\n");
            System.out.print(CompareDet(m.matrixDetetminant(A)));
        }
    }

    public String CompareDet(double a) { //Ввод определителя и его сравнение с результатом
        Scanner s = new Scanner(System.in);
        double b;
        if (s.hasNextDouble()) {
            b = s.nextDouble();
        } else {
            b = CheckFractionalNumber(s.next());
        }
        if (b == a) {
            return "Good job!";
        } else {
            return "Incorrect.\nMust be: " + a + "\nFound: " + b;
        }
    }

    public String Compare(double[][] A, boolean f) { //Ввод матрицы пользователя и её сравнение с результатом
        Scanner s = new Scanner(System.in);
        double[][] X = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (!f) {
                    if (s.hasNextDouble()) {
                        X[j][i] = s.nextDouble();
                    } else {
                        X[j][i] = CheckFractionalNumber(s.next());
                    }
                    if (X[j][i] != A[j][i]) {
                        return "Mistake in row: " + (i + 1) + ", col: " + (j + 1) + "\nMust be: " + A[j][i] + "\nFound: " + X[j][i];
                    }
                } else {
                    if (s.hasNextDouble()) {
                        X[i][j] = s.nextDouble();
                    } else {
                        X[i][j] = CheckFractionalNumber(s.next());
                    }
                    if (X[i][j] != A[i][j]) {
                        return "Mistake in row: " + (i + 1) + ", col: " + (j + 1) + "\nMust be: " + A[i][j] + "\nFound: " + X[i][j];
                    }
                }
            }
        }
        return "Good job!";
    }

    public double CheckFractionalNumber(String fractional) { //Обработка числа в дробном формате
        int slash = fractional.indexOf('/');
        double top = Double.parseDouble(fractional.substring(0, slash));
        double bot = Double.parseDouble(fractional.substring(slash + 1));
        return top / bot;
    }

    public double[][] MatrixGenerator(int n, int m) { //Создание и заполнение матрицы случайными числами
        double[][] A = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                A[i][j] = (int) (Math.random() * 21);
            }
        }
        return A;
    }

    public void MulOutput(double[][] A) { //Вывод матрицы для операции умножения
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[A.length - 1].length; j++) {
                System.out.printf("%1$-3s", (int) A[i][j]);
            }
            System.out.print("\n");
        }
    }

    public void MatrixOutput(double[][] A, boolean f) { //Вывод матрицы
        for (int i = 0; i < A.length; i++) {
            if (!f) {
                f = true;
            } else {
                System.out.print("\n");
            }
            for (int j = 0; j < A[0].length; j++) {
                System.out.printf("%1$-3s", (int) A[j][i]);
            }
        }
    }

    public static void main(String[] args) { //Для отладки
        new TaskGenerator().Training(new Scanner(System.in).nextInt());
    }
}