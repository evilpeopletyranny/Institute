package com.ngtu.sdp.loboratory_worl3.nodes;

/**
 * Конечй лист - не имеет потомков
 *
 * @see Node
 * @see ValueNode
 */
public abstract class Leaf extends Node
{
    public Leaf()
    {
        super();
    }

    /**
     * Конструктор по умолчанию
     */
    public Leaf(String data)
    {
        super(data);
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     */
    public Leaf(Node parent, String data)
    {
        super(parent, data);
    }
}