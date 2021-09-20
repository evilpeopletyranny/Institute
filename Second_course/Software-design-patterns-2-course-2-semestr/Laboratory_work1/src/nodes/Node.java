package nodes;

import propertys.Property;

import java.util.LinkedList;
import java.util.List;

/**
 * Абстрактный класс, содержащий основные поля
 * и методы узлов графовой структуры
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see ClassNode
 * @see IndividualNode
 * @see AttributeNode
 * @see ValueNode
 */
public abstract class Node {
    protected Node parent;    //Поле для хранении ссылки на родительский узел
    protected String data;    //Поле для хранения данных

    //Список связей с дочерними узлами
    protected List<Property> propertyList = new LinkedList<>();

    /**
     * Метод для получения ссылки на родительский узел
     *
     * @return - ссылка на родительский узел
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Метод для задания ссылки на родительский узел
     *
     * @param parent - ссылка на родительский узел
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Метод для получения данных из узла
     *
     * @return - данные
     */
    public String getData() {
        return data;
    }

    /**
     * Метод для получения ссылки на список связей с
     * дочерними узлами
     *
     * @return - список связей с дочерними узлами
     */
    public List<Property> getPropertyList() {
        return propertyList;
    }
}
