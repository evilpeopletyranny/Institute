package function;

/**
 * Класс - вторая функция Варианта №15
 *
 * @see Function
 * */
public class SecondFunction implements Function
{
    /**
     * Получение значения функции при заданных координатах.
     *
     * @param x - координата по х
     * @param y - координата по у
     * @return значение функции
     * */
    @Override
    public double getValue(double x, double y)
    {
        return Math.cos(1.5 * x + y) + 1.5 * (x - y);
    }
}
