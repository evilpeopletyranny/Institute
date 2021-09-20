package solution_strategy;

/**
 * Общий интерфейс всех стратегий решения.
 *
 * @see DifferenceForm
 * @see LagrangianSolution
 * @see NewtonSolution
 * @author Vladislav Sapozhnikov 19-IVT-3
 * */
public interface SolutionStrategy
{
    /**
     * Метод для получения первой производной различными способами.
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @return список значений производной в заданных точках.
     * */
    double[][] getFirstDerivative(double[][] coordinates);

    /**
     * Метод для получения второй производной различными способами.
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @return список значений производной в заданных точках.
     * */
    double[][] getSecondDerivative(double[][] coordinates);
}
