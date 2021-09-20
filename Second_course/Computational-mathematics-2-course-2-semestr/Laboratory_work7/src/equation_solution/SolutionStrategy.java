package equation_solution;

import equation.Equation;

import java.util.List;

/**
 * Общий интерфейс всех стратегий решения.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see BisectionSolution
 * */
public interface SolutionStrategy
{
    /**
     * Метод для вызова той или иной стратегии решения.
     *
     * @param equation  - ур-ие, которое необходимо решить
     * @return список значений, которые являются решениями данного
     *         ур-ия
     * */
    List<Double> getSolution(Equation equation);
}
