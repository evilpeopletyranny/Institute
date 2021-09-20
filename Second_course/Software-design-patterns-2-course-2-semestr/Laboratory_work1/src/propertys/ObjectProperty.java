package propertys;

import nodes.Node;

/**
 * Свойство данных - связь между
 * узлами типов IndividualNode -> AttributeNode
 * и AttributeNode -> ValueNode
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see Node
 * @see nodes.IndividualNode
 * @see nodes.AttributeNode
 * @see nodes.ValueNode
 */
public class ObjectProperty extends Property {
    /**
     * Констурктор с параметром,
     * использует конструктор род. класса
     */
    public ObjectProperty(Node childNode) {
        super(childNode);
    }
}
