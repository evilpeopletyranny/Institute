import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация третьей лабораторной работы по диспилине: Алгоритмы и структуры данных. Вариант№17.
 *
 * Текст задания:  Дано N-дерево. Найти в дереве самый длинный путь без ветвлений.
 *
 * @release: 14.10.20
 * @last_update: 14.10.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main {
    public static void main(String[] args)
    {
        System.out.println("\t\t\u001B[33mНачало работы...\u001B[0m");

        Scanner scanner = new Scanner(System.in);                               //открытие потока ввода

        int[][] initMatrix;                                                     //создание ссылки на двуремный
                                                                                //целочисленный массив

        SinglyLinkedList<String> vertexNameList = new SinglyLinkedList<>();     //создание ссылки на список,
                                                                                //содержащий строки

        System.out.println("\t   \u001B[33mСоздание n-дерева\u001B[0m");
        System.out.println("Для выхода введите '--q'");
        System.out.print("Введите количество вершин в дереве: ");
        readAndSave.fillVertexList(vertexNameList, scanner);                    //заполнение списка вершин
        System.out.println();

        System.out.print("\t   \u001B[33mЗаполнение матрицы смежности\u001B[0m");
        initMatrix = readAndSave.fillAdjacencyMatrix(vertexNameList, scanner);  //заполнение матрицы смежности

        System.out.println();
        System.out.println("\t\u001B[33mЗаполненая матрица смежности \u001B[0m");
        readAndSave.printAdjacencyMatrix(vertexNameList, initMatrix);           //вывод заполненолц матрицы смежности
        System.out.println();

        Tree<String> tree = new Tree<>(vertexNameList, initMatrix);             //создание объекта - дерево
        System.out.println("\t\u001B[33mПредставление дерева списками смежности\u001B[0m");
        tree.printAdjacencyList();                                              //вывод дерева в виде списков
                                                                                //смежности

        System.out.println("\t\u001B[33m\n\tПоиск длиннейшего пути без ветвлений\u001B[0m");
        tree.searchLongWay();                                                   //поиск кратчайшего пути без ветвлений
    }
}
