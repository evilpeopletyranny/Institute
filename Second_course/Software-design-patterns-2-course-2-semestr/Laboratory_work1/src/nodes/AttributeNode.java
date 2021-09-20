package nodes;

import propertys.DataProperty;

/**
 * Узел атрибут
 * Фомрмируется при вывозе метод addAttribute
 * у узла типа IndividualNode
 * <p>
 * Содержит значение Имя атрибута и ссылку на значение
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see Node
 * @see ValueNode
 */
public class AttributeNode extends Node {
    /**
     * Конструктор с параметрами
     *
     * @param name  - имя данного атрибута
     * @param value - значение, которое будет передано узлу ValueNode
     */
    public AttributeNode(String name, String value) {
        this.data = name;
        ValueNode valueNode = new ValueNode(value, this);
        addChildNode(valueNode);
    }

    /**
     * Метод для добавления дочернего узла
     * Дочерним узлом для данного типа может быть только узел
     * типа ValueNode
     * Формирует связь типа DataProperty
     *
     * @param node - дочерний узел типа ValueNode
     */
    public void addChildNode(ValueNode node) {
        DataProperty property = new DataProperty(node);
        propertyList.add(property);
    }
}
