package solution_strategy;

import function.Function;

/**
 * Класс реализующий решение простого дифференциального
 * уравнения методом Рунге-Кутта.
 *
 * @see SolutionStrategy
 * */
public class RungeKuttSolution implements SolutionStrategy
{
    /**
     * Метод для получения массива точек являющихся решением
     * дифференциальных уравнений при помощи метода Рунге-Кутта.
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
        //Добавляем в массив начальные условия
        System.out.printf("%.4f", x);
        System.out.print("  ");
        System.out.printf("%.4f", y);
        System.out.println();

        double K1;
        double K2;
        double K3;
        double K4;

        //по формуле высчитываем все остальные точки
        while (x <= xn)
        {
            K1 = function.getValue(x , y);
            K2 = function.getValue(x + h / 4.0, y + (h / 4.0) * K1);
            K3 = function.getValue(x + h / 2.0, y + (h / 2.0) * K2);
            K4 = function.getValue(x + h, y + h * K1 - 2.0 * h * K2 + 2.0 * h * K3);

            y = y + (h * (K1 + 2.0 * K2 + 2.0 * K3 + K4)) / 6.0;
            x += h;

            System.out.printf("%.4f", x);
            System.out.print("  ");
            System.out.printf("%.4f", y);
            System.out.println();
        }
    }
}
