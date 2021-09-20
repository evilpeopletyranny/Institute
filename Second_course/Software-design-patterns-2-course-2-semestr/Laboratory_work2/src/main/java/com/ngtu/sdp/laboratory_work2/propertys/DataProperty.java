package com.ngtu.sdp.laboratory_work2.propertys;

import com.ngtu.sdp.laboratory_work2.nodes.Node;

/**
 * Свойство данных - связь между
 * Узлом родителем типа ClassNode и дочерним узлом
 * типа ClassNode или IndividualNode
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
