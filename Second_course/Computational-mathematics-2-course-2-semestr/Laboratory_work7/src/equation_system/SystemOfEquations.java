package equation_system;

/**
 * Интерфейс системы уравнений.
 *
 * @see SystemOfFourEquations
 * */
public interface SystemOfEquations
{
    /**
     * Получение двумерного массива коэфициентов системы.
     *
     * @return двумерный массив коэфифицентов системы.
     * */
    double[][] getCoefficients();

    /**
     * Получение вектора B (вектор числе с правой стороны от '=')
     *
     * @return вектор B
     * */
    double[]   getVectorB();

    /**
     * Метод вывода системы уравненийи в консоль.
     * */
    void printSystem();
}
