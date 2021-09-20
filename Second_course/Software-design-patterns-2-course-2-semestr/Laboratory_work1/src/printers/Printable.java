package printers;

/**
 * Итерфейс, реализующий основные методы для
 * вывода информации об объекте
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 */
public interface Printable<T> {
    /**
     * Метод выводящий информацию о переданном объекте
     * в консоль
     *
     * @param object - объект о котором необходимо вывести информацию
     */
    void print(T object);
}
