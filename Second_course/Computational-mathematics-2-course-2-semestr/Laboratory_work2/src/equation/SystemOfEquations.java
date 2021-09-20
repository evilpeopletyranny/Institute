package equation;

/**
 * Интерфейс реализующий основные методы системы ур-ий.
 *
 * Общий вид системы:
 *      a11 * x1 + a12 * x2 + ... + a1n * xn = b1
 *      a21 * x1 + a22 * x2 + ... + a2n * xn = b2
 *                        ...
 *      an1 * x1 + an2 * x2 + ... + ann * x2 = bn
 *
 * Содержит 1 метод:
 *    setCoefficients - для задания коэфициентов при x
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SystemOfThreeEquations
 * */
public interface SystemOfEquations
{
    /**
     * Метод для задания коэфициентов при x
     *
     * @param coefficients - массив коэфициентов при членах уравнения.
     * */
    void setCoefficients(double[][] coefficients);

    /**
     * Метод для получения коэфициентов при x
     *
     * @return массив коэфициентов при членах уравнения.
     * */
    double[][] getCoefficients();

    /**
     * Метод для задания вектора b
     *
     * @param vectorB - массив значений вектора b.
     * */
    void setVectorB(double[] vectorB);

    /**
     * Метод для получения вектора b
     *
     * @return массив значений вектора b.
     * */
    double[] getVectorB();
}
