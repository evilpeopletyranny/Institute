/**
 * Реализация односвязного списка (Данный класс взят из Лабораторной работы №1).
 *
 * @author  Vladislav Sapozhnikov
 * @param <E> тип, содержащийся в данной коллекции
 */
public class SinglyLinkedList<E>
{
    /**
     * Класс - узел/ячейка списка.
     * Стандартный класс для большинства динамических структур.
     *
     * @param <E> тип, содержащийся в данном узле
     * */
    private static class Node<E>
    {
        E value;                //поле, хранящее данные
        Node<E> next;           //указатель на след. узел

        Node(Node<E> next, E value)
        {
            this.value = value;
            this.next  = next;
        }
    }

    private Node<E> head;       //указатель на начало списка
    private int size;           //поле для хранения размерности списка
                                //Является необходимым для работы данной программы.

    /**
     * private метод для получения узла по заданному списку.
     *
     * @param index                      - индекс, по которому необходимо получить узел.
     *
     * @throws IndexOutOfBoundsException - исключение, сообщающее о выход за пределы списка.
     *                                     Данное исключение не обрабатывается внутри функции, а делегируется
     *                                     методу, из которого вызвана данная функция.
     *
     * @return Node<E>                   - возвращает узел списка.
     * */
    private Node<E> getByIndex(int index)
    {
        if (index < 0)
        {
            throw new IndexOutOfBoundsException("Index must be a positive number.");
        }

        Node<E> buffNode = head;
        for(int i = 0; i < index; i++)
        {
            if (buffNode.next == null)
            {
                throw new IndexOutOfBoundsException("Index [" + index + "] out of list.");
            }
            buffNode = buffNode.next;
        }
        return buffNode;
    }

    public SinglyLinkedList()
    {
        head = null;
        size = 0;
    }

    /**
     * Метод, добавляющий новый узел в конец списка.
     *
     * @param value - данные, которые следует добавить в новый узел списка.
     * */
    public void push_back(E value)
    {
        if (head == null)
        {
            head = new Node<>(null, value);
        }
        else
        {
            Node<E> buffNode = head;
            while(buffNode.next != null)
            {
                buffNode = buffNode.next;
            }
            buffNode.next = new Node<>(null, value);
        }

        size++;
    }

    /**
     * Перегруженный метод от класса Object.
     * Отвечет за вывод объекта в консоль.
     *
     * @return String - строка, содержающая строковое представление
     *                  объекта.
     * */
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");

        Node<E> buffNode = head;

        while(buffNode.next != null)
        {
            result.append(buffNode.value).append(", ");
            buffNode = buffNode.next;
        }
        result.append(buffNode.value).append("]");

        return new String(result);
    }

    /**
     * Метод, возвращающий значение из узла по переданному индексу.
     *
     * @param index - индекс узла, содержимое которого необходимо получить.
     * */
    public E get(int index)
    {
        Node <E> buffNode = getByIndex(index);
        return buffNode.value;
    }

    /**
     * Метод, возвращающий размерность данного списка.
     *
     * @return size - целочисленно значение равное размерности текущего списка.
     * */
    public int getSize()
    {
        return size;
    }
}