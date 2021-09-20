package equation_solution_strategy;

import function.Function;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий вычисление интеграла методом
 * трапеций.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class TrapeziumMethod implements SolutionStrategy
{
    /**
     * Метод для вычисления значения интеграла
     * методом трапеций.
     *
     * @param function           - функция, для которой необходимо вычислить интеграл
     * @param a                  - нижний  предел интегрирования
     * @param b                  - верхний предел интегрирования
     * @param n                  - кол-во итераций
     * @return значение инетграла для данной функции.
     * */
    @Override
    public double getSolution(Function function, double a, double b, double n)
    {
        //Вычисляем величену шага h
        double h = (b - a)/ n;

        //пеменная для хранения значения интеграла
        double res = 0.0;

        //Выполняем заданное кол-во итераций
        for (int i = 1; i < n; ++i)
        {
            //К результату прибавляем значение f(Xi)
            //Начальная точка a, последующие шаги вычисляются как: h * i
            res += function.getValueAtX(a + h * i);
        }

        //Добавляем к результату (y0 + yn) / 2;
        res += (function.getValueAtX(a) + function.getValueAtX(b)) / 2.0;

        //Поскольку шаг равномерный, то домножаем результат на h
        res *= h;

        return res;
    }

    /**
     * Метод для вычисления значения погрешности при вычислении
     * интеграла методом трапеций.
     *
     * @param function           - функция, для которой необходимо вычислить интеграл
     * @param a                  - нижний  предел интегрирования
     * @param b                  - верхний предел интегрирования
     * @param n                  - кол-во интервалов разбиения
     * @return значение инетграла для данной функции.
     * */
    @Override
    public double getError(Function function, double a, double b, double n)
    {
        List<Double> yList = new LinkedList<>();

        //Вычисляем величену шага h
        double h = (b - a)/ n;

        //Получаем список значений третьей производной в промежутках
        for (int i = 0; i < n; i++)
        {
            //Получаем значения второй производной функциий в точках от a до b с шагом h
            yList.add(function.getSecDerivativeAtX(a + h * (i)));
        }

        //Находим максимальное значение второй производной на промежутке от a до b
        double error = Math.abs(Collections.max(yList));

        //Вычисляем погрешность: Rn = (b-a)/12 * h^2 * max(f''(x))
        error *= ((b - a) / 12.0) * h * h;

        return error;
    }

    /**
     * Конструктор без параметров.
     * */
    public TrapeziumMethod()
    {
    }
}
