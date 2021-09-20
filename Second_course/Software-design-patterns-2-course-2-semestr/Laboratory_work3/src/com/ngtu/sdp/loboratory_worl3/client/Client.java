package com.ngtu.sdp.loboratory_worl3.client;

import com.ngtu.sdp.loboratory_worl3.query.Query;

import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Класс клиент - генерирует запросы на
 * создания графов в отдельном потоке.
 * */
public class Client implements Runnable
{
    private final Queue<Query> queue;           //ссылка на очредь - раздялемый ресурс между потоками
    private final Semaphore genSemaphore;       //ссылка на семафор генератора
    private final Semaphore clientSemaphore;    //ссылка на семафор клиента

    /**
     * Конструктор с параметрами.
     *
     * @param clientSemaphore - ссылка на семафор клиента
     * @param genSemaphore    - ссылка на семафор генератора
     * @param queue           - ссылка на очередь
     * */
    public Client(Queue<Query> queue, Semaphore clientSemaphore, Semaphore genSemaphore)
    {
        this.queue = queue;
        this.clientSemaphore = clientSemaphore;
        this.genSemaphore = genSemaphore;
    }

    /**
     * Метод run() интефейса Runnable - данный код выполняется
     * в дополнительном потоке.
     * Алгоритм задачи о спящем брадобрее для клиента
     * */
    @Override
    public void run()
    {
        //генерация 25 запросов
        for (int i = 1; i <= 10; i++)
        {
            Query query = new Query("create graph Graph" + i);
            try
            {
                //Опустили mutex на очередь
                synchronized (queue)
                {
                    System.out.println("Получение запроса на создание " + query.getGraphName());

                    //Добавили запрос в очередь, освободили семафор клиента
                    queue.add(query);
                    clientSemaphore.release();
                }
                //Подняли mutex, заняли семафор генератора
                genSemaphore.acquire();
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
