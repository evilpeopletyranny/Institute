package validator;

/**
 * Интерфейс реализующий метод проверки
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see ResponseValidator
 * */
public interface Validator
{
    /**
     * Метод для проверки праивльности значения
     *
     * @param prevValue    - предыдущее значение функции - f(Xn-1)
     * @param presentValue - текущее значение функции - f(Xn)
     * @return true - если найдено подхоядщее решение
     * */
    boolean isValid(double presentValue, double prevValue);

    /**
     * Метод задания параметра для сравнения.
     *
     * @param parameter - значение с которым будет происходить сравнивание
     * */
    void setParameter(double parameter);
}
