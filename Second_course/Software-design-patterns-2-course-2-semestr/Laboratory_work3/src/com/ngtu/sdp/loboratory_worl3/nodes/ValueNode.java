package com.ngtu.sdp.loboratory_worl3.nodes;

/**
 * Узел значение.
 * Содержит значение Атрибута
 *
 * @see Leaf
 * @see AttributeNode
 */
public class ValueNode extends Leaf
{
    /**
     * Конструктор по умолчанию
     */
    public ValueNode()
    {
        super();
    }

    /**
     * Конструктор с параметрами
     *
     * @param data - данные узла
     */
    public ValueNode(String data)
    {
        super(data);
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     */
    public ValueNode(Node parent, String data)
    {
        super(parent, data);
    }
}
