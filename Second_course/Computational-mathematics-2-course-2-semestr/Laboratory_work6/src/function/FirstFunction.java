package function;

/**
 * Класс - первая функция Варианта №15
 *
 * @see Function
 * */
public class FirstFunction implements Function
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
        return x + Math.sin(y / Math.PI);
    }
}
