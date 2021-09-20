package com.ngtu.sdp.laboratory_work2.builder;

import com.ngtu.sdp.laboratory_work2.nodes.ContainerNode;

/**
 * Интерфейс строителя.
 *
 * @see GraphBuilder
 * @see Director
 */
public interface Builder {
    /**
     * Сброс строителя, посторение нового графа.
     *
     * @param data - данные корня графа.
     * @return полученный узел
     */
    ContainerNode reset(String data);

    /**
     * Метод, для добавления к узлу ClassNode потомка ClassNode
     *
     * @param childNodeData - данные дочернего узла
     * @param srcNode       - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    ContainerNode toClassNodeAddClassNode(ContainerNode srcNode, String childNodeData);

    /**
     * Метод, для добавления к узлу ClassNode потомка IndividualNode
     *
     * @param childNodeData - данные дочернего узла
     * @param srcNode       - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    ContainerNode toClassNodeAddIndividualNode(ContainerNode srcNode, String childNodeData);

    /**
     * Метод, для добавления к узлу IndividualNode потомка AttributeNode с параметром
     *
     * @param value       - значение атрибута
     * @param atrNodeData - данные дочернего узла
     * @param srcNode     - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    ContainerNode toIndividualNodeAddAttributeNode(ContainerNode srcNode, String atrNodeData, String value);
}
