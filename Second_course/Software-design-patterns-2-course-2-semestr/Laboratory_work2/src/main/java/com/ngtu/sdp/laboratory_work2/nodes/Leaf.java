package com.ngtu.sdp.laboratory_work2.nodes;

/**
 * Конечй лист - не имеет потомков
 *
 * @see Node
 * @see ValueNode
 */
public abstract class Leaf extends Node {
    public Leaf() {
        super();
    }

    /**
     * Конструктор по умолчанию
     */
    public Leaf(String data) {
        super(data);
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     */
    public Leaf(Node parent, String data) {
        super(parent, data);
    }
}