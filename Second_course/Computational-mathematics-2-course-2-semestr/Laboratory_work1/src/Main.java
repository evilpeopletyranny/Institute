import equation.NoLinearThirdDegreeEquation;
import equation_solution_strategy.*;
import validator.ResponseValidator;
import validator.Validator;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация первой лабораторной работы по диспилине: Вычислительная математика
 *
 * Текст задания:
 *  Решить нелинейное уравнение с одним неизвестным с использованием четырех методов
 *  (метод биссекции, метод хорд, метод Ньютона, метод простой итерации). Задание по вариантам.
 *  Номер варианта – номер студента в списке группы. ε=0.001
 *
 * @release: 27.02.21
 * @last_update: 27.02.21
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
        System.out.println("\t\t\t\tЛабораторная работа №1 <<" + PURPLE + "Решение нелинейного уравнения" +
                RESET + ">>");

        //Создаем переменную для хранения ур-ия
        //Открываем поток ввода
        NoLinearThirdDegreeEquation equation = new NoLinearThirdDegreeEquation();
        Scanner scanner = new Scanner(System.in);

/////////////////// ТЕСТ - УДОЛИ /////////////////////////
        double[] coefficients = {2.0 , (-3.0), (-12.0), (-5.0)};  //с практики

//        double[] coefficients = {1.0, (-3.0), 9.0, (-8.0)};       //Вар 1
//        double[] coefficients = {1.0, (-3.0), 6.0, (-5.0)};       //Вар 2
//        double[] coefficients = {1.0, (-3.0), 6.0, 3.0};          //Вар 3
//        double[] coefficients = {1.0, (-0.1), (-0.4), (-1.5)};    //Вар 4
//        double[] coefficients = {1.0, 0.1, 0.4, (-1.2)};          //Вар 15
/////////////////// ТЕСТ - УДОЛИ /////////////////////////

        System.out.println("Программа для решения нелинейных уравнений 3ей степени.");
        System.out.println("\tОбщий вид таких уравнений: ");
        System.out.println("\t\ta*x^3 + b*x^2 + c*x + d = 0");
        System.out.println();

        System.out.print("Введите точность ответа (epsilon): ");
        double epsilon = scanner.nextDouble();
        System.out.println();

        //Ввод коэффициентов уравнения
//        double[] coefficients = new double[4];
//        System.out.print("Введите коэффициент a: ");
//        coefficients[0] = scanner.nextDouble();
//        System.out.print("Введите коэффициент b: ");
//        coefficients[1] = scanner.nextDouble();
//        System.out.print("Введите коэффициент c: ");
//        coefficients[2] = scanner.nextDouble();
//        System.out.print("Введите коэффициент d: ");
//        coefficients[3] = scanner.nextDouble();
//        System.out.println();

        //Запись введенных коэффициентов у равнение
        equation.setCoefficients(coefficients);

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
            System.out.println("\t1. Метод биссекций");
            System.out.println("\t2. Метод хорд");
            System.out.println("\t3. Метод Ньютона");
            System.out.println("\t4. Метод простых итераций");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            switch (ch)
            {
                case ("1") -> strategy = new BisectionSolution();
                case ("2") -> strategy = new ChordSolution();
                case ("3") -> strategy = new NewtonSolution();
                case ("4") -> strategy = new SimpleIterationSolution();
                case ("q") -> {
                                   System.out.println(RED + "Завершение работы..." + RESET);
                                   System.exit(0);
                              }
                default    -> System.out.println(RED + "Неверный ввод!" + RESET);
            }

            //Создание объекта класса валидатор и установка
            //значения для сравнения (эпсилон)
            Validator validator = ResponseValidator.getInstance();
//            validator.setParameter(0.2);
//            validator.setParameter(0.001);
            validator.setParameter(epsilon);

            //Засекаем время до начала решения
            double start = System.currentTimeMillis();

            assert strategy != null;

            //Засекаем время после конца решения
            double end   = System.currentTimeMillis();
            //Получаем список реше
            List<Double> resultLst = strategy.getSolution(equation, validator);

            System.out.print(CYAN + "Значения функции при найденных ответах:\n "
                    + RESET);
            for (int i = 0; i < resultLst.size(); i++)
            {
                System.out.printf("y" + (i + 1) + ": %.5f", equation.getValueAtX(resultLst.get(i)));
                System.out.print("; ");
            }
            System.out.println();

            //Выводим получившиеся ответы
            System.out.print(RED + "Ответ: " + RESET);
            for (int i = 0; i < resultLst.size(); i++)
            {
                System.out.printf("x" + (i + 1) + ": %.5f", resultLst.get(i));
                System.out.print("; ");
            }
            System.out.println();

            //Выводим затраченное время для данного решения
            System.out.println("Затраченное время: " + (end - start)/1000.0 + " секунд\n");
        }
    }
}
