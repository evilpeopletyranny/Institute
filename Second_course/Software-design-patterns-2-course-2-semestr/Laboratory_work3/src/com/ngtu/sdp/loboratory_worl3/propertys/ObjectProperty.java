package com.ngtu.sdp.loboratory_worl3.propertys;


import com.ngtu.sdp.loboratory_worl3.nodes.Node;

/**
 * Свойство данных - связь между
 * узлами типов IndividualNode -> AttributeNode
 * и AttributeNode -> ValueNode
 */
public class ObjectProperty extends Property
{
    /**
     * Констурктор с параметром,
     * использует конструктор род. класса
     */
    public ObjectProperty(Node childNode)
    {
        super(childNode);
    }
}
