package printers;

import nodes.ClassNode;
import nodes.IndividualNode;
import nodes.Node;
import propertys.Property;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Класс, реализующий интрфейс Printable.
 * Предназначен для вывода информации о графовой структуре в консоль
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see printers.Printable
 * @see ClassNode
 */
public class GraphPrinter implements Printable<ClassNode> {
    private static GraphPrinter instance;    //Ссылка на единственный экземпляр класса

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
    private GraphPrinter() {
    }

    /**
     * Метод для получения единственного экземпляра
     * класса
     * <p>
     * Реализация паттерна singleton
     *
     * @return - ссылка на экземпляр данного класса
     */
    public static GraphPrinter getInstance() {
        if (instance == null) {
            instance = new GraphPrinter();
        }

        return instance;
    }

    /**
     * Реализация метода print из интефрейса
     * Printable
     *
     * @param graph - корень графовой структуры
     */
    @Override
    public void print(ClassNode graph) {
        //Если передана пустая ссылка, то завершение работы
        if (graph == null) return;

        System.out.println("\t\tПредставление графовой структуры в виде списков смежностей: ");
        System.out.println();

        System.out.print("Обозначения: ");
        System.out.print(RED + "узел дерева, ");
        System.out.print(YELLOW + "подкласс, ");
        System.out.print(GREEN + "индивид." + RESET);
        System.out.println();
        System.out.println();


        //Создаем очередь для хранения узлов
        Queue<Node> nodeQueue = new LinkedList<>();

        //Помещаем в очередь узел
        nodeQueue.offer(graph);

        //Ссылка на временные списки
        List<Property> tempList;
        List<Property> secondTempList;

        //Создание ссылки на узел общего типа
        Node tempNode;

        //Цикл do while
        //пока очередь не опустеет
        do {
            //"Вытаскиваем" узел из очереди
            tempNode = nodeQueue.poll();

            assert tempNode != null;

            //В временный список записываем
            tempList = tempNode.getPropertyList();

            //Вывод подсвеченного имени текущего узла
            printDataWithColor(tempNode);
            System.out.print(": ");

            //Если "вытащенный" узел не принадлжеит типу IndividualNode
            if (!(tempNode instanceof IndividualNode)) {
                //Проходя по потомкам выводим их имена с подсветкой
                for (int i = 0; i < tempList.size(); i++) {
                    printDataWithColor(tempList.get(i).getChildNode());
                    if (i < tempList.size() - 1) {
                        System.out.print(", ");
                    }

                    //добавляем дочерний узел в очередь
                    nodeQueue.offer(tempList.get(i).getChildNode());
                }
            }
            //Иначе выводим потомков и потомков потомков (Узлы AttributeNode и ValueNode)
            else {
                //Проход по узлам потомкам - Attribute узлам
                for (int i = 0; i < tempList.size(); i++) {
                    //Проход по потомкам потомков - value узлам
                    secondTempList = tempList.get(i).getChildNode().getPropertyList();
                    for (int j = 0; j < secondTempList.size(); j++) {
                        System.out.print(tempList.get(i).getChildNode().getData() + ": " +
                                tempList.get(i).getChildNode().getPropertyList().get(j).getChildNode().getData());
                    }
                    if (i < tempList.size() - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.print("\n");
        }
        while (!nodeQueue.isEmpty());
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
        }
        //Другой тип выводится зеленым
        else {
            System.out.print(GREEN);
        }

        System.out.print(node.getData() + RESET);
    }
}
