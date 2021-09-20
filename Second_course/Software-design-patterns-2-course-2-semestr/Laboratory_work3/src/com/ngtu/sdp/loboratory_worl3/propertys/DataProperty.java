package com.ngtu.sdp.loboratory_worl3.propertys;

import com.ngtu.sdp.loboratory_worl3.nodes.Node;

/**
 * Свойство данных - связь между
 * Узлом родителем типа ClassNode и дочерним узлом
 * типа ClassNode или IndividualNode
 */
public class DataProperty extends Property
{

    /**
     * Констурктор с параметром,
     * использует конструктор род. класса
     */
    public DataProperty(Node childNode)
    {
        super(childNode);
    }
}
