package builder;

import exceptions.ChildNodeException;
import nodes.ClassNode;
import nodes.IndividualNode;
import nodes.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Класс, реализующий автомат для создания графовой структуры
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see nodes.Node
 * @see nodes.ClassNode
 * @see nodes.IndividualNode
 * @see nodes.AttributeNode
 * @see nodes.ValueNode
 */
public class GraphBuilder {
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";

    /**
     * Поскольку для создания графа нет необходимости
     * создавать объект данного класса, то запрящаем
     * создание объекта
     */
    private GraphBuilder() {
    }

    /**
     * Метод выводящий разделитель при создании узлов
     * Скрыт, т.к. не использутеся напрямую
     */
    private static void printSeparator() {
        System.out.println("------------------------------------------------------------------------" +
                "------------------------");
        System.out.println("\t\t\t\t\t\t\t\t\t" + GREEN + "Создание нового узла" + RESET);
        System.out.println("------------------------------------------------------------------------" +
                "------------------------");
    }

    /**
     * Метод выводящий информацию о переданном узле.
     * Скрыт, т.к. не использутеся напрямую
     */
    private static void printNodeInfo(Node node) {
        //Для каждого создающегося в данный момент узла
        //выводим информацию о родителе
        System.out.print(CYAN + "Родитель: " + RESET + node.getData());

        //Если тип данного узла ClassNode, то выводим еще и состояние
        if (node instanceof ClassNode) {
            System.out.print(" | " + PURPLE + "Статус родителя: " + RESET + ((ClassNode) node).getState());
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------" +
                "------------------------");
    }

    public
    /**
     * "Автомат создания графа (но это не точно автомат)"
     *
     * @return - ссылку на новую графовую структуру
     * */
    public static ClassNode createNewGraph() throws ChildNodeException {
        //"Состояние автомата"
        //Создаем очередь для хранения узлов
        Queue<Node> nodeQueue = new LinkedList<>();

        //Создание ссылки на узел общего типа
        Node tempNode;

        //создаем ссылку на корень
        ClassNode graphRoot = null;

        //Открытие потока ввода
        Scanner scanner = new Scanner(System.in);

        String input;    //Временные ссылки для хранения
        String name;     //введенных строк

        printSeparator();

        //Создание 1ого узла - корня графовой структуры
        //Механизм do while предалагает пользователю повторный ввод
        //при неверных введенных данных
        do {
            System.out.println("1. - Создать узел типа Класс(ClassNode)");
            System.out.println(RED + "q." + RESET + " - Завершить ввод на данном уровне");
            System.out.println();
            System.out.print("Ввод: ");

            input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case ("1"): {
                    System.out.print("Введите имя узла: ");
                    name = scanner.nextLine();

                    graphRoot = new ClassNode(name);
                    nodeQueue.offer(graphRoot);

                    //Когда закончили ввод, то устанавливает флаг - выход
                    input = "Выход";

                    break;
                }
                case ("q"): {
                    //Если мы вышли на данном шаге, то graphRoot = null
                    //поэтому сразу возращаем null
                    return null;
                }
                default: {
                    System.out.println(RED + "Ошибка ввода..." + RESET);
                    break;
                }
            }
        }
        while (!input.equals("Выход"));
        System.out.println();

        //Цикл do while
        //пока очередь не опустеет
        //делаем "шаги автомата"
        do {
            printSeparator();
            tempNode = nodeQueue.poll();    //"Вытаскиваем" узел из очереди

            assert tempNode != null;        //Проверка на null

            printNodeInfo(tempNode);        //Вывод информации о "вытащенном" узле

            //Если "вытащенный" узел имеет тип ClassNode
            //то предлагается создать дочерний подкласс или индивид
            if (tempNode instanceof ClassNode) {
                //Механизм do while предалагает пользователю повторный ввод
                //при неверных введенных данных
                do {
                    System.out.println("1. - Создать узел типа Подкласс(ClassNode)");
                    System.out.println("2. - Создать узел типа Индивид (IndividualNode)");
                    System.out.println(RED + "q." + RESET + " - Завершить ввод на данном уровне");
                    System.out.println();
                    System.out.print("Ввод: ");

                    input = scanner.nextLine();
                    System.out.println();

                    switch (input) {
                        //Создание нового подкласса
                        case ("1"): {
                            System.out.print("Введите имя узла: ");
                            name = scanner.nextLine();

                            ClassNode node = new ClassNode(name);

                            //Приводим общий узел к типу ClassNode и вызываем
                            //метод addChild
                            ((ClassNode) tempNode).addChild(node);

                            //Добавляем новый созданный узел в очередь
                            nodeQueue.offer(node);
                            break;
                        }

                        //Создание нового индивида
                        case ("2"): {
                            System.out.print("Введите имя индивида: ");
                            name = scanner.nextLine();

                            IndividualNode node = new IndividualNode(name);

                            //Приводим общий узел к типу ClassNode и вызываем
                            //метод addChild
                            ((ClassNode) tempNode).addChild(node);

                            //Добавляем новый созданный узел в очередь
                            nodeQueue.offer(node);
                            break;
                        }
                        case ("q"): {
                            break;
                        }
                        default: {
                            System.out.println(RED + "Ошибка ввода..." + RESET);
                            break;
                        }
                    }

                }
                while (!input.equals("q"));
            }
            System.out.println();

            //Если "вытащенный" узел имеет тип IndividualNode
            //то предлагается добавить атрибут для данного индивида
            if (tempNode instanceof IndividualNode) {
                //Механизм do while предалагает пользователю повторный ввод
                //при неверных введенных данных
                do {
                    System.out.println("1. - Создать узел типа Атрибут(AttributeNode)");
                    System.out.println(RED + "q." + RESET + " - Завершить ввод на данном уровне");
                    System.out.println();
                    System.out.print("Ввод: ");

                    input = scanner.nextLine();
                    System.out.println();

                    switch (input) {
                        //Создание нового атрибута
                        case ("1"): {
                            String data;

                            System.out.print("Введите имя атрибута: ");
                            name = scanner.nextLine();

                            System.out.print("Введите значение атрибута: ");
                            data = scanner.nextLine();

                            //Приводим общий узел к типу IndividualNode и вызываем
                            //метод addAttribute
                            ((IndividualNode) tempNode).addAttribute(name, data);

                            //Поскольку узла типа Атрибут и значение конечные,
                            //то их уже не добавляем в очередь
                            break;
                        }
                        case ("q"): {
                            break;
                        }
                        default: {
                            System.out.println(RED + "Ошибка ввода..." + RESET);
                            break;
                        }
                    }
                }
                while (!input.equals("q"));
            }
        }
        while (!nodeQueue.isEmpty());

        return graphRoot;
    }
}
