package equation_system_solution;

import equation_system.SystemOfEquations;

/**
 * Класс, реализующий решение методом Гаусса.
 * ВНИМАНИЕ!!! Данный метод подходит только для
 * системы уравнений данного варианта!.
 *
 * @see SolutionStrategy
 * */
public class GaussSolution implements SolutionStrategy
{
    /**
     * Метод для получения решения системы уравнений
     * методом Гаусса
     *
     * @param system - система уравнений
     * @return массив решений данной системы.
     * */
    @Override
    public double[] getSolution(SystemOfEquations system)
    {
        double[][] tempMatrix  = new double[4][4];
        double[]   tempVectorB = new double[4];
        double[] result        = new double[4];

        for (int i = 0; i < 4; i++)
        {
            System.arraycopy(system.getCoefficients()[i], 0, tempMatrix[i], 0, system.getCoefficients()[i].length);
        }
        System.arraycopy(system.getVectorB(), 0, tempVectorB, 0, system.getVectorB().length);

        //Умножим первую строку на (-1.144565):
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] *= -1.145;
        }
        tempVectorB[0] *= -1.144565;

        //Добавим 2-ю строку к 1ой:
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] += tempMatrix[1][i];
        }
        tempVectorB[0] += tempVectorB[1];
        //   0   1.178  0.627    1   | 1.933
        //   0   -0.63 -0.939 -0.719 | -6.103
        // 21.35  4.5   0.5      0   | -100.658
        // 25.238 5.77  1.2      0   | -112.596


        //Умножим 2-ю строку на -0,719485
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[1][i] *= -0.719485;
        }
        tempVectorB[1] *= -0.719485;

        //Добавим 3-ю строку ко второй
        for (int i =0; i < tempMatrix.length; i++)
        {
            tempMatrix[1][i] += tempMatrix[2][i];
        }
        tempVectorB[1] += tempVectorB[2];
        //  0     1.78  0.627     1   |1.933
        //  0    -0.63  -0.939 -0.719 |-6.103
        // 21.35  4.5    0.5      0   | -100.658
        // 25.238 5.77   1.2      0   | -112.596


        //Умножим 3-ю строку на -1,182108
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[2][i] *= -1.182108;
        }
        tempVectorB[2] *= -1.182108;

        //Добавим 4-ю строку к 3-й
        for (int i =0; i < tempMatrix.length; i++)
        {
            tempMatrix[2][i] += tempMatrix[3][i];
        }
        tempVectorB[2] += tempVectorB[3];
        //  0     1.78   0.627     1  |1.933
        //  0    -0.63  -0.939 -0.719 |-6.103
        //  0     0,451  0,609    0   | 6,393
        // 25.238 5.77   1.2      0   | -112.596


        //Умножим 1-ю строку на 0,534626
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] *= 0.534626;
        }
        tempVectorB[0] *= 0.534626;

        //Добавим 2-ю строку к 1-й
        for (int i =0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] += tempMatrix[1][i];
        }
        tempVectorB[0] += tempVectorB[1];
        //  0     1.78   0.627     1  |1.933
        //  0    -0.63  -0.939 -0.719 |-6.103
        //  0     0,451  0,609    0   | 6,393
        // 25.238 5.77   1.2      0   | -112.596


        //Умножим 2-ю строку на 0,715185
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[1][i] *= 0.715185;
        }
        tempVectorB[1] *= 0.715185;

        //Добавим 3-ю строку к 2-й
        for (int i =0; i < tempMatrix.length; i++)
        {
            tempMatrix[1][i] += tempMatrix[2][i];
        }
        tempVectorB[1] += tempVectorB[2];
        //  0       0  -0.604  -0,185 |-5,07
        //  0       0  -0.0626 -0,515 |2,028
        //  0     0,451  0,609    0   | 6,393
        // 25.238 5.77   1.2      0   | -112.596


        //Умножим 1-ю строку на -0,103625
        for (int i = 0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] *= -0.103625;
        }
        tempVectorB[0] *= -0.103625;

        //Добавим 2-ю строку к 1-й
        for (int i =0; i < tempMatrix.length; i++)
        {
            tempMatrix[0][i] += tempMatrix[1][i];
        }
        tempVectorB[0] += tempVectorB[1];
        //  0       0     0    0.495  |2.553
        //  0       0  -0.0626 -0,515 |2,028
        //  0     0,451  0,609    0   | 6,393
        // 25.238 5.77   1.2      0   | -112.596

        //x4 = 2.553/(-0.495)
        result[3] = tempVectorB[0]/tempMatrix[0][3];

        //x3 = [2.028 - (-0.515x4)]/0.451
        result[2] = (tempVectorB[1] - (tempMatrix[1][3] * result[3])) / tempMatrix[1][2];

        //x2 = [6.393 - (0.609x3)]/0.451
        result[1] = (tempVectorB[2] - (tempMatrix[2][2] * result[2])) / tempMatrix[2][1];

        //x1 = [-112.596-(5.77*x2 + 1.2x3)]/25.238
        result[0] = (tempVectorB[3] - (tempMatrix[3][1]*result[1] + tempMatrix[3][2]*result[2]))/tempMatrix[3][0];

        return result;
    }
}
