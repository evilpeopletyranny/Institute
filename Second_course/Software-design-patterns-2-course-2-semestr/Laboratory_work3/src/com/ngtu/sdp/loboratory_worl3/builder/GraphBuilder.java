package com.ngtu.sdp.loboratory_worl3.builder;


import com.ngtu.sdp.loboratory_worl3.nodes.*;

/**
 * Частная релизация bilder'a
 * Bilder для графа.
 *
 * @see Builder
 */
public class GraphBuilder implements Builder
{
    public GraphBuilder() {
    }

    /**
     * Метод, для добавления к узлу ClassNode потомка ClassNode
     *
     * @param childNodeData - данные дочернего узла
     * @param srcNode       - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    @Override
    public ContainerNode toClassNodeAddClassNode(ContainerNode srcNode, String childNodeData) {
        //Создание нового дочернего узла
        ClassNode childNode = new ClassNode(srcNode, childNodeData, ClassNodeStateEnum.SUBCLASS);

        //К род. узлу добавляем новый узел
        srcNode.addChildNode(childNode);

        //Если родиетльский узел еще не имеет состояния "ИМЕЕТ ПОДКЛАСС", то задаем ему это состяние
        if (!((ClassNode) srcNode).getState().contains(ClassNodeStateEnum.HAVE_SUBCLASS)) {
            ((ClassNode) srcNode).addState(ClassNodeStateEnum.HAVE_SUBCLASS);
        }

        return childNode;
    }

    /**
     * Метод, для добавления к узлу ClassNode потомка IndividualNode
     *
     * @param childNodeData - данные дочернего узла
     * @param srcNode       - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    @Override
    public ContainerNode toClassNodeAddIndividualNode(ContainerNode srcNode, String childNodeData) {
        //Создание нового дочернего узла
        IndividualNode childNode = new IndividualNode(srcNode, childNodeData);

        //К род. узлу добавляем новый узел
        srcNode.addChildNode(childNode);

        //Если родиетльский узел еще не имеет состояния "ИМЕЕТ ИНДИВИДА", то задаем ему это состяние
        if (!((ClassNode) srcNode).getState().contains(ClassNodeStateEnum.HAVE_INDIVIDUAL)) {
            ((ClassNode) srcNode).addState(ClassNodeStateEnum.HAVE_INDIVIDUAL);
        }

        //Автоматическое задание Атрибута ID
        childNode.setID();

        return childNode;
    }

    /**
     * Метод, для добавления к узлу IndividualNode потомка AttributeNode с параметром
     *
     * @param value       - значение атрибута
     * @param atrNodeData - данные дочернего узла
     * @param srcNode     - узел родитель, к которому необходимо добавить потомка.
     * @return полученный узел
     */
    @Override
    public ContainerNode toIndividualNodeAddAttributeNode(ContainerNode srcNode, String atrNodeData, String value) {
        //Создание нового узла значения
        ValueNode valueNode = new ValueNode(value);

        //Создание нового узла атрибута
        AttributeNode atrNode = new AttributeNode(srcNode, atrNodeData);

        //К атрибуту прибавляем значение
        atrNode.addChildNode(valueNode);

        //Значению записываем родителя атрибута
        valueNode.setParent(atrNode);

        //К род. узлу добавляем новый узел
        srcNode.addChildNode(atrNode);
        return atrNode;
    }
}
