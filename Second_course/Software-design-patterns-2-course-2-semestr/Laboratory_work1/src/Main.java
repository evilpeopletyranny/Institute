import exceptions.ChildNodeException;
import nodes.ClassNode;
import builder.GraphBuilder;
import printers.GraphPrinter;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 * <p>
 * Реализация первой лабораторной работы по диспилине: Шаблоны проектирования ПО
 * Вариант №8
 * <p>
 * Текст задания: https://github.com/progerSapog/Software-design-patterns-2-course-2-semestr
 *
 * @author Vladislav Sapozhnikov 19-IVT-3 (github: https://github.com/progerSapog )
 * @author Valerii Sukhorukov    19-IVT-3 (github: https://github.com/Valery-S    )
 * @author Vyacheslav Mostashov  19-IVT-3 (github: https://github.com/Vyacheslav-M)
 * @release: 10.03.21
 * @last_update: 18.03.21
 */
public class Main {
    public static void main(String[] args) {
        try {
            //Вызов метода для создания графовой структуры
            ClassNode classNode = GraphBuilder.createNewGraph();

            //Получение ссылки на едиственный экземпляр класса GraphPrinter
            GraphPrinter graphPrinter = GraphPrinter.getInstance();

            //Вывод информации по графу в консоль
            graphPrinter.print(classNode);

            System.out.println("\n\t\t\t\t\u001B[31m Конец работы...\u001B[0m");
        }
        //Обработка исключений реализованных в рамке данной работы
        catch (ChildNodeException e) {
            e.printStackTrace();
        }
    }
}