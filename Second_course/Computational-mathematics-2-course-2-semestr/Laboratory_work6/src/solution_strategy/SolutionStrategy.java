package solution_strategy;

import function.Function;

/**
 * Общий интерфейс всех стратегий решения.
 * */
public interface SolutionStrategy
{
    /**
     * Метод для получения массива точек являющихся решением
     * дифференциальных уравнений.
     * */
    void getSolution(Function function, double x, double xn, double y, double h);
}
