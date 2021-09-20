package solution_strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение методом Гаусса.
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class LagrangianInterpolationEquidistantNodes implements SolutionStrategy
{
    /**
     * Метод для приближенного вычисления значений
     * при помощи многочлена Лагранжа для равноостоящих узлов
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @param desiredValues - массив значений Xi, в которых необходимо найти значений функций
     *
     * @return список значений функции в заданных точках.
     * */
    @Override
    public List<Double> getSolution(double[][] coordinates, double[] desiredValues) {
        //Список для хранения ответов
        List<Double> resList = new LinkedList<>();

        //Вычисление шага между точками
        double h = coordinates[1][0] - coordinates[0][0];

        //Проход по каждому Хi в котором необходимо найти значение функции
        for (double desiredValue : desiredValues)
        {
            //Перемнная для хранения промежуточного резлуьтата
            double res = 0.0;

            for (int i = 0; i < coordinates.length; i++)
            {
                //Временная переменная для хранения результатов вычислений
                double temp = 1.0;

                for (int j = 0; j < coordinates.length; j++)
                {
                    //если i = j, то шаг пропускатеся
                    if (i != j)
                    {
                        //Вычисление членов произведения вида (Х - Хi -j*h)/(h(i-j))
                        temp *= (desiredValue - coordinates[0][0] - j * h) / (h * (i - j));
                    }
                }
                //Полученное произвдевение умножаем на Yi и добавляем к ответу
                res += temp * coordinates[i][1];
            }
            //Заносим ответ в список ответов
            resList.add(res);
        }
        return resList;
    }

    /**
     * Конструктор без параметров.
     * */
    public LagrangianInterpolationEquidistantNodes()
    {
    }
}
