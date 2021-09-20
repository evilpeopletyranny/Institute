package com.ngtu.sdp.laboratory_work2;

import com.ngtu.sdp.laboratory_work2.builder.Builder;
import com.ngtu.sdp.laboratory_work2.builder.Director;
import com.ngtu.sdp.laboratory_work2.nodes.ContainerNode;
import com.ngtu.sdp.laboratory_work2.printers.GraphPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 * <p>
 * Реализация второй лабораторной работы по диспилине: Шаблоны проектирования ПО
 * Вариант №8
 * <p>
 * Текст задания: https://github.com/progerSapog/Software-design-patterns-2-course-2-semestr
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 * @author Valerii Sukhorukov    19-IVT-3 (github: https://github.com/Valery-S    )
 * @author Vyacheslav Mostashov  19-IVT-3 (github: https://github.com/Vyacheslav-M)
 * @release: 27.04.21
 * @last_update: 27.04.21
 */
@Component("app")
@Scope("singleton")
public class App {
    //Dependency Injection
    @Autowired
    private GraphPrinter graphPrinter;

    @Autowired
    private Builder GraphBuilder;

    /**
     * Точка входа в программу
     */
    public static void main(String[] args) {
        //Получение контекста из XML файла
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //Получение экземпляра класса App
        App app = context.getBean("app", App.class);

        //Получаем бин директора над билдером
        Director director = context.getBean("director", Director.class);

        //Построение графовой структуры
        Optional<ContainerNode> nodeOpt = director.build(app.GraphBuilder);

        //Если обретка не пуста, то выводим граф
        nodeOpt.ifPresent(containerNode -> app.graphPrinter.print(containerNode));

        //закрытие контекста
        context.close();
    }
}