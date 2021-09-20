package equation_solution_strategy;

import function.Function;

/**
 * Общий интерфейс всех методов вычисления интеграла.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see MediumRectangleMethod
 * @see TrapeziumMethod
 * @see SimpsonMethod
 * */
public interface SolutionStrategy
{
    /**
     * Метод для вычисления значения интеграла.
     *
     * @param function           - функция, для которой необходимо вычислить интеграл
     * @param a                  - нижний  предел интегрирования
     * @param b                  - верхний предел интегрирования
     * @param n                  - кол-во интервалов разбиения
     * @return значение инетграла для данной функции.
     * */
    double getSolution(Function function, double a, double b, double n);

    /**
     * Метод для вычисления значения погрешности.
     *
     * @param function           - функция, для которой необходимо вычислить интеграл
     * @param a                  - нижний  предел интегрирования
     * @param b                  - верхний предел интегрирования
     * @param n                  - кол-во интервалов разбиения
     * @return значение инетграла для данной функции.
     * */
    double getError(Function function, double a, double b, double n);
}
