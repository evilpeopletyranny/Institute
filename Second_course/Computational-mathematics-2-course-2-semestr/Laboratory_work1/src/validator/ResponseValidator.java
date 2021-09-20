package validator;

/**
 * Singleton класс реализующий интерфейс проверки Validator.
 * Проверяет решение на соответсвие критериям остановки.
 *
 * WARNING!!!
 *    Критерий остановки: найдено точное значение f(Xn) = 0 не используется
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see Validator
 * */
public class ResponseValidator implements Validator
{

    private static ResponseValidator instance;   //поля для хранения ссылки на единственный
                                                 //экземпляр класса

    private double epsilon;                      //поля для хранения значения с которым
                                                 //будет проводится сравнение

    /**
     * Проверка решение на соответсвие критериям остановки:
     *  - Достигунта заданная точность |f(Xn)| < epsilon
     *  - Значение двух последних приближений отличается меньше, чем на epsilon
     *    |X(n-1) - X(n)| < epsilon
     *
     * @param prevValue    - предыдущее значение функции - f(Xn-1)
     * @param presentValue - текущее значение функции - f(Xn)
     * @return true - если найдено подхоядщее решение
     * */
    @Override
    public boolean isValid(double prevValue, double presentValue)
    {
//        if (Math.abs(prevValue - presentValue) < epsilon) return true;
        return Math.abs(presentValue) < epsilon;
    }

    /**
     * Метод для получения едиснтвенного экземпляара класса.
     * Нам достаточно лишь одного экземпляара класса ResponseValidator
     * для вызова методов проверки
     *
     * @return ссылку на один единственный экземпляр класса
     * */
    public static ResponseValidator getInstance()
    {
        if (instance == null)
        {
            instance = new ResponseValidator();
        }

        return instance;
    }

    /**
     * Метод для установки параметра сравнений.
     *
     * @param epsilon - значения для сравнений
     * */
    @Override
    public void setParameter(double epsilon)
    {
        this.epsilon = epsilon;
    }

    /**
     * Приватный конструктор запрящает создание объекта из вне.
     * */
    private ResponseValidator()
    {
    }
}
