package com.ngtu.sdp.laboratory_work2.builder;

import com.ngtu.sdp.laboratory_work2.nodes.ClassNode;
import com.ngtu.sdp.laboratory_work2.nodes.ContainerNode;
import com.ngtu.sdp.laboratory_work2.nodes.IndividualNode;
import com.ngtu.sdp.laboratory_work2.nodes.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Класс, управляющий Builder'ом
 *
 * @see Builder
 * @see GraphBuilder
 */
@Component("director")
@Scope("singleton")
public class Director {
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";

    /**
     * Конструктор по умолчанию.
     */
    public Director() {
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
     *
     * @param node - узел, ин-ию о котором необходимо вывести.
     */
    private static void printNodeInfo(Node node) {
        //Для каждого создающегося в данный момент узла
        //выводим информацию о родителе
        System.out.print(CYAN + "Родитель: " + RESET + node.getData());

        //Если тип данного узла ClassNode, то выводим еще и состояние
        if (node instanceof ClassNode) {
            System.out.print(" | " + PURPLE + "Статус родителя: " + RESET + ((ClassNode) node).getStateAsString());
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------" +
                "------------------------");
    }

    /**
     * Метод сборки графа
     *
     * @param builder - экземпляр билдера при помощи которого будет производится
     *                создание структуры.
     * @return оболочку Optional с родительским узлом
     */
    public Optional<ContainerNode> build(Builder builder) {
        //Создаем очередь для хранения узлов
        Queue<ContainerNode> nodeQueue = null;

        ContainerNode outputNode;
        ContainerNode inputNode;
        ContainerNode root = null;

        //Открытие потока ввода
        Scanner scanner = new Scanner(System.in);

        String input;    //Временные ссылки для хранения
        String data;     //введенных строк

        //Создание 1ого узла - корня графовой структуры
        //Механизм do while предалагает пользователю повторный ввод
        //при неверных введенных данных
        do {
            printSeparator();
            System.out.println("1. - Создать корень дерева");
            System.out.println(RED + "q." + RESET + " - Завершить ввод на данном уровне");
            System.out.println();
            System.out.print("Ввод: ");

            input = scanner.nextLine();
            System.out.println();

            switch (input) {
                case ("1"): {
                    nodeQueue = new ArrayDeque<>();

                    data = "";
                    System.out.print("Введите имя узла: ");
                    while (data.isBlank()) {
                        data = scanner.nextLine();
                    }
                    root = builder.reset(data);
                    nodeQueue.offer(root);

                    //Когда закончили ввод, то устанавливает флаг - выход
                    input = "q";
                    break;
                }
                case ("q"): {
                    //Если мы вышли на данном шаге, то graphRoot = null
                    //поэтому сразу возращаем null
                    return Optional.empty();
                }
                default: {
                    System.out.println(RED + "Ошибка ввода..." + RESET);
                    break;
                }
            }
        }
        while (!input.equals("q"));
        System.out.println();

        //Цикл do while
        //пока очередь не опустеет
        //делаем "шаги автомата"
        do {
            printSeparator();
            outputNode = nodeQueue.poll();    //"Вытаскиваем" узел из очереди

            assert outputNode != null;        //Проверка на null
            printNodeInfo(outputNode);        //Вывод информации о "вытащенном" узле

            //Если "вытащенный" узел имеет тип ClassNode
            //то предлагается создать дочерний подкласс или индивид
            if (outputNode instanceof ClassNode) {
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
                            data = "";
                            System.out.print("Введите имя узла: ");
                            while (data.isBlank()) {
                                data = scanner.nextLine();
                            }
                            inputNode = builder.toClassNodeAddClassNode(outputNode, data);

                            nodeQueue.offer(inputNode);
                            break;
                        }

                        //Создание нового индивида
                        case ("2"): {
                            System.out.print("Введите имя индивида: ");
                            data = scanner.nextLine();
                            inputNode = builder.toClassNodeAddIndividualNode(outputNode, data);

                            //Добавляем новый созданный узел в очередь
                            nodeQueue.offer(inputNode);
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
            if (outputNode instanceof IndividualNode) {
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
                            data = "";
                            System.out.print("Введите имя атрибута: ");
                            String name = scanner.nextLine();

                            System.out.print("Введите значение атрибута: ");
                            while (data.isBlank()) {
                                data = scanner.nextLine();
                            }

                            builder.toIndividualNodeAddAttributeNode(outputNode, name, data);

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

        assert root != null;
        return Optional.of(root);
    }
}
