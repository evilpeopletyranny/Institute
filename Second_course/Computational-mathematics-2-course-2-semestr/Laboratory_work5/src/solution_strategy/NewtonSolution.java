package solution_strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, в котором реализованы методы нахождения
 * первых и вторых произовдных при помощи многочлена Ньютона
 *
 * @see SolutionStrategy
 * */
public class NewtonSolution implements SolutionStrategy
{
    /**
     * Метод для нахождения конечных приращений
     *
     * @param coordinates - массив координат для которого необходимо найти
     *                      конечные приращения
     * @return список списокв конечных приращений
     * */
    private List<List<Double>> getFiniteDifferences(double[][] coordinates)
    {
        //Создание списка списков для хранения значений конченых разностей
        List<List<Double>> finiteDifferences = new ArrayList<>();

        //Ссылка на временный список для хранения промежуточных значений
        //конечных разностей
        List<Double> tempList;

        //В промежуточный список заносятся конечные разности 1-ого порядка
        tempList = new ArrayList<>();
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
            tempList = new ArrayList<>();    //инициализация промежуточного списка пустым списком
            for (int j = 0; j < finiteDifferences.get(i).size() - 1; j++)
            {
                //Вычисление конечных разностей
                tempList.add(finiteDifferences.get(i).get(j + 1) - finiteDifferences.get(i).get(j));
            }
            finiteDifferences.add(tempList);
        }

        return finiteDifferences;
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
     * Метод для получения первых проиводных при помощи
     * многочлена Ньютона
     *
     * @param coordinates - массив координат точек, в которых
     *                      необходимо найти проиводные
     * @return массив значений первых производных
     * */
    @Override
    public double[][] getFirstDerivative(double[][] coordinates)
    {
        //Вычисление шага
        double h = coordinates[1][0] - coordinates[0][0];
        double[][] resArr = new double[15][2];

        //Нахождение производных для первых 6ти членов
        //выделяем координаты необходимых точек
        double[][] tempArr = new double[6][2];
        for (int i = 0; i < 6; i++)
        {
            tempArr[i][0] = coordinates[i][0];
            tempArr[i][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        List<List<Double>> finiteDifference = getFiniteDifferences(tempArr);

        //Расчет производных по формуле
        for (int i = 0; i < 6; i++)
        {
            double t = (coordinates[i][0] - coordinates[0][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(0).get(0)
                    + ((2.0 * t - 1) * finiteDifference.get(1).get(0) / getFact(2))
                    + ((3.0*t*t - 6.0*t + 2) * finiteDifference.get(2).get(0) / getFact(3)
                    + ((4.0*t*t*t + 18.0*t*t + 22.0*t - 6.0) * finiteDifference.get(3).get(0)) / getFact(4))
                    + ((5.0*t*t*t*t - 40.0*t*t*t + 105.0*t*t -100.0*t +24.0) * finiteDifference.get(4).get(0) / getFact(5)))
                        / h;
        }

        //Нахождение производных для следующих 3 членов
        //выделяем координаты необходимых точек
        tempArr = new double[6][2];
        for (int i = 6, j = 0; i < 12; i++, j++)
        {
            tempArr[j][0] = coordinates[i][0];
            tempArr[j][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        finiteDifference = getFiniteDifferences(tempArr);

        //Расчет производных по формуле
        for (int i = 6; i < 10; i++)
        {
            double t = (coordinates[i][0] - coordinates[6][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(0).get(0)
                    + ((2.0 * t - 1) * finiteDifference.get(1).get(0) / getFact(2))
                    + ((3.0*t*t - 6.0*t + 2) * finiteDifference.get(2).get(0) / getFact(3)
                    + ((4.0*t*t*t + 18.0*t*t + 22.0*t - 6.0) * finiteDifference.get(3).get(0)) / getFact(4))
                    + ((5.0*t*t*t*t - 40.0*t*t*t + 105.0*t*t -100.0*t +24.0) * finiteDifference.get(4).get(0) / getFact(5)))
                    / h;
        }

        //Нахождение производных для последних 6ти членов
        //выделяем координаты необходимых точек
        tempArr = new double[6][2];
        for (int i = 9, j = 0; i < 15; i++, j++)
        {
            tempArr[j][0] = coordinates[i][0];
            tempArr[j][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        finiteDifference = getFiniteDifferences(tempArr);


        //Расчет производных по формуле
        for (int i = 9; i < 15; i++)
        {
            double t = (coordinates[i][0] - coordinates[9][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(0).get(0)
                    + ((2.0 * t - 1) * finiteDifference.get(1).get(0) / getFact(2))
                    + ((3.0*t*t - 6.0*t + 2) * finiteDifference.get(2).get(0) / getFact(3)
                    + ((4.0*t*t*t + 18.0*t*t + 22.0*t - 6.0) * finiteDifference.get(3).get(0)) / getFact(4))
                    + ((5.0*t*t*t*t - 40.0*t*t*t + 105.0*t*t -100.0*t +24.0) * finiteDifference.get(4).get(0) / getFact(5)))
                    / h;
        }

        return resArr;
    }

    /**
     * Метод для получения вторых производных при помощи
     * многочлена Ньютона
     *
     * @param coordinates - массив координат точек, в которых
     *                      необходимо найти производные
     * @return массив значений вторых производных
     * */
    @Override
    public double[][] getSecondDerivative(double[][] coordinates)
    {
        //Вычисление шага
        double h = coordinates[1][0] - coordinates[0][0];
        double[][] resArr = new double[15][2];

        //Нахождение производных для первых 6ти членов
        //выделяем координаты необходимых точек
        double[][] tempArr = new double[6][2];
        for (int i = 0; i < 6; i++)
        {
            tempArr[i][0] = coordinates[i][0];
            tempArr[i][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        List<List<Double>> finiteDifference = getFiniteDifferences(tempArr);

        //Расчет производных по формуле
        for (int i = 0; i < 6; i++)
        {
            double t = (coordinates[i][0] - coordinates[0][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(1).get(0)
                    + ((6.0 * t - 6.0) * finiteDifference.get(2).get(0) / getFact(3))
                    + ((12.0*t*t - 36.0*t + 22.0) * finiteDifference.get(3).get(0) / getFact(4)
                    + ((20.0*t*t*t - 120.0*t*t + 210.0*t - 100.0) * finiteDifference.get(4).get(0)) / getFact(5)))
                    / (h*h);
        }


        //Нахождение производных для следующих 3 членов
        //выделяем координаты необходимых точек
        tempArr = new double[6][2];
        for (int i = 6, j = 0; i < 12; i++, j++)
        {
            tempArr[j][0] = coordinates[i][0];
            tempArr[j][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        finiteDifference = getFiniteDifferences(tempArr);

        //Расчет производных по формуле
        for (int i = 6; i < 10; i++)
        {
            double t = (coordinates[i][0] - coordinates[6][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(1).get(0)
                    + ((6.0 * t - 6.0) * finiteDifference.get(2).get(0) / getFact(3))
                    + ((12.0*t*t - 36.0*t + 22.0) * finiteDifference.get(3).get(0) / getFact(4)
                    + ((20.0*t*t*t - 120.0*t*t + 210.0*t - 100.0) * finiteDifference.get(4).get(0)) / getFact(5)))
                    / (h*h);
        }

        //Нахождение производных для последних 6ти членов
        //выделяем координаты необходимых точек
        tempArr = new double[6][2];
        for (int i = 9, j = 0; i < 15; i++, j++)
        {
            tempArr[j][0] = coordinates[i][0];
            tempArr[j][1] = coordinates[i][1];
        }
        //Получаем список конечных приращений
        finiteDifference = getFiniteDifferences(tempArr);

        //Расчет производных по формуле
        for (int i = 9; i < 15; i++)
        {
            double t = (coordinates[i][0] - coordinates[9][0]) / h;

            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (finiteDifference.get(1).get(0)
                    + ((6.0 * t - 6.0) * finiteDifference.get(2).get(0) / getFact(3))
                    + ((12.0*t*t - 36.0*t + 22.0) * finiteDifference.get(3).get(0) / getFact(4)
                    + ((20.0*t*t*t - 120.0*t*t + 210.0*t - 100.0) * finiteDifference.get(4).get(0)) / getFact(5)))
                    / (h*h);
        }

        return resArr;
    }
}