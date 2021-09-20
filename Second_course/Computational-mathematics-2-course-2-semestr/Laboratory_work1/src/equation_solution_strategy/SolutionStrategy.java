package equation_solution_strategy;

import equation.Equation;
import validator.Validator;

import java.util.List;

/**
 * Общий интерфейс всех стратегий решения.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see BisectionSolution
 * @see ChordSolution
 * @see NewtonSolution
 * @see SimpleIterationSolution
 * */
public interface SolutionStrategy
{
    /**
     * Метод для вызова той или иной стратегии решения.
     *
     * @param equation  - ур-ие, которое необходимо решить
     * @param validator - валидатор с заданным условием проверки
     * @return список значений, которые являются решениями данного
     *         ур-ия
     * */
    List<Double> getSolution(Equation equation, Validator validator);
}
