package com.ngtu.sdp.loboratory_worl3.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Главный узел графовой структуры - служит корнем графа
 * и/или родителем для узлов типа IndividualNode
 *
 * @see ContainerNode
 * @see IndividualNode
 */
public class ClassNode extends ContainerNode
{
    private List<ClassNodeStateEnum> state;    //Поле отвечающее за состояния данного узла

    /**
     * Конструктор без параметров.
     * Инициализация списка состояний
     */
    public ClassNode()
    {
        state = new ArrayList<>();
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param states - параметр перемнной длины (автоупаковка в массив незвестного массива)
     */
    public ClassNode(String data, ClassNodeStateEnum... states)
    {
        super(data);
        this.state = new ArrayList<>(Arrays.asList(states));
    }

    /**
     * Конструктор с параметрами
     *
     * @param data   - данные узла
     * @param parent - ссылка на родителя
     * @param states - параметр перемнной длины (автоупаковка в массив незвестного массива)
     */
    public ClassNode(Node parent, String data, ClassNodeStateEnum... states)
    {
        super(parent, data);
        this.state = new ArrayList<>(Arrays.asList(states));
    }

    /**
     * Перегрузка метода добавления дочернего элемента.
     *
     * @param childNode - дочерний узел
     */
    @Override
    public void addChildNode(Node childNode)
    {
        super.addChildNode(childNode);
        //Если передан узел ClassNode, то текущий узел получает состояние ИМЕЕТ_ПОДКЛАСС
        if (childNode instanceof ClassNode) {
            state.add(ClassNodeStateEnum.HAVE_SUBCLASS);
        }
        //Если передан узел IndividualNode, то текущий узел получает состояние ИМЕЕТ_ИНДИВИДА
        if (childNode instanceof IndividualNode) {
            state.add(ClassNodeStateEnum.HAVE_INDIVIDUAL);
        }
    }

    /**
     * Возвращает состояние данного узла.
     *
     * @return состояние
     */
    public List<ClassNodeStateEnum> getState()
    {
        return state;
    }

    /**
     * Возвращает состояние данного узла в виде строки.
     *
     * @return в виде строки.
     */
    public String getStateAsString()
    {
        StringBuilder stateStr = new StringBuilder();
        for (int i = 0; i < state.size() - 1; i++) {
            stateStr.append(state.get(i).toString()).append(", ");
        }

        stateStr.append(state.get(state.size() - 1));

        return stateStr.toString();
    }

    /**
     * Установливает состояние узла
     *
     * @param state - состояние, которое необходимо задать.
     */
    public void addState(ClassNodeStateEnum state)
    {
        this.state.add(state);
    }
}
