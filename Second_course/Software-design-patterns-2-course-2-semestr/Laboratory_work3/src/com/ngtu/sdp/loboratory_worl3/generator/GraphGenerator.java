package com.ngtu.sdp.loboratory_worl3.generator;

import com.ngtu.sdp.loboratory_worl3.query.Query;
import com.ngtu.sdp.loboratory_worl3.builder.Builder;
import com.ngtu.sdp.loboratory_worl3.nodes.*;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Генератор графовой структуры.
 * Созданный граф содержит:
 *     Корневой узел
 *     1-4 подкласса корневого узла
 *     0-4 индивидов для каждого подкласса
 *     1-5 атрибутов для каждого индивида (включая ID)
 *
 * @see Generator
 * @see Builder
 * @see Query
 * */
public class GraphGenerator implements Generator<ClassNode>, Runnable
{
    private final Builder builder;              //ссылка на билдер
    private List<ClassNode> resList;            //ссылка на список корневых узлов, в который генератор
                                                // будут заносить готовые грфы

    private final Queue<Query> queue;           //очредь запросв - разделяемый ресурс

    private final Semaphore genSemaphore;
    private final Semaphore clientSemaphore;    //семафор клиента

    private final String name;                 //имя генератора


    public GraphGenerator(Semaphore genSemaphore, Semaphore clientSemaphore, String name, Queue<Query> queue, Builder builder)
    {
        this.builder = builder;
        this.name = name;
        this.genSemaphore = genSemaphore;
        this.clientSemaphore = clientSemaphore;
        this.queue = queue;
    }

    /**
     * Метод run() интефейса Runnable - данный код выполняется
     * в дополнительном потоке.
     * Алгоритм задачи о спящем брадобрее для среверной стороны
     * */
    @Override
    public void run()
    {
        try
        {
            while (!queue.isEmpty())
            {
                clientSemaphore.acquire();
                Query query;
                synchronized (queue)
                {
                    query = queue.remove();
                    genSemaphore.release();
                }
                System.out.println(name + " начал создание " + query.getGraphName());
                resList.add(generate(query));
                System.out.println(name + " закончил создание " + query.getGraphName());
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Генерация графа.
     * Имя графа берется из запроса.
     *
     * @param query   - запрос на создание графа. */
    @Override
    public synchronized ClassNode generate(Query query)
    {
        Queue<ContainerNode> nodeQueue = new ArrayDeque<>();    //очередь для создания узлов

        ContainerNode outputNode;                               //переменная для хранения обрабатываемого
                                                                //(вытащенного из очереди) узла

        ContainerNode tempNode;                                 //переменная для хранения ссылки на созданный узел

        /* Из запроса получаем имя графа (корневого узла).
           Создаем узел с таким именем и помещаем в очередь. */
        ClassNode rootNode = new ClassNode(query.getGraphName());
        nodeQueue.add(rootNode);

        //генерация узлов, пока очередь не будет пуста
        while (!nodeQueue.isEmpty())
        {
            try
            {
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            //Вытаскиваем из чоереди узел, который будет обрабатывать.
            outputNode = nodeQueue.poll();

            //Обработка, если вытащенный узел принадлежит классу ClassNode
            if (outputNode instanceof ClassNode)
            {
                /* Для сокращения созданного графа иметь подклассы может только корневой узел.
                   До первой операции корневой узел не имеет состояний.
                   К корневому узлы добавляем от 1 до 2 подклассов*/
                if (((ClassNode) outputNode).getState().isEmpty())
                {
                    for (int i = 0; i < getRandomNumberUsingInts(1, 3); i++)
                    {
                        tempNode = builder.toClassNodeAddClassNode(outputNode, "подкласс" + "№" + (i + 1));
                        nodeQueue.offer(tempNode);
                    }
                }

                //Для любого узла типа ClassNode создаем от 0 до 3 индивидов
                for (int i = 0; i < getRandomNumberUsingInts(0, 4); i++)
                {
                    tempNode = builder.toClassNodeAddIndividualNode(outputNode, "индивид" + "№" + (i + 1));
                    nodeQueue.offer(tempNode);
                }
            }

            /* Обработка, если вытащенный узел принадлежит классу IndividualNode
               Создаем для индивида от 0 до 3 атрибутов + Каждый индивид по умолчанию имеет атрибут ID*/
            if (outputNode instanceof IndividualNode)
            {
                for (int i = 0; i < getRandomNumberUsingInts(0, 4); i++)
                {
                    tempNode = builder.toIndividualNodeAddAttributeNode(outputNode, "атрибут" + "№" + (i + 1), "значение");
                    nodeQueue.offer(tempNode);
                }
            }
        }
        return rootNode;
    }

    public void setResList(List<ClassNode> readyList)
    {
        this.resList = readyList;
    }

    /**
     * Всопогательный метод получения случайного значения типа int
     * в заданном диапазоне [min; max)
     *
     * @param min - левая  граница диапазона (включительно)
     * @param max - правая граница диапазона (не включительно)
     * @return случайное значение из заданного диапазона.
     * */
    private int getRandomNumberUsingInts(int min, int max)
    {
        Random random = new Random();

        return random.ints(min, max)    //создание бесконечного потока интов в заданном диапазоне
                .findFirst()            //выбираем первый элемент
                .getAsInt();            //получаем его как тип int
    }
}
