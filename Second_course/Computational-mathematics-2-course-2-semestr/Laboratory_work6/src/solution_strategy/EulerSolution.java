package solution_strategy;

import function.Function;

/**
 * Класс реализующий решение простого дифференциального
 * уравнения методом Эйлера.
 *
 * @see SolutionStrategy
 * */
public class EulerSolution implements SolutionStrategy
{
    /**
     * Метод для получения массива точек являющихся решением
     * дифференциальных уравнений при помощи метода Эйлера.
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

        //по формуле высчитываем все остальные точки
        while (x <= xn)
        {
            y = y + h * function.getValue(x, y);
            x += h;

            System.out.printf("%.4f", x);
            System.out.print("  ");
            System.out.printf("%.4f", y);
            System.out.println();
        }
    }
}
