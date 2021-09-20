package equation_system;

/**
 * Система из 4ех уравнений
 *
 * @see SystemOfEquations
 * */
public class SystemOfFourEquations implements SystemOfEquations
{
    //Переменные для хранения коэфициентов и вектора B
    private final double[][] coefficients;
    private final double[]   vectorB ;

    /**
     * Конструктор с параметрами.
     *
     * @param coefficients - массив коэфициентов
     * @param vectorB      - вектор B
     * */
    public SystemOfFourEquations(double[][] coefficients, double[] vectorB)
    {
        this.coefficients = coefficients;
        this.vectorB = vectorB;
    }

    /**
     * Метод получение двумерного массива коэфициентов
     * систьемов уравнения.
     *
     * @return двумерный массив коэфициентов системы уравнений
     * */
    public double[][] getCoefficients()
    {
        return coefficients;
    }

    /**
     * Метод получение вектора B системы уравнений.
     *
     * @return вектор B
     * */
    public double[] getVectorB()
    {
        return vectorB;
    }

    /**
     * Метод вывода системы уравнений в консоль.
     * */
    public void printSystem()
    {
        System.out.printf("%.4fp1 + %.4fp2 + %.4fp3 + %.4fp4 = %.4f\n", coefficients[0][0], coefficients[0][1], coefficients[0][2], coefficients[0][3], vectorB[0]);
        System.out.printf("%.4fp1 + %.4fp2 + %.4fp3 + %.4fp4 = %.4f\n", coefficients[1][0], coefficients[1][1], coefficients[1][2], coefficients[1][3], vectorB[1]);
        System.out.printf("%.4fp1 + %.4fp2 + %.4fp3 + %.4fp4 = %.4f\n", coefficients[2][0], coefficients[2][1], coefficients[2][2], coefficients[2][3], vectorB[2]);
        System.out.printf("%.4fp1 + %.4fp2 + %.4fp3 + %.4fp4 = %.4f\n", coefficients[3][0], coefficients[3][1], coefficients[3][2], coefficients[3][3], vectorB[3]);
    }
}
