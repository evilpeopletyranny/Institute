import solution_strategy.*;

import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация пятой лабораторной работы по диспилине: Вычислительная математика
 * Вариант №15
 *
 * Текст задания:
 *  Найти первую и вторую производную функции в точках х, заданных
 *  таблицей, используя интерполяционные многочлены Ньютона. Сравнить со
 *  значениями производных, вычисленными по формулам, основанным на
 *  интерполировании многочленом Лагранжа (вычисление производных через
 *  значения функций).
 *
 *            x        y
 *          3.50    33.1154
 *          3.55    34.8133
 *          3.60    36.5982
 *          3.65    38.4747
 *          3.70    40.4473
 *          3.75    42.5211
 *          3.80    44.7012
 *          3.85    46.9931
 *          3.90    49.4024
 *          3.95    51.9354
 *          4.00    54.5982
 *          4.05    57.3975
 *          4.10    60.3403
 *          4.15    63.4340
 *          4.20    66.6863
 *
 *
 * @release: -     03.05.21
 * @last_update: - 03.05.21
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Main
{
    //Константы для хранения последовательностей для
    //изменения цвета текста в консоли
    public static final String RESET = "\u001B[0m";
    public static final String PURPLE = "\u001B[35m";
    public static final String RED = "\u001B[31m";

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\tЛабораторная работа №5 <<" + PURPLE + "Численное дифференцирование функций" +
                " Ньютона и многочленом Лагранжа" + RESET + ">>");

        //Открытие потока ввода
        Scanner scanner = new Scanner(System.in);

        //Таблица значений Вариант №15
        double[][] coordinates = {{3.50, 33.1154},
                                  {3.55, 34.8133},
                                  {3.60, 36.5982},
                                  {3.65, 38.4747},
                                  {3.70, 40.4473},
                                  {3.75, 42.5211},
                                  {3.80, 44.7012},
                                  {3.85, 46.9931},
                                  {3.90, 49.4024},
                                  {3.95, 51.9354},
                                  {4.00, 54.5982},
                                  {4.05, 57.3975},
                                  {4.10, 60.3403},
                                  {4.15, 63.4340},
                                  {4.20, 66.6863}};

        //Создание ссылки на объект, реализующий интерфейс
        //SolutionStrategy
        SolutionStrategy strategy = null;

        //Переменная для хранения результата ввода
        String ch = "";

        double[][] firstDerivative;
        double[][] secondDerivative;

        //Выбор стратегии решения
        while (!ch.equals("q"))
        {
            System.out.println("Выберите способ нахождения производной:");
            System.out.println("\t1. При помощи многочлена Ньютона");
            System.out.println("\t2. При помощи многочлена Лагранжа");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            //Ввод с повторением
            switch (ch)
            {
                case ("1") -> strategy = new NewtonSolution();
                case ("2") -> strategy = new LagrangianSolution();
                case ("3") -> strategy = new DifferenceForm();
                case ("q") ->
                        {
                            System.out.println(RED + "Завершение работы..." + RESET);
                            System.exit(0);
                        }
                default -> System.out.println(RED + "Неверный ввод!" + RESET);
            }

            assert strategy != null;
            firstDerivative  = strategy.getFirstDerivative(coordinates);
            secondDerivative = strategy.getSecondDerivative(coordinates);

            System.out.println("Значения первой производной: ");
            for (double[] doubles : firstDerivative)
            {
                System.out.printf("%.2f", doubles[0]);
                System.out.print("   ");
                System.out.printf("%.4f", doubles[1]);
                System.out.println();
            }

            System.out.println();
            System.out.println();

            System.out.println("Значения второй производной: ");
            for (double[] doubles : secondDerivative)
            {
                System.out.printf("%.2f", doubles[0]);
                System.out.print("   ");
                System.out.printf("%.4f", doubles[1]);
                System.out.println();
            }
        }
    }
}