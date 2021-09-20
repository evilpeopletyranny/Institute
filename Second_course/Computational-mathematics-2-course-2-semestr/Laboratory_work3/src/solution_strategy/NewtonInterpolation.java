package solution_strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение при помощи интерполяционной формулы Ньютона
 * для равноотстоящих узлов
 *
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class NewtonInterpolation implements SolutionStrategy
{
    /**
     * Вспомогательный метод для вывода конечных разностей i-ого порядка
     * в виде "лестничном виде"
     *
     * @param finiteDifferences - список списков конечных разностей
     * */
    private void printFiniteDifferences(List<List<Double>> finiteDifferences)
    {
        for (int i = 0; i < finiteDifferences.size(); i++)
        {
            //Если выводимое число будет меньше 10, то задаем дополнительный пробел
            //перед его выводом для равномерного отображения в консоли
            if (i < 9)
            {
                System.out.print("Конечные разности  " + (i+1) + " порядка: ");
            }
            else
            {
                System.out.print("Конечные разности " + (i+1) + " порядка: ");
            }

            for (int j = 0; j < finiteDifferences.get(i).size(); j++)
            {
                //Если число меньше 0 то выводим как оно есть,
                //иначе задаем дополнительный пробле для равномерного вывода в консоль
                if (finiteDifferences.get(i).get(j) < 0)
                {
                    System.out.printf("%.5f", finiteDifferences.get(i).get(j));
                }
                else
                {
                    System.out.printf(" %.5f", finiteDifferences.get(i).get(j));
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Метод для вычисления приближенного значения функции при интерполяции вперед
     *
     * @param t                 - значения параметра t, зависящее от координаты Х, в
     *                            которой ищется значение
     * @param coordinates       - массив координат заданных точек
     * @param finiteDifferences - список списков конечных разностей
     *
     * @return - искомое значение функции
     * */
    private double directInterpolation(double t, double[][] coordinates, List<List<Double>> finiteDifferences)
    {
        //Переменная для хранения результат
        double res = 0.0;

        //К результату прибавляются Y0 + t*ΔY0
        res += coordinates[0][1];
        res += t * finiteDifferences.get(0).get(0);

        //Вычисление членов (t(t-1)..(t-n+1) * ΔnY0)/n!
        for (int i = 1; i < finiteDifferences.size(); i++)
        {
            double temp = 1.0;

            //Промежуточные вычисления знаменателя (t - 1)..(t - n+1)
            for (int j = 1; j <= i; j++)
            {
                temp *= (t - j);
            }

            //К текущему результату добавляем член вида: (t(t-1)..(t-n+1) * ΔnY0)/n!
            res += temp * t * finiteDifferences.get(i).get(0)/ getFact((i + 1));
        }

        return res;
    }

    /**
     * Метод для вычисления приближенного значения функции при интерполяции назад
     *
     * @param t                 - значения параметра t, зависящее от координаты Х, в
     *                            которой ищется значение
     * @param coordinates       - массив координат заданных точек
     * @param finiteDifferences - список списков конечных разностей
     *
     * @return - искомое значение функции
     * */
    private double reverseInterpolation(double t, double[][] coordinates, List<List<Double>> finiteDifferences)
    {
        //Переменная для хранения результат
        double res = 0.0;

        //К результату прибавляются Yn + t*ΔY(n-1)
        res += coordinates[coordinates.length -1][1];
        res += t * finiteDifferences.get(0).get(finiteDifferences.get(0).size()-1);

        //Вычисление членов (t(t+1)..(t+n-1) * ΔnY(n-1))/n!
        for (int i = 1; i < finiteDifferences.size(); i++)
        {
            double temp = 1.0;

            //Промежуточные вычисления знаменателя (t + 1)..(t + n-1)
            for (int j = 1; j <= i; j++)
            {
                temp *= (t + j);
            }

            //К текущему результату добавляем член вида: (t(t+1)..(t+n-1) * ΔnY(n-1))/n!
            res += t * temp * finiteDifferences.get(i).get(finiteDifferences.get(i).size()-1)/ getFact((i + 1));
        }

        return res;
    }

    /**
     * Вспомогательный метод для получения факториала
     *
     * @param n - число от которого необходимо получить факториал
     *
     * @return - факториал переданного числа
     * */
    private int getFact(int n)
    {
        int res = 1;

        while (n > 1)
        {
            res *= n;
            n--;
        }

        return res;
    }

    /**
     * Метод для приближенного вычисления значений
     * при помощи интерполяционной формулы Ньютона
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @param desiredValues - массив значений Xi, в которых необходимо найти значений функций
     *
     * @return список значений функции в заданных точках.
     * */
    @Override
    public List<Double> getSolution(double[][] coordinates, double[] desiredValues)
    {
        List<Double> resList = new LinkedList<>();

        //Создание списка списков для хранения значений конченых разностей
        List<List<Double>> finiteDifferences = new LinkedList<>();

        //Ссылка на временный список для хранения промежуточных значений
        //конечных разностей
        List<Double> tempList;

        //В промежуточный список заносятся конечные разности 1-ого порядка
        tempList = new LinkedList<>();
        for (int i = 0; i < coordinates.length - 1; i++)
        {
            //Вычисление конечных разностей
            tempList.add(coordinates[i + 1][1] - coordinates[i][1]);
        }
        //промежуточный список ханосится в список списков кончеых разностей
        finiteDifferences.add(tempList);

        //На каждом i-ом шаге вычисляем значения конченых разностей нового порядка
        //и заносим в промежуточный список.
        //Полученный промежуточный список заносим в список списков промежуточных разностей
        for (int i = 0; i < coordinates.length-2; i++)
        {
            tempList = new LinkedList<>();    //инициализация промежуточного списка пустым списком
            for (int j = 0; j < finiteDifferences.get(i).size() - 1; j++)
            {
                //Вычисление конечных разностей
                tempList.add(finiteDifferences.get(i).get(j + 1) - finiteDifferences.get(i).get(j));
            }
            finiteDifferences.add(tempList);
        }

        //Вывод "лестницы" конечных разностей
        printFiniteDifferences(finiteDifferences);

        //Вычисление шага h
        double h = coordinates[1][0] - coordinates[0][0];

        //Перемнная для хранения параметра t
        double t;

        //Вычисление середины отрезка переданных X
        double midPoint = (coordinates[0][0] + coordinates[coordinates.length - 1][0]) / 2;

        //Для каждого числа из массива Хi, в которых необходимо найти значения функции
        //находим значения функции
        for (double desiredValue : desiredValues)
        {
            //Если Xi лежит в промежутке [x0; (x0 + xn) / 2]
            if (desiredValue < midPoint)
            {
                //t вычисляется как (x - x0)/h
                t = (desiredValue - coordinates[0][0]) / h;

                //В список ответов заносим значение полученное при интерполяции вперед
                resList.add(directInterpolation(t, coordinates, finiteDifferences));
            }
            //Иначе Xi лежит в промежутке [(x0 - xn)/2; xn]
            else
            {
                //t вычисляется как (x - xn)/h
                t = (desiredValue - coordinates[coordinates.length - 1][0]) / h;

                //В список ответов заносим значение полученное при интерполяции назад
                resList.add(reverseInterpolation(t, coordinates, finiteDifferences));
            }
        }

        return resList;
    }

    /**
     * Конструктор без параметров.
     * */
    public NewtonInterpolation()
    {
    }
}
