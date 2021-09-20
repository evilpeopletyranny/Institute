import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация пятой лабораторной работы по диспилине: Алгоритмы и структуры данных. Вариант№17.
 *
 * Текст задания: Реализовать внешнюю сортировку.
 *
 * @release: 8.12.20
 * @last_update: 8.12.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main
{
    /**
     * Точка входа в программу - функция main.
     *
     * @param args - список параметров запуска.
     * */
    public static void main(String[] args)
    {
        try
        {
            Sort.externalFileSorting(args[0]);    //сортировка переданного файла.
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Файл: '" + args[0] + "' не найден.");
        }
        catch (InputMismatchException e)
        {
            System.err.println("Неверное содержимое файла!");
            System.err.println("Ожидается: целочисленные значения.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
