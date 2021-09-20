package equation;

import java.util.Arrays;

/**
 * Класс системы из 3ёх нелинйеных уравнений
 * Реализует интерфейс SystemOfEquations
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SystemOfEquations
 * */
public class SystemOfThreeEquations implements SystemOfEquations
{
    public double[][] coefficients = new double[3][3];    //массив коэфициентов перед иксами
    public double[]   vectorB      = new double[3];       //массив значений вектора b

    /**
     * Метод для задания коэфициентов при x
     *
     * @param coefficients - массив коэфициентов при членах уравнения.
     * */
    @Override
    public void setCoefficients(double[][] coefficients)
    {
        for (int i = 0; i < coefficients.length; i++)
        {
            System.arraycopy(coefficients[i], 0, this.coefficients[i], 0, coefficients[0].length);
        }
    }

    /**
     * Метод для получения коэфициентов при x
     *
     * @return  массив коэфициентов при членах уравнения.
     * */
    @Override
    public double[][] getCoefficients()
    {
        return coefficients;
    }

    /**
     * Метод для задания вектора b
     *
     * @param vectorB - массив значений вектора b.
     * */
    @Override
    public void setVectorB(double[] vectorB)
    {
        System.arraycopy(vectorB, 0, this.vectorB, 0, vectorB.length);

    }

    @Override
    public double[] getVectorB() {
        return vectorB;
    }

    /**
     * Конуструктор без параметров
     * */
    public SystemOfThreeEquations()
    {
    }
}
