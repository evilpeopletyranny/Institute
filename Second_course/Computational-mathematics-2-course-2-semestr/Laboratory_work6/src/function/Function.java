package function;

/**
 * Интерфейс, содержащий основные методы функций.
 * */
public interface Function
{
    /**
     * Получение значения функции при заданных координатах.
     *
     * @param x - координата по х
     * @param y - координата по у
     * @return значение функции
     * */
    double getValue(double x, double y);
}
