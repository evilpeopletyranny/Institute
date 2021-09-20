package validator;

import java.util.List;

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
     * @param prevValues    - вектор значений, полученных про прошлой итерации
     * @param presentValues - вектор значений, полученный при текущей итерации
     * @return true - если найдено подхоядщее решение
     * */
    boolean isValid(double[] presentValues, double[] prevValues);

    /**
     * Метод задания параметра для сравнения.
     *
     * @param parameter - значение с которым будет происходить сравнивание
     * */
    void setParameter(double parameter);
}
