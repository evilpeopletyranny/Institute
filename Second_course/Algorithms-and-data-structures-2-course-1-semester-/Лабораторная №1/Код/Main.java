import java.io.BufferedReader;              //класс оболочка для буферизированного ввода
import java.io.IOException;                 //класс исключение ввода/вывода
import java.io.InputStreamReader;           //класс оболочка для ввода

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация первой лабораторной работы по диспилине: Алгоритмы и структуры данных. Вариант№17.
 *
 * Текст задания: Сделать определенный элемент списка первым.
 *
 * @release: 17.06.20
 * @last_update: 20.06.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main {
    public static void main(String[] args)
    {
        System.out.println("\n\u001B[31m\t\t\t\t\t        Начало работы..." + "\n\u001B[0m");
        try
        {
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();                              //создание реализованного списка

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 16);   //открытие потока на чтение
                                                                                                   //из стандартного потока ввода

            System.out.println("Для прерывания ввода введите - 'q'");
            System.out.println("Заполнение списка: ");
            fillIngerListManually(list, reader);                                                   //заполнение спика пользователем

            System.out.println("\nИсходный список: ");
            System.out.println(list);

            System.out.print("\nУкажите индекс элемента для перестановки: ");
            String str = inputWithCheck(reader);
            System.out.println("\nИзмененный список: ");
            makeListElFirst(list, str.equals("q") ? 0 : Integer.parseInt(str));                 //ввод пользователем индекса
            System.out.println(list);                                                           //элемента для замены

        }
        catch (IOException e)
        {
            System.err.println("Произошла ошибка ввода. Попробуйте еще раз.");
        }
        finally {
            System.out.println("\n\u001B[31m\t\t\t\t\t       Завершение работы..." + "\u001B[0m");
        }

    }

    /**
     * Функция, проверяющая вводимую строку на содержание
     * только цифр.
     *
     * @param reader       - буфферизированный поток ввода.
     *
     * @throws IOException - при неудачной попытке ввода бросается исключение потока ввод/вывода.
     *                       Данное исключение не обрабатывается внутри функции, а делегируется
     *                       методу, из которого вызвана данная функция.
     *
     * @return str         - строка с введенными значениями.
     * */
    public static String inputWithCheck(BufferedReader reader) throws IOException
    {
        String str;

        while (true)
        {
            str = reader.readLine();

            if (str.equals("q"))                                                //выход при введение 'q'
            {
                break;
            }
            else if (!str.matches("-?\\d+(\\.\\d+)?"))                   //проверка на содержание только чисел
            {                                                                  //при помощи регулярного выражения
                System.err.println("Некорректный ответ. Повторите ввод.");
                continue;
            }
            break;
        }
        return str;
    }


    /**
     * Функция, заполняющая переданный лист значениями типа Integer.
     *
     * @param list<Integer> - лист типа Integer для заполнения.
     *
     * @throws IOException  - при неудачной попытке ввода бросается исключение потока ввод/вывода.
     *                        Данное исключение не обрабатывается внутри функции, а делегируется
     *                        методу, из которого вызвана данная функция.
     * */
    public static void fillIngerListManually(SinglyLinkedList<Integer> list, BufferedReader reader) throws IOException
    {
        String str;

        while (true)
        {
            System.out.print("Введите след. элемент: ");
            str = inputWithCheck(reader);

            if (str.equals("q")) break;

            list.push_back(Integer.parseInt(str));
        }
    }

    /**
     * Функция, делающая элемент списка по переданному индексу первым в списке.
     * Данная функция реализована только при помощи методов класса SinglyLinkedList
     *
     * @param index   - индекс, по которому будет происходить замена.
     * @param list<E> - лист обобщенного типа, в котором будут происходить изменения.
     * */
    public static <E> void makeListElFirst(SinglyLinkedList<E> list, int index)
    {
        if (index == 0)
        {
            return;
        }
        E value = list.get(index);
        list.removeAfter(index-1);
        list.push_front(value);
    }
}