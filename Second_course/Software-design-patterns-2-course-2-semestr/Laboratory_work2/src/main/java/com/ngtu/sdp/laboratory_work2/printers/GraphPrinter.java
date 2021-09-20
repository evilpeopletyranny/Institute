package com.ngtu.sdp.laboratory_work2.printers;

import com.ngtu.sdp.laboratory_work2.nodes.*;
import com.ngtu.sdp.laboratory_work2.propertys.Property;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Класс, реализующий интрфейс Printable.
 * Предназначен для вывода информации о графовой структуре в консоль
 */
@Component("printer")
@Scope("singleton")
public class GraphPrinter implements Printable<ContainerNode> {
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    /**
     * Приватный конструктор.
     * Запрещяет свободное создание объектов данного класс
     */
    public GraphPrinter() {
    }

    @Override
    public void print(ContainerNode node) {
        System.out.println("\t\tПредставление графовой структуры в виде списков смежностей: ");
        System.out.println();

        System.out.print("Обозначения: ");
        System.out.print(RED + "узел дерева, ");
        System.out.print(YELLOW + "подкласс, ");
        System.out.print(GREEN + "индивид." + RESET);
        System.out.println();
        System.out.println();


        //Создаем очередь для хранения узлов
        Queue<ContainerNode> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(node);

        //Пока очередь не пуста
        //Вытаскиваем узлы, пишем о них инф-ию, заносим потомков в очередь
        while (!nodeQueue.isEmpty()) {
            //Вытаскиваем узел, пишем о нем инф-ию
            node = nodeQueue.poll();
            printDataWithColor(node);
            System.out.print(": ");


            node.getPropertyList().stream()            //Получаем стрим из списка связей узла
                    .map(Property::getChildNode)       //получаем из каждой связи дочерний узел
                    .forEach(new Consumer<Node>() {    //Для каждого узла выполянем метод accept
                        @Override
                        //анонимного класса, реализующего функцианальный интерфейс
                        public void accept(Node node)          //Consumer
                        {
                            //выводим ин-ию о зуле
                            printDataWithColor(node);

                            //если узел не AttributeNode, то заносим в очередь
                            if (!(node instanceof AttributeNode)) addToQueue(node);
                        }

                        public void addToQueue(Node node) {
                            nodeQueue.offer((ContainerNode) node);
                        }
                    });
            System.out.println();
        }
    }

    /**
     * Вспомогательный метод, выводящий имя узла
     * разными цветами, в зависимости от типа
     * <p>
     * Красный - корень дерева
     * Желтый  - подкласс
     * Зеленый - индивид
     */
    private void printDataWithColor(Node node) {
        //Если переданный узел относится к типу ClassNode
        if (node instanceof ClassNode) {
            //Если у узла нет родителя - то узел является корнем
            //и выводится красным
            if (node.getParent() == null) {
                System.out.print(RED);
            }
            //Иначе узел является подклассом и выводится
            //желтым
            else {
                System.out.print(YELLOW);
            }
            System.out.print(node.getData() + RESET + " ");
        }
        //Тип Индивид выводится зеленым
        if (node instanceof IndividualNode) {
            System.out.print(GREEN);
            System.out.print(node.getData() + RESET + " ");
        }

        //Атриуб и значения выводится без выделения цветом.
        if (node instanceof AttributeNode) {
            System.out.print(node.getData() + " = " + ((AttributeNode) node).getPropertyList()
                    .get(0).getChildNode().getData() + " | ");
        }
    }

}
