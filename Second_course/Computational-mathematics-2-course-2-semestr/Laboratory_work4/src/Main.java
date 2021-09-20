import function.TrigonometricFunction;
import equation_solution_strategy.*;

import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод main.
 * Язык: java
 *
 * Реализация четвертой лабораторной работы по диспилине:
 *     Вычислительная математика
 *
 * Текст задания:
 *  Вычислить интеграл по формулам центральных (средних) прямоугольников,
 *  трапеций и формуле Симпсона, при n=8 и n=20; оценить погрешность результата.
 *
 * @release: 01.04.21
 * @last_update: 01.04.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main
{
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String PURPLE = "\u001B[35m";

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\tЛабораторная работа №4 <<" + PURPLE + "Численное интегрирование функций" +
                RESET + ">>");

        //Создаем объект функцию
        TrigonometricFunction function = new TrigonometricFunction();

        //Открываем поток ввода
        Scanner scanner = new Scanner(System.in);

        System.out.println("Программа для вычисления интеграла функций методами средний " +
                "прямоугольников, трапеций и методом Симпсона.");
        System.out.println("\tФункция: cos(x^2)/(x+1)");
        System.out.println();

        //Ввод нижней и верхней границы интегрирования
        System.out.print("Введите нижнию  границу интегрирования: ");
        double a = scanner.nextDouble();

        System.out.print("Введите верхнюю границу интегрирования: ");
        double b = scanner.nextDouble();

        //Ввод кол-во итераций
        System.out.print("Введите кол-во итервалов разбиения (n): ");
        double numberOfIterations = scanner.nextDouble();
        System.out.println();

        //Создание ссылки на объект, реализующий интерфейс
        //SolutionStrategy
        SolutionStrategy strategy = null;

        //Переменная для хранения результата ввода
        String ch;

        //Сброс потока ввода
        ch = scanner.nextLine();

        //Выбор стратегии решения
        while (!ch.equals("q"))
        {
            System.out.println("Выберите метод для решения уранвения:");
            System.out.println("\t1. Метод средних прямоугольников");
            System.out.println("\t2. Метод трапеций");
            System.out.println("\t3. Метод Симпсона");
            System.out.println();
            System.out.println("\t#. Изменить кол-во итервалов разбиения (n): ");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            switch (ch)
            {
                case ("1") -> strategy = new MediumRectangleMethod();
                case ("2") -> strategy = new TrapeziumMethod();
                case ("3") -> strategy = new SimpsonMethod();
                case ("#") -> {
                                   System.out.print("Введите кол-во итервалов разбиения (n): ");
                                   numberOfIterations = scanner.nextInt();
                                   scanner.nextLine();
                                   continue;
                              }
                case ("q") -> {
                                   System.out.println(RED + "Завершение работы..." + RESET);
                                   System.exit(0);
                              }
                default    -> System.out.println(RED + "Неверный ввод!" + RESET);
            }

            //Получаем значение интеграла
            assert strategy != null;
            double res = strategy.getSolution(function, a, b, numberOfIterations);

            //Получаем значение погрешности
            double error = strategy.getError(function, a, b, numberOfIterations);

            System.out.print("Значение интеграла:   ");
            System.out.printf("%.8f" + "\n", res);
            System.out.print("Значение погрешности: " + RED);
            System.out.printf("%.8f" + RESET + "\n\n", error);
        }
    }
}
