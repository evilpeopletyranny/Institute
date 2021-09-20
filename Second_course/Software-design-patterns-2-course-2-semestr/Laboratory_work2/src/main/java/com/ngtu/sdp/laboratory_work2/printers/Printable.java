package com.ngtu.sdp.laboratory_work2.printers;

/**
 * Итерфейс, реализующий основные методы для
 * вывода информации об объекте
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
