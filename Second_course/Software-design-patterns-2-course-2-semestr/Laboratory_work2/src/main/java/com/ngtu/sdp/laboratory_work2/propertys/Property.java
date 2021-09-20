package com.ngtu.sdp.laboratory_work2.propertys;

import com.ngtu.sdp.laboratory_work2.nodes.Node;

/**
 * Абстрактный класс, содержащий основные поля
 * и методы связей - свойств между узлами графовой структуры.
 */
public abstract class Property {
    protected Node childNode;    //Ссылка на дочерний узел

    /**
     * Конструктор с параметром
     *
     * @param childNode - ссылка на узел, с которым устанавливается
     *                  связь
     */
    public Property(Node childNode) {
        this.childNode = childNode;
    }

    /**
     * Метод для получения ссылки на дочерний узел
     *
     * @return - ссылка на дочерний узел
     */
    public Node getChildNode() {
        return childNode;
    }
}
