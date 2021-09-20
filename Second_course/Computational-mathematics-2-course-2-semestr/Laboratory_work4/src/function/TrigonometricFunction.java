package function;

/**
 * Класс, реализующий необходимые методы для функции Варианта №15
 *         cos(x^2)/(x+1)
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see Function
 * */
public class TrigonometricFunction implements Function
{
    /**
     * Метод для получения значения функции в заданной точке.
     *
     * @param x - значение Х, в котором необходимо получить
     *            значение функции.
     *
     * @return - значение функции при заданном Х
     * */
    @Override
    public double getValueAtX(double x)
    {
        return Math.cos(x*x) / (x + 1);
    }

    /**
     * Метод для получения значения второй производной в заданной точке.
     * Функция второй производной в "читаемом" виде представлена в отчете.
     *
     * @param x - значение Х, в котором необходимо получить
     *            значение функции.
     *
     * @return - значение второй производной при заданном Х
     * */
    @Override
    public double getSecDerivativeAtX(double x)
    {
        return (2.0 / (x + 1)) * ((-2.0 * x*x * Math.cos(x*x)) + (2 * x * ((Math.sin(x*x))/(x + 1))) - (Math.sin(x*x)) + ((Math.cos(x*x))/((x +1)*(x + 1))));
    }

    /**
     * Метод для получения значения третьей производной в заданной точке.
     * Функция третьей производной в "читаемом" виде представлена в отчете.
     *
     * @param x - значение Х, в котором необходимо получить
     *            значение функции.
     *
     * @return - значение второй производной при заданном Х
     * */
    @Override
    public double getThirdDerivativeAtX(double x)
    {
        return 2.0 * (-3.0 * Math.cos(x*x) / Math.pow((1.0 + x), 3.0) + 2 * x *(-3.0 * Math.cos(x*x) + 2 * x*x *
                Math.sin(x*x)) + 3.0 * (2.0 * x*x * Math.cos(x*x) + Math.sin(x*x)) / (1.0 + x) -
                6.0 * x * Math.sin(x*x) / (1.0 + x)*(1.0 + x)) / (1.0 + x);
    }

    /**
     * Конуструктор без параметров
     * */
    public TrigonometricFunction()
    {
    }
}
