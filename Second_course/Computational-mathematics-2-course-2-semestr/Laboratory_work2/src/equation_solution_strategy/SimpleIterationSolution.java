package equation_solution_strategy;

import equation.SystemOfEquations;
import validator.Validator;

/**
 * Класс реализующий решение методом Простой итерации (метод Якоби).
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class SimpleIterationSolution implements SolutionStrategy
{
    /**
     * Метод для получения решений методом Простой итерации (метод Якоби).
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

        double[]   prevApproximation    = {0.0, 0.0, 0.0};             //инициализуем значения вектора
                                                                       //прошлый приближений нулями

        double[]   currentApproximation = new double[4];               //массив для хранения вектора текущих
                                                                       //приближений. В последний элемент записывается
                                                                       //кол-во итераций

        int count = 0;                                                 //счетчик итераций

        //В цикле вычисляем значения вектора приближений
        for (;;)
        {
            currentApproximation[0] = (vectorB[0] - (coefficients[0][1]*prevApproximation[1] +    //значение X1
                    coefficients[0][2] * prevApproximation[2])) / coefficients[0][0];

            currentApproximation[1] = (vectorB[1] - (coefficients[1][0]*prevApproximation[0] +    //значение Х2
                    coefficients[1][2] * prevApproximation[2])) / coefficients[1][1];

            currentApproximation[2] = (vectorB[2] - (coefficients[2][0]*prevApproximation[0] +    //значение Х3
                    coefficients[2][1] * prevApproximation[1])) / coefficients[2][2];

            //При каждой итерации увеличиваем счетчик
            count++;

            //Проверка на соответсвие условия валидатора
            if (validator.isValid(currentApproximation, prevApproximation)) break;

            //В вектор прошлый значений заносим текущие
            System.arraycopy(currentApproximation, 0, prevApproximation, 0, prevApproximation.length);
        }

        //Заносим кол-во итераций в конец вектора приблжений
        currentApproximation[3] = count;

        return currentApproximation;
    }

    /**
     * Конструктор без параметров.
     * */
    public SimpleIterationSolution()
    {
    }
}
