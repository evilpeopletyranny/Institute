package propertys;

import nodes.Node;

/**
 * Абстрактный класс, содержащий основные поля
 * и методы связей - свойств между узлами графовой структуры.
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @author Valerii Sukhorukov    19-IVT-3
 * @author Vyacheslav Mostashov  19-IVT-3
 * @see DataProperty
 * @see ObjectProperty
 * @see Node
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
