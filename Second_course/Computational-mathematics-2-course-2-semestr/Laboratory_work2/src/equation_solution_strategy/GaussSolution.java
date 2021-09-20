package equation_solution_strategy;

import equation.SystemOfEquations;
import validator.Validator;

/**
 * Класс реализующий решение методом Гаусса.
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class GaussSolution implements SolutionStrategy
{
    /**
     * Метод для получения решений методом Гаусса.
     *   Все шаги данного метода прописанны только Варианта №15
     *   ЛР №2
     *
     * @param system    - система, которую необходимо решить
     * @param validator - валидатор, с заданным параметром проверки
     * @return список значений, являющимися решениями данной системы уравнений.
     * */
    @Override
    public double[] getSolution(SystemOfEquations system, Validator validator)
    {
        double[][] coefficients     = new double[3][3];            //перезаписываем значения из массива
        double[][] tempCoefficients = system.getCoefficients();    //коэффциентов системы в промежуточный массив
        for (int i = 0; i < coefficients.length; i++)              //в противном случае при работе с массивом
        {                                                          //меняются коэф-ты в самой системе
            System.arraycopy(tempCoefficients[i], 0, coefficients[i], 0, coefficients[0].length);
        }

        double[] vectorB = new double[3];                          //аналогичную перезапись производим
        //для вектора b
        System.arraycopy(system.getVectorB(), 0, vectorB, 0, vectorB.length);

        double[]   currentApproximation = new double[4];           //массив для хранения вектора текущих
        currentApproximation[3] = 0;

        //1-ую строку делим на 1.6
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[0][i] /= 1.6;
        }
        vectorB[0] /= 1.6;

        //от 2 строки отнимаем 1 строку, умноженную на 0.28;
        //от 3 строки отнимаем 1 строку, умноженную на 0.38
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[1][i] = coefficients[1][i] - 0.28 * coefficients[0][i];
            coefficients[2][i] = coefficients[2][i] - 0.38 * coefficients[0][i];
        }
        vectorB[1] = vectorB[1] - vectorB[0] * 0.28;
        vectorB[2] = vectorB[2] - vectorB[0] * 0.38;

        //2-ую строку делим на 0.439
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[1][i] /= 0.439;
        }
        vectorB[1] /= 0.439;

        //от 1 строки отнимаем 2 строку, умноженную на 0.075;
        //от 3 строки отнимаем 2 строку, умноженную на 0.2215
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[0][i] = coefficients[0][i] - 0.075  * coefficients[1][i];
            coefficients[2][i] = coefficients[2][i] - 0.2215 * coefficients[1][i];
        }
        vectorB[0] = vectorB[0] - 0.075  * vectorB[1];
        vectorB[2] = vectorB[2] - 0.2215 * vectorB[1];

        //3-ую строку делим на -53.5199430523918
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[2][i] /= -53.5199430523918;
        }
        vectorB[2] /= -53.5199430523918;

        //от 1 строки отнимаем 3 строку, умноженную на 0.564636;
        //к 2 строке добавляем 3 строку, умноженную на 2.7784738041
        for (int i = 0; i < coefficients.length; i++)
        {
            coefficients[0][i] = coefficients[0][i] - 0.564636     * coefficients[2][i];
            coefficients[1][i] = coefficients[1][i] + 2.7784738041 * coefficients[2][i];
        }
        vectorB[0] = vectorB[0] - 0.564636 * vectorB[2];
        vectorB[1] = vectorB[1] + 2.7784738041 * vectorB[2];

        //После последнего шага система принимает вид:
        //   1 0 0 | -0.03078
        //   0 1 0 | 1.92454
        //   0 0 1 | -0.00297
        //
        //Дальше сопоставляем иксы и их значения из вектора b
        for (double[] coefficient : coefficients)
        {
            for (int j = 0; j < coefficients[0].length; j++)
            {
                if (coefficient[j] == 1.0) {
                    currentApproximation[j] = vectorB[j];
                }
            }
        }

        return currentApproximation;
    }

    /**
     * Конструктор без параметров.
     * */
    public GaussSolution()
    {
    }
}
