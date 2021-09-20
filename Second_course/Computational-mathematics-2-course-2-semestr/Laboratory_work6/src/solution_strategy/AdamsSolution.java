package solution_strategy;

import function.Function;

/**
 * Класс реализующий решение простого дифференциального
 * уравнения методом Адамса.
 *
 * @see SolutionStrategy
 * */
public class AdamsSolution implements SolutionStrategy
{
    /**
     * Метод для получения массива точек являющихся решением
     * дифференциальных уравнений при помощи метода Адамса.
     *
     * @param function - уравнение значение которго необходимо получить
     * @param x        - начальное значение х
     * @param xn       - конченое  значение х
     * @param y        - начальное значение y
     * @param h        - величина шага
     * */
    @Override
    public void getSolution(Function function, double x, double xn, double y, double h)
    {
        //Массив для хранения результатов
        double[][] res = new double[11][2];

        //Добавляем в массив начальные условия
        res[0][0] = x;
        res[0][1] = y;

        //Переменные для расчета методом Рунге-Кутта
        double K1;
        double K2;
        double K3;
        double K4;

        //Вычисляем начальный отрезок методом Рунге-Кутта
        for (int i = 1; i < 4; i++)
        {
            K1 = function.getValue(x , y);
            K2 = function.getValue(x + h / 4.0, y + (h / 4.0) * K1);
            K3 = function.getValue(x + h / 2.0, y + (h / 2.0) * K2);
            K4 = function.getValue(x + h, y + h * K1 - 2.0 * h * K2 + 2.0 * h * K3);

            y = y + (h * (K1 + 2.0 * K2 + 2.0 * K3 + K4)) / 6.0;
            x += h;

            res[i][0] = x;
            res[i][1] = y;

            System.out.printf("%.4f", res[i][0]);
            System.out.print("  ");
            System.out.printf("%.4f", res[i][1]);
            System.out.println();
        }

        //Вычисляем все остальные значения при помощи метода Адамса
        double[] df = new double[3];
        for (int i = 4; i < 11; i++)
        {
            df[0] = res[i][1] - res[i - 1][1];
            df[1] = res[i][1] - 2.0 * res[i - 1][1] + res[i - 2][1];
            df[1] = res[i][1] - 3.0 * res[i - 1][1] + 3.0 * res[i - 2][1] - res[i - 3][1];

            y = y + h * function.getValue(x, y) + (df[0] * h * h / 2.0) + 5.0 * (df[1] * h * h * h / 12.0)
                    + 3.0 * (df[2] * h * h * h * h / 8.0);
            x += h;

            res[i][0] = x;
            res[i][1] = y;

            System.out.printf("%.4f", res[i][0]);
            System.out.print("  ");
            System.out.printf("%.4f", res[i][1]);
            System.out.println();
        }
    }
}
