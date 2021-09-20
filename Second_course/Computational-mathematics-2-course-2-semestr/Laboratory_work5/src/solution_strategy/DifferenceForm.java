package solution_strategy;

/**
 * Проверочные методы нахождения первой и второй производной
 * при помощи разностных формул.
 *
 * @see SolutionStrategy
 * */
public class DifferenceForm implements SolutionStrategy
{
    /**
     * Нахождение первых производных при помощи разностных
     * формул.
     *
     * @param coordinates - массив координат функции
     * @return массив значений первых производных
     * */
    @Override
    public double[][] getFirstDerivative(double[][] coordinates)
    {
        //вычисление шага h
        double h = coordinates[1][0] - coordinates[0][0];
        double[][] resArr = new double[15][2];

        //вычисление значения первой производной
        //при помощи правой разностной формулы
        // (f(x+h) - f(x))/h
        resArr[0][0] = coordinates[0][0];
        resArr[0][1] = ((coordinates[1][1] - coordinates[0][1]) / h);

        //вычисление значения первой производной
        //при помощи центральной разностной формулы
        // f(x+h) - f(x-h)/2h
        for (int i = 1; i < coordinates.length - 1; i ++)
        {
            resArr[i][0] = coordinates[i][0];
            resArr[i][1] = (coordinates[i + 1][1] - coordinates[i - 1][1]) / (2.0*h);
        }

        //вычисление значения первой производной
        //при помощи левой разностной формулы
        // (f(x)-f(x-h))/h
        resArr[coordinates.length - 1][0] = coordinates[coordinates.length - 1][0];
        resArr[coordinates.length - 1][1] = ((coordinates[coordinates.length -1][1] - coordinates[coordinates.length - 2][1]) / h);

        return resArr;
    }

    /**
     * Нахождение вторых производных при помощи разностных
     * формул.
     * Поскольку для нахождения производных данным способом
     * задействуются i-1 и i+1 значения функции, то находятся
     * значения только в 13 точках. Х ∈ [3.55; 4.15]
     *
     * @param coordinates - массив координат функции
     * @return массив значений вторых производных
     * */
    @Override
    public double[][] getSecondDerivative(double[][] coordinates)
    {
        double h = coordinates[1][0] - coordinates[0][0];
        double[][] resArr = new double[13][2];

        //нахождение вторых проиовздных при помощи приближенной формулы
        // (f(x+h) - 2*f(x) + f(x-h)) / h^2
        for (int i = 1; i < coordinates.length - 1; i++)
        {
            resArr[i - 1][0] = coordinates[i][0];
            resArr[i - 1][1] = (coordinates[i + 1][1] - 2.0 * coordinates[i][1] + coordinates[i - 1][1]) / (h * h);
        }
        return resArr;
    }
}
