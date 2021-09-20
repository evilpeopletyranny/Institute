package equation_system_solution;

import equation_system.SystemOfEquations;

/**
 * Общий интерфейс всех стратегий решения.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see GaussSolution
 * */
public interface SolutionStrategy
{
    /**
     * Метод для вызова той или иной стратегии решения.
     *
     * @param system    - система, которую необходимо решить
     * @return список значений, являющимися решениями данной системы уравнений.
     * */
    double[] getSolution(SystemOfEquations system);
}
