package com.ngtu.sdp.loboratory_worl3.nodes;

/**
 * Абстрактный класс, содержащий основные поля
 * и методы узлов графовой структуры
 *
 * @see ContainerNode
 * @see Leaf
 */
public abstract class Node
{
    protected Node parent;    //Поле для хранении ссылки на родительский узел
    protected String data;    //Поле для хранения данных

    /**
     * Конструктор по умолчанию.
     */
    public Node()
    {    }

    /**
     * Конструктор с параметрами
     *
     * @param data - данные узла
     */
    public Node(String data)
    {
        this.data = data;
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     */
    public Node(Node parent, String data)
    {
        this.parent = parent;
        this.data = data;
    }

    /**
     * Метод для получения ссылки на родительский узел
     *
     * @return - ссылка на родительский узел
     */
    public Node getParent()
    {
        return parent;
    }

    /**
     * Метод для получения данных из узла
     *
     * @return - данные
     */
    public String getData()
    {
        return data;
    }

    /**
     * Метод для задания ссылки на родительский узел
     *
     * @param parent - ссылка на родительский узел
     */
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    /**
     * Метод для задания строки данных
     *
     * @param data - строка данных
     */
    public void setData(String data)
    {
        this.data = data;
    }
}
