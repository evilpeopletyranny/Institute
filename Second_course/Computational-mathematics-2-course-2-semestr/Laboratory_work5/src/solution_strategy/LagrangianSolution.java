package solution_strategy;

/**
 * Класс, в котором реализованы методы нахождения
 * первых и вторых произовдных при помощи многочлена Лагранжа
 *
 * @see SolutionStrategy
 * */
public class LagrangianSolution implements SolutionStrategy
{
    /**
     * Метод для получения первых проиводных при помощи
     * многочлена Лагранжа
     *
     * @param coordinates - массив координат точек, в которых
     *                      необходимо найти проиводные
     * @return массив значений первых производных
     * */
    @Override
    public double[][] getFirstDerivative(double[][] coordinates)
    {
        //Вычисление шага h
        double h       = coordinates[1][0] - coordinates[0][0];
        double[][] res = new double[15][2];

        //Вычисление yi yi+1 yi+2 yi+3 yi+4 по формулам лагранжа при n = 4
        for (int i = 0; i < 15; i += 5)
        {
            res[i][0]     = coordinates[i][0];
            res[i + 1][0] = coordinates[i + 1][0];
            res[i + 2][0] = coordinates[i + 2][0];
            res[i + 3][0] = coordinates[i + 3][0];
            res[i + 4][0] = coordinates[i + 4][0];

            res[i][1]     = ((-(25.0) * coordinates[i][1] + 48.0 * coordinates[i + 1][1] - 36.0 * coordinates[i + 2][1] + 16.0 * coordinates[i + 3][1] - 3.0 * coordinates[i + 4][1]) / (12.0 * h));
            res[i + 1][1] = ((-(3.0) * coordinates[i][1] - 10.0 * coordinates[i + 1][1] + 18.0 * coordinates[i + 2][1] -  6.0 * coordinates[i + 3][1] + coordinates[i + 4][1]) / (12.0 * h));
            res[i + 2][1] = ((coordinates[i][1] - 6.0 * coordinates[i + 1][1] + 3.0 * coordinates[i + 2][1] + 2.0 * coordinates[i + 3][1]) / (6.0 * h));
            res[i + 3][1] = ((-(coordinates[i][1]) + 6.0 * coordinates[i + 1][1] - 18.0 * coordinates[i + 2][1] + 10.0 * coordinates[i + 3][1] + 3.0 * coordinates[i + 4][1]) / (12.0 * h));
            res[i + 4][1] = ((3.0 * coordinates[i][1] - 16.0 * coordinates[i + 1][1] + 36.0 * coordinates[i + 2][1] - 48.0 * coordinates[i + 3][1] + 25.0 * coordinates[i + 4][1]) / (12.0 * h));
        }
        return res;
    }

    /**
     * Метод для получения вторых проиводных при помощи
     * многочлена Лагранжа
     *
     * @param coordinates - массив координат точек, в которых
     *                      необходимо найти проиводные
     * @return массив значений первых производных
     * */
    @Override
    public double[][] getSecondDerivative(double[][] coordinates)
    {
        //Вычисление шага h
        double h       = coordinates[1][0] - coordinates[0][0];
        double[][] res = new double[15][2];

        //Нахождение вторых произовдных yi yi+1 yi+2 yi+3
        //при помощи формул производых высшего порядка
        for (int i = 0; i < 12; i += 4)
        {
            res[i][0]     = coordinates[i][0];
            res[i + 1][0] = coordinates[i + 1][0];
            res[i + 2][0] = coordinates[i + 2][0];
            res[i + 3][0] = coordinates[i + 3][0];

            res[i][1]     = ((2.0 * coordinates[i][1] - 5.0 * coordinates[i + 1][1] + 4.0 * coordinates[i + 2][1] - coordinates[i + 3][1]) / (h*h));
            res[i + 1][1] = ((coordinates[i][1] - 2.0 * coordinates[i + 1][1] + coordinates[i + 2][1]) / (h*h));
            res[i + 2][1] = ((coordinates[i + 1][1] - 2.0 * coordinates[i + 2][1] + coordinates[i + 3][1]) / (h*h));
            res[i + 3][1] = ((-(coordinates[i][1]) + 4.0 * coordinates[i + 1][1] - 5.0 * coordinates[i + 2][1] + 2.0 * coordinates[i + 3][1]) / (h*h));
        }

        //Нахождение вторых произовдных yi+1 yi+2 yi+3
        //при помощи формул производых высшего порядка
        res[12][0] = coordinates[12][0];
        res[13][0] = coordinates[13][0];
        res[14][0] = coordinates[14][0];

        res[12][1] = ((coordinates[11][1] - 2.0 * coordinates[12][1] + coordinates[13][1]) / (h*h));
        res[13][1] = ((coordinates[12][1] - 2.0 * coordinates[13][1] + coordinates[14][1]) / (h*h));
        res[14][1] = ((-(coordinates[11][1]) + 4.0 * coordinates[12][1] - 5.0 * coordinates[13][1] + 2.0 * coordinates[14][1]) / (h*h));

        return res;
    }
}
