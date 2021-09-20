package propertys;

import nodes.Node;

/**
 * Свойство данных - связь между
 * Узлом родителем типа ClassNode и дочерним узлом
 * типа ClassNode или IndividualNode
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see Node
 * @see nodes.ClassNode
 * @see nodes.IndividualNode
 */
public class DataProperty extends Property {

    /**
     * Констурктор с параметром,
     * использует конструктор род. класса
     */
    public DataProperty(Node childNode) {
        super(childNode);
    }
}
