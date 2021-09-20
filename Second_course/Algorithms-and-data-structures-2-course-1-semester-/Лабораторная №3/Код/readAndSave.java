import java.util.Scanner;

/**
 *  Интерфейс, содержащий вспомогательные функции ввода.
 * */
public interface readAndSave
{
    /**
     * Вспомогательный метод, проверяющий желает ли пользователь
     * досрочно завершить ввыполнение программы.
     *
     * @param str - строка введеная пользователем,
     *              которая передается для проверки.
     * */
    private static void checkForExit(String str)
    {
        if (str.equals("--q"))                                        //если пользователь ввел                                          
        {                                                             //'--q' - досрочный выход
            System.err.println("Досрочный выход из программы...");    //из программы с соответсвующим    
            System.exit(-1);                                    //сообщением
        }
    }

    /**
     * Вспомогательный метод, проверяющий корректность ввода кол-во вершин
     * в дереве.
     *
     * @param scanner - класс - оболочка потока ввода.
     *
     * @return Integer.parseInt(str) - введенное пользователь значение
     *                                 переденное в целочисленный формат.
     * */
    private static Integer inputVertexQuantity(Scanner scanner)
    {
        String str;
        int value;

        while (true)                            //цикл ввода и проверки значений
        {
            str = scanner.nextLine();

            checkForExit(str);

            if (str.matches("[0-9]+"))    //проверка содержимого введенной строки на содержание
            {                                   //только чисел при помощи регулярного выражения

                value = Integer.parseInt(str);

                if (value > 25)
                {
                    System.out.println("Вряд ли вам нужно столь большее дерево.");
                }
                else if (value == 0)
                {
                    System.err.println("Кол-во вершин не может быть равно нулю.");
                }
                else
                {
                    return value;
                }
            }
            else
            {
                System.err.println("Ошибка ввода. Повторите попытку.");
            }
        }
    }

    /**
     * Вспомогательный метод, проверяющий корректность ввода имени вершин
     * дерева.
     *
     * @param scanner - класс - оболочка потока ввода.
     *
     * @return str    - строка - имя вершины вершины.
     * */
    private static String inputVertexName(Scanner scanner)
    {
        String str;
        while (true) {                                   //цикл ввода и проверки значений
            str = scanner.nextLine();

            checkForExit(str);

            if (str.matches("\\w+|[0-9, \\w]"))    //проверка на содержание строки
            {                                            //только чисел и букв латинского алфавита
                return str;                              //при помощи регулярного выражения
            }
            else
            {
                System.err.println("Ошибка ввода. Повторите попытку.");
            }
        }
    }

    /**
     * Вспомогательный метод, проверяющий корректность ввода пути между вершинами
     *
     * @param scanner - класс - оболочка потока ввода.
     *
     * @param count                  - счетчик, указывающий сдвиг по строке
     *                                 матрицы смежности
     *
     * @return Integer.parseInt(str) - введенное пользователь значение
     *                                 переденное в целочисленный формат.
     * */
    private static Integer hasWay(Scanner scanner, int[] count) {
        String str;

        while (true)                                   //цикл ввода и проверки значений
        {
            str = scanner.nextLine();
            checkForExit(str);

            if (str.matches("[0-1]"))           //проверка на ввод только 0 и 1
            {
                if (str.equals("1"))
                {
                    count[0]++;
                }
                return Integer.parseInt(str);
            }
            else
            {
                System.err.println("Ошибка ввода. Повторите попытку.");
            }
        }
    }

    /**
     * Метод, выводящий матрицу смежности.
     *
     * @param list   - списко с именем вершин дерева.
     *
     * @param matrix - матрица смежности дерева.
     * */
    static void printAdjacencyMatrix(SinglyLinkedList<String> list, int[][] matrix)
    {
        int size = list.getSize();

        System.out.print("\t");
        for (int i = 0 ; i < size; i++) System.out.print(list.get(i) + "\t");
        System.out.println();
        for (int i = 0; i < size; i++)
        {
            System.out.print(list.get(i) + "\t");
            for (int j = 0; j < size; j++)
            {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


    /**
     * Метод для заполнения списка имен вершин дерева.
     *
     * @param scanner - класс - оболочка потока ввода.
     *
     * @param list    - список для заполнения.
     *  */
    static void fillVertexList(SinglyLinkedList<String> list, Scanner scanner)
    {
        int size = inputVertexQuantity(scanner);

        System.out.println();
        System.out.println("Имена вершин могут содержать только буквы латинского алфавита и положительные целые числа.");
        System.out.println("Заполняйте имена вершин аккуратно, в противном случае необходим перезапуск программы.");
        System.out.println();

        for (int i = 1; i <= size; i++)
        {
            System.out.print("Введите имя вершины " + i + ": ");
            list.push_back(inputVertexName(scanner));
        }
    }

    /**
     * Метод для заполнения матрицы смежности.
     *
     * @param scanner - класс - оболочка потока ввода.
     *
     * @param list    - список с именами вершин.
     *
     * @return matrix - заполненая матрицы смежности.
     * */
    static int[][] fillAdjacencyMatrix(SinglyLinkedList<String> list, Scanner scanner)
    {
        int size = list.getSize();
        int[][] matrix = new int[size][size];

        System.out.println();
        System.out.println("'1' - есть путь между вершинами");
        System.out.println("'0' - пути между вершинами нет");

        int[] count = {1};
        for (int i = 0; (i < size-1) && (count[0] != size); i++)
        {
            System.out.println();
            System.out.println("Заполнение пути для вершины " + list.get(i));
           for (int j = count[0]; j < size; j++)
           {
               System.out.print("Путь " + list.get(i) + " - " + list.get(j) + ": ");
               matrix[i][j] = hasWay(scanner, count);
           }
        }
        return matrix;
    }
}