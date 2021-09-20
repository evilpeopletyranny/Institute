/**
 * Tree - класс, реализующий n-арное дерево.
 *
 * @author  Vladislav Sapozhnikov
 * @param <E> тип, содержащийся в данной коллекции (узлах дерева)
 * */
public class Tree<E>
{
    private final SinglyLinkedList<Node<E>> nodeList;  //список 'имен' узлов дерева

    /**
     * Класс - узел/вершина дерева.
     * Стандартный класс для большинства динамических структур.
     *
     * @param <E> тип, содержащийся в данном узле
     * */
    private static class Node<E>
    {                                                           //данная реализация дерева не требует указателя на родителя
        private final String                    name;           //'имя' данного узла
        private final SinglyLinkedList<Node<E>> child;          //список потомков данного узла

        /**
         * Конструктор с параметром.
         *
         * @param name - 'имя' для данной вершины.
         * */
        Node(String name)
        {
            this.name = name;
            child     = new SinglyLinkedList<>();
        }
    }

    /**
     * Вспогательный метод.
     * Создает список узлов с именами переданными
     * в списке строк.
     *
     * @param list      - список строк - имена вершин.
     *
     * @return nodeList - список вершин, с именами, полученными
     *                    из списка.
     * */
    private SinglyLinkedList<Node<E>> makeNodeList(SinglyLinkedList<String> list)
    {
        SinglyLinkedList<Node<E>> nodeList = new SinglyLinkedList<>();

        for (int i = 0; i < list.getSize(); i++)
        {
            Node<E> buffNode = new Node<>(list.get(i));    //создание узла с именем из списка
            nodeList.push_back(buffNode);                  //добавление узла в списко узлов
        }

        return nodeList;
    }

    /**
     * Вспомогательный метод.
     * Сортировка массива пузырьком.
     * Помогает отсортировать самый длинный путь.
     * Используется в вспомогательном методе pathBuilder.
     *
     * @param arr - целочисленный массив, который необходимо отсортировать.
     * */
    private void sortArr(int[] arr)
    {
        int buff;                                 //вспомогательная переменная для обмена значений

        for (int i = 0; i < arr.length-1; i++)    //Алгоритм сортировки пузырьком.
        {                                         //
            if (arr[i] > arr[i+1])                //Если текущий элемент больше следущего
            {                                     //то сохраняем в переменную значение
                buff       = arr[i];              //текущего элемента
                arr[i]     = arr[i + 1];          //в текущий элемент записываем значение следущего
                arr[i + 1] = buff;                //значение слоедущего меняем на значение из перемнной
            }                                     //
        }                                         //Максимальный элемент становится крайним правым
    }                                             //элементом массива

    /**
     * Вспомогательный метод.
     * Создает одномерный массив, содержащий длины списков из
     * переданного двумерного списка.
     *
     * @param list     - двумерный список, длины списков которого требуется
     *                   получить.
     *
     * @return sizeArr - получившийся массив размерностей списков.
     * */
    private int[] getSizeArr(SinglyLinkedList<SinglyLinkedList<Node<E>>> list)
    {
        int size      = list.getSize();            //создание массива размерностью
        int[] sizeArr = new int[size];             //равной кол-во списков в двумерном списке

        for (int i = 0; i < size; i++)
        {
            sizeArr[i] = list.get(i).getSize();    //запись в элементы массива длин списков
        }

        return sizeArr;
    }

    /**
     * Вспомогательный метод.
     * По переданному отсортированному массиву размерностей списка
     * наход самый длиный путь.
     *
     * @param arr  - отсортированный массив размерностей списка.
     *
     * @param list - двумерный списко путей.
     * */
    private void foundLongWay(int[] arr, SinglyLinkedList<SinglyLinkedList<Node<E>>> list)
    {
        System.out.println();
        if (arr[arr.length-1] == 1)
        {
            System.out.println("Все пути в дереве содержат ветвления.");
            return;
        }
        for (int i = 0; i < arr.length; i++)                   //т.к. самый большой элемент массива
        {                                                      //является последним, то сравниваем
            if (list.get(i).getSize() == arr[arr.length-1])    //длину списков списка с последним элементом массива
            {
                System.out.print("Длинейший путь: ");
                printNodeList(list.get(i));                    //вывод самого длинного пути в консоль.
            }
        }
    }


    /**
     * Вспомогательный метод.
     * Заполняет список вершинами пути от которых не имеет ветвлений.
     *
     * @param nodeList - список вершин без ветвлений.
     *
     * @param wayList  - список для заполнения путей.
     * */
    private void pathBuilder (SinglyLinkedList<Node<E>> nodeList, SinglyLinkedList<SinglyLinkedList<Node<E>>> wayList)
    {
        for (int i = 0; i < nodeList.getSize(); i++) {                  //на каждую вершину списка вершин
            wayList.push_back(new SinglyLinkedList<>());                //начинаем построение собственного пути

            Node<E> buffNode = nodeList.get(i);                         //при помощи вспомогательной переменной - узла
            wayList.get(i).push_back(buffNode);                         //производим обход по потомкам


            while (buffNode.child.getSize() != 0)                       //и записываем путь в список вплоть до
            {                                                           //конечной вершины данного пути
                wayList.get(i).push_back(buffNode.child.get(0));
                buffNode = buffNode.child.get(0);
            }
        }
    }

    /**
     * Вспомогательный метод.
     * Выводит элементы одномерного списка.
     * */
    private void printNodeList(SinglyLinkedList<Node<E>> list)
    {
        for (int i = 0; i < list.getSize(); i++)
        {
            System.out.print(list.get(i).name + " ");
        }
        System.out.println();
    }

    /**
     * Вспомогательный метод.
     * Заполняет список вершинами от которых не идет ветвлений.
     *
     * @param list  - список для заполнения.
     * */
    private void makeVertexWOBranchingList(SinglyLinkedList<Node<E>> list)
    {
        for (int i = 0; i < nodeList.getSize(); i++)
        {
            if (nodeList.get(i).child.getSize() == 0 || nodeList.get(i).child.getSize() == 1)    //если у вершины нет
            {                                                                                    //или только 1 потомок
                list.push_back(nodeList.get(i));                                                 //заносим ее в список
            }
        }
    }

    /**
     * Конструктор дерева с параметрами.
     *  @param initList   - список с именами вершин, которые
     *                     будут хранится в дереве.
     *
     * @param initMatrix - матрица смежности данного дерева. */
    Tree(SinglyLinkedList<String> initList, int[][] initMatrix)
    {
        nodeList = makeNodeList(initList);                               //заполнение списка вершинданного дерева
        int size     = initMatrix.length;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (initMatrix[i][j] == 1)
                {
                    nodeList.get(i).child.push_back(nodeList.get(j));    //заполнение списков смежности вершин
                }
            }
        }
    }

    /**
     * Метод, находящий самый длиныый путь без ветвлений.
     * */
    public void searchLongWay()
    {
        SinglyLinkedList<SinglyLinkedList<Node<E>>> wayList = new SinglyLinkedList<>();    //создание ссылки на
                                                                                           //двумерный список,
                                                                                           //в котором будут хранится
                                                                                           //все пути без ветвлений

        SinglyLinkedList<Node<E>> vertexWithoutBranching = new SinglyLinkedList<>();       //создание ссылки на список
                                                                                           //вершин, в котором будут
                                                                                           //хранится все вершины
                                                                                           //пути от которых не имеют
                                                                                           //ветвлений.

        System.out.println("\u001B[35mВычисление вершин, участвующий в путях без ветвлений\u001B[0m");
        makeVertexWOBranchingList(vertexWithoutBranching);        //заполнение списка вершинами, пути от которых не
                                                                  //имеет ветвлений

        System.out.println("\u001B[35mПостроение всех возможных путей без ветвлений\u001B[0m");
        pathBuilder(vertexWithoutBranching, wayList);             //заполнение списка всеми возможными путями без
                                                                  //ветвлений

        System.out.println("\u001B[35mВычисление длин путей и их сравнение\u001B[0m");
        int[] sizeArr = getSizeArr(wayList);                      //создание массива с размерностями путей
        sortArr(sizeArr);                                         //сортировка массива
        foundLongWay(sizeArr, wayList);                           //поиск самого длинного пути без ветвлений на основе
                                                                  //списка путей и размерностей.
    }

    /**
     * Вывод списков смежнсти для каждой вершины.
     * Аналог матрицы смежностей.
     * */
    public void printAdjacencyList()
    {
        for (int i = 0; i < nodeList.getSize(); i++)
        {
            System.out.print("\u001B[34m" + nodeList.get(i).name + "\u001B[0m: ");//Вывод имени вершины список
            if (nodeList.get(i).child.getSize() != 0)                             //смежности которой выводится
            {
                for (int j = 0; j < nodeList.get(i).child.getSize(); j++)
                {
                    System.out.print(nodeList.get(i).child.get(j).name + " ");    //вывод списка смежности вершины
                }
            }
            else
            {                                                                     //если вершина не имеет дочерних
                System.out.print("-");                                            //вершин, то выводим 'прочерк'
            }
            System.out.println();
        }
    }
}