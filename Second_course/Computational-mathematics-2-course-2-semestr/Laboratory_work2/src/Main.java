import equation.SystemOfThreeEquations;
import equation_solution_strategy.*;
import validator.ResponseValidator;
import validator.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация второй лабораторной работы по диспилине: Вычислительная математика
 *
 * Текст задания:
 *  Решить систему линейных уравнений методом Гаусса, итерационным методом и методом Гаусса-Зейделя.
 *  При необходимости преобразовать систему к диагонально преобладающему виду. Сделать оценку количества итераций
 *  для итерационных методов, сравнить. Задание по вариантам. Номер варианта – номер студента в списке группы.
 *  ε=0.001
 *
 * Ур-ие:
 *  1.6x1  + 0.12x2 + 0.57x3 = 0.18
 *  0.38x1 + 0.25x2 - 54x3   = 0.63
 *  0.28x1 + 0.46x2 - 1.12x3 = 0.88
 *
 * WARNING!!!
 *   Прогрмма не имеет системы ввода коэфициентов системы ур-ий, т.к.
 *   предназначенна для решения только 15 Варианта ЛР №2
 *
 * @release: -
 * @last_update: -
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
    public static final String CYAN = "\u001B[36m";

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\tЛабораторная работа №2 <<" + PURPLE + "Решение системы линейных уравнений " +
                "итерационным методом и методом Гаусса-Зейделя" +
                RESET + ">>");

        //Создаем переменную для хранения ур-ия
        //Открываем поток ввода
        SystemOfThreeEquations system = new SystemOfThreeEquations();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Программа для решения системы 3ёх нелинейных уравнений.");
        System.out.println("\tОбщий вид системы таких уравнений: ");
        System.out.println("\t\ta11 * x1 + a12 * x2 + a13 * x3 = b1");
        System.out.println("\t\ta21 * x1 + a22 * x2 + a23 * x3 = b2");
        System.out.println("\t\ta31 * x1 + a32 * x2 + a33 * x3 = b3");
        System.out.println();

        System.out.print("Введите точность ответа (epsilon): ");
        double epsilon = scanner.nextDouble();
        System.out.println();
        scanner.nextLine();

        //Изначально ур-ие имеет вид:
        //  1.6 * x1  + 0.12 * x2 + 0.57 * x3 = 0.18
        //  0.38 * x1 + 0.25 * x2 - 54.0 * x3 = 0.63
        //  0.28 * x1 + 0.46 * x2 - 1.12 * x3 = 0.88
        //
        //Однако при записи в систему строки 2 и 3 переставлены
        //В противном случае из-за сильно выделяющегося коэфициента -54.0
        //система не сходится
        double[][] coefficients = {{1.6,  0.12,  0.57},
                                   {0.28, 0.46, -1.12},
                                   {0.38, 0.25, -54.0}};
        double[]  vectorB        = {0.18, 0.88, 0.63};

        //Запись введенных коэффициентов в систему
        system.setCoefficients(coefficients);

        //запись вектора B в систему
        system.setVectorB(vectorB);

        //Создание ссылки на объект, реализующий интерфейс
        //SolutionStrategy
        SolutionStrategy strategy = null;

        //Переменная для хранения результата ввода
        String ch = "";

        //Выбор стратегии решения
        while (!ch.equals("q"))
        {
            System.out.println("Выберите метод для решения уранвения:");
            System.out.println("\t1. Метод Гаусса");
            System.out.println("\t2. Метод Простой итерации");
            System.out.println("\t3. Метод Гаусса-Зейделся");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            switch (ch)
            {
                case ("1") -> strategy = new GaussSolution();
                case ("2") -> strategy = new SimpleIterationSolution();
                case ("3") -> strategy = new GaussSeidelSolution();
                case ("q") -> {
                                   System.out.println(RED + "Завершение работы..." + RESET);
                                   System.exit(0);
                              }
                default    -> System.out.println(RED + "Неверный ввод!" + RESET);
            }

            //Создание объекта класса валидатор и установка
            //значения для сравнения (эпсилон)
            Validator validator = ResponseValidator.getInstance();
            validator.setParameter(epsilon);

            //Засекаем время до начала решения
            double start = System.currentTimeMillis();

            assert strategy != null;

            //Засекаем время после конца решения
            double end   = System.currentTimeMillis();
            //Получаем список реше
            double[] resArr = strategy.getSolution(system, validator);

            //Выводим получившиеся ответы
            System.out.print(RED + "Ответ: " + RESET);
            for (int i = 0; i < resArr.length - 1; i++)
            {
                System.out.printf("x" + (i + 1) + ": %.5f", resArr[i]);
                System.out.print("; ");
            }
            System.out.println();

            //Выводим затраченное время для данного решения
            System.out.println("Затраченное время: " + CYAN + (end - start)/1000.0 + RESET + " секунд");
            System.out.println("Кол-во итераций:   " + CYAN + (int)resArr[3] + RESET + " итераций\n");
        }
    }
}
