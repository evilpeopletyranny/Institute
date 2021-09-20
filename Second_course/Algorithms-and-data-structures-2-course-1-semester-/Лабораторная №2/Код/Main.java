import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;                   //класс отвечающий за чтение с потока ввода

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация второй лабораторной работы по диспилине: Алгоритмы и структуры данных. Вариант№17.
 *
 * Текст задания: Дана разреженная ленточная матрица. Найти сумму её элементов.
 *
 * @release: 27.06.20
 * @last_update: 27.06.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scanner = new Scanner(System.in);
            BandSpareMatrix spareMatrix = new BandSpareMatrix();    //создание объекта класса, предназначенного
                                                                    //для хранения разреженной ленточной матрицы
            SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

            Integer[][] initMatrix = null;
            String str = "";

            System.out.println(" 1 - автозаполнение");
            System.out.println(" 2 - ручное заполнение");
            str = scanner.nextLine();

            if (str.equals("1"))
            {
                initMatrix  = readMatrixFromFile(args[0]);
            }
            else if (str.equals("2"))
            {
                System.out.println("Введите размер квадратной матрицы.");
                int size = scanner.nextInt();

                if (size <= 0)
                {
                    throw new InputMismatchException("Введено неверное значение: " + size);
                }

                Integer[][] buff = new Integer[size][size];

                for (int i = 0; i < size; i++)
                {
                    System.out.println("Введите строку матрицы: ");
                    for (int j = 0; j < size; j++)
                    {
                        buff[i][j] = scanner.nextInt();
                    }
                }

                for (Integer[] i: buff)
                {
                    for (Integer j: i)
                    {
                        System.out.print(j + " ");;
                    }
                    System.out.println();
                }



                initMatrix = buff;
            }
            else
            {
                System.err.println("Выбран неверный пункт. Перезапустите программу. ");
                System.exit(-1);
            }




            System.out.println("\nИсходная матрица: ");
            for (Integer[] i: initMatrix)
            {
                for (Integer j: i)
                {
                    System.out.print(j + " ");
                }
                System.out.println();
            }

            System.out.println("\nОбработанная матрица (ленточно строчный формат): ");
            spareMatrix.fillMatrix(initMatrix);                     //перезапись прочитанной матрицы
                                                                    //в ленточно строчный формат

            spareMatrix.printMatrix();                              //вывод матрицы в ленточно строчном формате

            System.out.println();                                   //вывол суммы элементов матрицы
            System.out.println("Сумма элементов матрицы: " + spareMatrix.getSumMatrixElement());
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Нет такого файла: " + args[0]);
            System.err.println("Укажите путь к существующему файлу и повторите попытку.");
        }
        catch (NumberFormatException e)
        {
            System.err.println("Неверное содержимое файла.");
            System.err.println("Измените переданный файл или его содержимое и повторите попытку.");
        } catch (InputMismatchException e) {
            System.err.println("Введены неверные данные");
        }
    }

    /**
     * Метод, считавыющий матрицу с файла.
     *
     * @param path                   - путь к файлу из которого следует читатьт матрицу.
     *
     * @throws FileNotFoundException - исключенние, уведомляющие о невозможности открытия файла.
     *                                 Данное исключение не обрабывается внутри метода а делегируется
     *                                 методу из которого был вызван данный метод.
     *
     * @return initMatrix            - метод возвращает двумерный массив ссылочного типа Integer.
     * */
    public static Integer[][] readMatrixFromFile (String path) throws FileNotFoundException
    {
        SinglyLinkedList<String[]> strList = readFile(path);
        int size = strList.getSize();

        Integer[][] initMatrix = new Integer[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                initMatrix[i][j] = Integer.parseInt(strList.get(i)[j]);
            }
        }

        return initMatrix;
    }

    /**
     * Метод считавающий файл посторочно. Дополнение метода readMatrixFromFile.
     *
     * @param path                   - путь к файлу из которого следует читатьт матрицу.
     *
     * @throws FileNotFoundException - исключенние, уведомляющие о невозможности открытия файла.
     *                                      Данное исключение не обрабывается внутри метода а делегируется
     *                                      методу из которого был вызван данный метод.
     *
     * @return strList               - возвращает список массивов строк типа String.
     * */
    private static SinglyLinkedList<String[]> readFile(String path) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(path));
        SinglyLinkedList<String[]> strList = new SinglyLinkedList<>();

        while (scanner.hasNextLine())
        {
            strList.push_back(scanner.nextLine().split("\\s"));     //для разделения по пробелам
        }                                                                 //используется регулярное выражение
                                                                          //"//s"
        scanner.close();
        return strList;
    }

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
}