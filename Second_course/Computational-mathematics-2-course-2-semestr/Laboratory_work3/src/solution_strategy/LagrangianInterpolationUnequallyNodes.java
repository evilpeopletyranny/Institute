package solution_strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение при помощи многочлена Лагранжа
 * для неравноотстоящих узлов
 *
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class LagrangianInterpolationUnequallyNodes implements SolutionStrategy
{
    /**
     * Метод для приближенного вычисления значений
     * при помощи многочлена Лагранжа для неравноотстоящих узлов
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @param desiredValues - массив значений Xi, в которых необходимо найти значений функций
     *
     * @return список значений функции в заданных точках.
     * */
    @Override
    public List<Double> getSolution(double[][] coordinates, double[] desiredValues)
    {
        //Список для хранения ответов
        List<Double> resList = new LinkedList<>();

        //Проход по каждому Хi в котором необходимо найти значение функции
        for (double desiredValue : desiredValues)
        {
            //Перемнная для хранения промежуточного резлуьтат
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
                        //Вычисление членов произведения вида (X-Xj)/(Xi-Xj)
                        temp *= (desiredValue - coordinates[j][0]) / (coordinates[i][0] - coordinates[j][0]);
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
    public LagrangianInterpolationUnequallyNodes()
    {
    }
}
