package com.ngtu.sdp.loboratory_worl3;

import com.ngtu.sdp.loboratory_worl3.builder.GraphBuilder;
import com.ngtu.sdp.loboratory_worl3.client.Client;
import com.ngtu.sdp.loboratory_worl3.generator.Generator;
import com.ngtu.sdp.loboratory_worl3.generator.GraphGenerator;
import com.ngtu.sdp.loboratory_worl3.nodes.ClassNode;
import com.ngtu.sdp.loboratory_worl3.printers.GraphPrinter;
import com.ngtu.sdp.loboratory_worl3.query.Query;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class App
{
    public static void main(String[] args) throws InterruptedException
    {
        //Принтер графа
        GraphPrinter printer = new GraphPrinter();

        //Общий список для записи готовых графов
        List<ClassNode> resList = new LinkedList<>();

        //Общий ресурс - очередь запросов
        Queue<Query> queue = new ArrayDeque<>();

        //семафоры для генераторов и клиента
        Semaphore genSemaphore = new Semaphore(3);
        Semaphore clientSemaphore = new Semaphore(1);

        //Передали клиенту общую очередь и семафоры
        Client client1 = new Client(queue, clientSemaphore, genSemaphore);

        Generator generator1 = new GraphGenerator(genSemaphore, clientSemaphore, "Генератор 1", queue, new GraphBuilder());
        Generator generator2 = new GraphGenerator(genSemaphore, clientSemaphore, "Генератор 2", queue, new GraphBuilder());
        Generator generator3 = new GraphGenerator(genSemaphore, clientSemaphore, "Генератор 3", queue, new GraphBuilder());

        //Передаем генераторам ссылку на общий список, в который будут записываться ответы
        generator1.setResList(resList);
        generator2.setResList(resList);
        generator3.setResList(resList);

        //создание потока клиента
        Thread clientThread1 = new Thread(client1);

        //создание потоков для генераторов
        Thread genThread1 = new Thread((GraphGenerator) generator1);
        Thread genThread2 = new Thread((GraphGenerator) generator2);
        Thread genThread3 = new Thread((GraphGenerator) generator3);


        //Запуск потока клиента
        clientThread1.start();

        Thread.sleep(1500);

        //Запуск потоков генераторов
        genThread1.start();
        genThread2.start();
        genThread3.start();

        //текущий поток ждет завершение всех потоков генераторов
        genThread1.join();
        genThread2.join();
        genThread3.join();

        Thread.sleep(2500);

        //Вывод готовых графов
        for (ClassNode graph : resList)
        {
            printer.print(graph);
        }
    }
}
