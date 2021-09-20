package com.ngtu.sdp.loboratory_worl3.nodes;

import java.util.stream.Stream;

/**
 * Класс Индивид - экземпляр класс/подкласса типа ClassNode
 *
 * @see ContainerNode
 * @see AttributeNode
 * @see ValueNode
 */
public class IndividualNode extends ContainerNode
{
    /**
     * Конструктор по умолчанию
     */
    public IndividualNode()
    {
        super();
    }


    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     */
    public IndividualNode(Node parent, String data)
    {
        super(parent, data);
    }

    /**
     * Метод для задания атрибута ID на основе hashCode данного объекта
     */
    public void setID()
    {
        int id = this.hashCode();
        //Создаем узел значение, передаем в него hashCode
        ValueNode valNode = new ValueNode(Integer.toString(id < 0 ? -(id) : id));

        //Создание атрибута ID
        AttributeNode atrNode = new AttributeNode(this, "ID");

        //Создание связи между атрибутом и значением
        atrNode.addChildNode(valNode);
        valNode.setParent(atrNode);

        this.addChildNode(atrNode);
    }

    /**
     * Перегруженный метод получения хэша
     *
     * @return значение хэша данного объекта
     */
    @Override
    public int hashCode()
    {
        int hash = propertyList.stream()                     //получаем Stream из списка связей с дочерними узлами
                .map(x -> x.getChildNode().getData())        //из каждой связи получаем дочерний узел, а потом ин-ию из него
                .reduce("", String::concat)          //складываем всю ин-ию в одну строку
                .chars()                                     //получаем стрим интов (char = int) из строки
                .reduce(0, (x, y) -> x * 31 + y);    //складываем элементы, домнажая каждый новый на простое число

        hash += Stream.of(parent.getData(), data)            //получаем инф-ии об узле и родителе
                .reduce("", String::concat)          //складываем всю ин-ию в одну строку
                .chars()                                     //получаем стрим интов (char = int) из строки
                .reduce(0, (x, y) -> x * 31 + y);    //складываем элементы, домнажая каждый новый на простое число

        return hash;
    }
}
