package equation;

import java.util.*;

/**
 * Класс уравнений четвертой степени.
 * Реализует интерфейс Equation
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see Equation
 * */
public class FourthDegreePolynomial implements Equation
{
    //перемнная для хранения коэфициентов уравнения
    private final double[] coefficients = new double[4];

    /**
     * Метод для задания коэфициентов.
     *
     * @param coefficients - коэфифиценты, которые необходимо задать
     * */
    @Override
    public void setCoefficients(double[] coefficients)
    {
        System.arraycopy(coefficients, 0, this.coefficients, 0, coefficients.length);
    }


    /**
     * Метод для получение значения функции в заданной точке
     *
     * @param x - точка, в которой необходимо получить значение функции
     * @return значение функции в заданной точке
     * */
    @Override
    public double getValueAtX(double x)
    {
        return Math.pow(x, 4) + coefficients[0]*Math.pow(x, 3) + coefficients[1]*Math.pow(x, 2) +
                coefficients[2]*x + coefficients[3];
    }

    /**
     * Метод для получения интервалов смены знаков функции.
     *
     * @return список пар чисел - интервалов, в которых функция меняет знак.
     * */
    public List<List<Double>> getIntervals()
    {
        List<Double> xList = new LinkedList<>();
        List<Double> yList = new LinkedList<>();

        //Задаем интервал иксов от -100 до 100
        for (double i = -100.0; i <= 100; i++)
        {
            xList.add(i);
        }

        //Получаем значения функии в каждой точке интервала
        //от -100 до 100
        for (Double aDouble : xList)
        {
            yList.add(getValueAtX(aDouble));
        }

        //В коллекцию, которая может содержать только уникальные элементы
        //вносим значения при которых функция меняет знак
        List<Double> newList = new ArrayList<>();
        for (int i = 0; i < yList.size() - 1; i++)
        {
            if (yList.get(i) * yList.get(i+1) < 0)
            {
                newList.add(xList.get(i));
                newList.add(xList.get(i+1));
            }
        }

        //Сортировка по возрастанию
        Collections.sort(newList);

        //Записываем по парам полученные значения
        List<List<Double>> cordList = new ArrayList<>();
        for (int i = 0; i < newList.size(); i += 2)
        {
            List<Double> temp = new ArrayList<>();

            temp.add(newList.get(i));
            temp.add(newList.get(i + 1));

            cordList.add(temp);
        }

        return cordList;
    }

    /**
     * Конуструктор без параметров
     * */
    public FourthDegreePolynomial()
    {
    }

    /**
     * Метод для вывода уравнения в консоль.
     * */
    @Override
    public void printEquation()
    {
        System.out.printf("λ^4 %.4fλ^3 + %.4fλ^2 + %.4fλ %.4f = 0", coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
    }
}
