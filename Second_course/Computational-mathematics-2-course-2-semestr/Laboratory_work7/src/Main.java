import equation.FourthDegreePolynomial;
import equation_solution.BisectionSolution;
import equation_system.SystemOfEquations;
import equation_system.SystemOfFourEquations;
import equation_system_solution.GaussSolution;
import matrix.Matrix;
import matrix.Matrix4x4;

import java.util.List;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация седьмой лабораторной работы
 * по диспилине: Вычислительная математика
 * Вариант №15
 *
 * Текст задания:
 *  Используя метод Крылова, найти собственные числа и собственные
 *  векторы матрицы. Собственные числа определить с четырьмя верными
 *  цифрами, а собственные векторы – с тремя десятичными знаками.
 *
 *   Исходная матрица:
 *   0.5  1.2   2    1
 *   1.2   2   0.5  1.2
 *    2   0.5   1   0.5
 *    1   1.2  0.5  1.6
 *
 * @release:     10.05.21
 * @last_update: 10.05.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main
{
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    public static final String RESET = "\u001B[0m";
    public static final String PURPLE = "\u001B[35m";

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\tЛабораторная работа №7 <<" + PURPLE + "Определение собственных чисел и собственных" +
                " векторов матрицы методом Крылова»" + RESET + ">>");
        double[] y0, y1, y2, y3, y4;

        //задаем вектор y0
        y0 = new double[]{0.0, 1.0, 0.0, 0.0};

        //создаем объект матрица, передаем туда исходные данные
        Matrix matrix2 = new Matrix4x4(new double[][]
                {{0.5, 1.2, 2.0, 1.0},
                 {1.2, 2.0, 0.5, 1.2},
                 {2.0, 0.5, 1.0, 0.5},
                 {1.0, 1.2, 0.5, 1.6}});

        System.out.println("Исходная матрица:");
        System.out.println(matrix2);
        System.out.println();


        //вывод вектор y0
        System.out.println("Вектор y0:");
        printVector(y0);
        System.out.println();

        //вычсиление вектор y1 и его вывод
        y1 = matrix2.multiplyByVector(y0);
        System.out.println("Вектор y1:");
        printVector(y1);
        System.out.println();

        //вычсиление вектор y2 и его вывод
        y2 = matrix2.multiplyByVector(y1);
        System.out.println("Вектор y2:");
        printVector(y2);
        System.out.println();

        //вычсиление вектор y3 и его вывод
        y3 = matrix2.multiplyByVector(y2);
        System.out.println("Вектор y3:");
        printVector(y3);
        System.out.println();

        //вычсиление вектор y4 и его вывод
        y4 = matrix2.multiplyByVector(y3);
        System.out.println("Вектор y4:");
        printVector(y4);
        System.out.println();


        //Создание объекта - система уравнений, на основе полученных векторов
        System.out.println("Полученная система уравнений:");
        SystemOfEquations system = new SystemOfFourEquations(
                new double[][]{{y3[0], y2[0], y1[0], y0[0]},
                {y3[1], y2[1], y1[1], y0[1]},
                {y3[2], y2[2], y1[2], y0[2]},
                {y3[3], y2[3], y1[3], y0[3]}},

                new double[]{-y4[0], -y4[1], -y4[2], -y4[3]}
        );
        system.printSystem();
        System.out.println();


        //нахождение корней системы методом Гаусса
        System.out.println("Решение системы уравнений при помощи метода Гаусса:");
        double[] p = new GaussSolution().getSolution(system);
        printVector(p);


        //Создаем объект уравнение на основе полученных
        //решений системы
        System.out.println();
        System.out.println("Полученное уравнение P(λ):");
        FourthDegreePolynomial equation = new FourthDegreePolynomial();
        equation.setCoefficients(p);
        equation.printEquation();

        //Получение собственных чисел - решений полученного уравнения
        System.out.println();
        System.out.println();
        List<Double> res = new BisectionSolution().getSolution(equation);
        System.out.println("Решения уравнения: ");
        System.out.printf("λ1 = %.4f\n", res.get(0));
        System.out.printf("λ2 = %.4f\n", res.get(1));
        System.out.printf("λ3 = %.4f\n", res.get(2));
        System.out.printf("λ4 = %.4f\n", res.get(3));
        System.out.println();
        System.out.println();


        //На основе полученных собственных чисел
        //векторов p и векторов y0-y4 чеез схему Горнера
        System.out.println("Нахождение собсвтенных векторов:");
        double[][] ownVectors = new double[4][4];
        for (int k = 0; k < 4; k++)
        {
            double[] q = new double[5];
            q[0] = 1.0;

            //объединяем все значения y0-y4 в один массив
            double[][] tempArr = new double[4][4];
            System.arraycopy(y0, 0, tempArr[0], 0, y0.length);
            System.arraycopy(y1, 0, tempArr[1], 0, y1.length);
            System.arraycopy(y2, 0, tempArr[2], 0, y2.length);
            System.arraycopy(y3, 0, tempArr[3], 0, y3.length);

            //вычисление значения qi
            for (int j = 1, i = 0; i < p.length; i++, j++)
            {
                q[j] = res.get(k) * q[j - 1] + p[i];
            }

            //умножаем вектор Y3 на q0
            for (int j = 0; j < y3.length; j++)
            {
                tempArr[3][j] *= q[0];
            }

            //умножаем вектор Y2 на q1
            for (int j = 0; j < y3.length; j++)
            {
                tempArr[2][j] *= q[1];
            }

            //умножаем вектор Y1 на q2
            for (int j = 0; j < y3.length; j++)
            {
                tempArr[1][j] *= q[2];
            }

            //умножаем вектор Y0 на q3
            for (int j = 0; j < y3.length; j++)
            {
                tempArr[0][j] *= q[3];
            }

            //Получаем собтсвенный вектор путем сложения
            //произвдеений qi на вектор Yj
            ownVectors[k][0] = tempArr[0][0] + tempArr[1][0] + tempArr[2][0] + tempArr[3][0];
            ownVectors[k][1] = tempArr[0][1] + tempArr[1][1] + tempArr[2][1] + tempArr[3][1];
            ownVectors[k][2] = tempArr[0][2] + tempArr[1][2] + tempArr[2][2] + tempArr[3][2];
            ownVectors[k][3] = tempArr[0][3] + tempArr[1][3] + tempArr[2][3] + tempArr[3][3];

            System.out.println("Собственный вектор V" + (k + 1) + ": ");
            printVector(ownVectors[k]);
            System.out.println();
        }
    }

    /**
     * Вспомогательный метод для вывода вектора.
     *
     * @param vector - вектор, который необходимо вывести
     * */
    public static void printVector(double[] vector)
    {
        for (double v : vector)
        {
            if (v < 0.0)
            {
                System.out.printf("%.4f\n", v);
            }
            else
            {
                System.out.printf(" %.4f\n", v);
            }
        }
    }
}
