import solution_strategy.*;

import java.util.List;
import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация третьей лабораторной работы по диспилине: Вычислительная математика
 * Вариант №15
 *
 * Текст задания:
 *  1. Вычислить значение функции при данных значениях аргумента, оценить погрешность:
 *    а) используя первую или вторую интерполяционную формулу Ньютона, в зависимости от значения аргумента;
 *    б) с помощью интерполяционного многочлена Лагранжа, используя формулу для равноотстоящих узлов.
 *
 *                                      Значения аргумента
 *      X   |     Y   | № Варианта |  X1   |  X2   |  X3   | X4    | X5    |
 *    0.101 | 1.26183 |      15    | 0.156 | 0.120 | 0.170 | 0.089 | 0.144 |
 *    0.106 | 1.27644 |            |       |       |       |       |       |
 *    0.111 | 1.29122 |            |       |       |       |       |       |
 *    0.116 | 1.30617 |            |       |       |       |       |       |
 *    0.121 | 1.32130 |            |       |       |       |       |       |
 *    0.126 | 1.33660 |            |       |       |       |       |       |
 *    0.131 | 1.35207 |            |       |       |       |       |       |
 *    0.136 | 1.36773 |            |       |       |       |       |       |
 *    0.141 | 1.38357 |            |       |       |       |       |       |
 *    0.146 | 1.39959 |            |       |       |       |       |       |
 *    0.151 | 1.41579 |            |       |       |       |       |       |
 *    0.156 | 1.43102 |            |       |       |       |       |       |
 *    0.161 | 1.45790 |            |       |       |       |       |       |
 *
 *
 *
 *  2. Найти приближенное значение функции при данных значениях аргумента с помощью интерполяционного многочлена
 *     Лагранжа, если функция задана в неравноостоящих узлах таблицы, оценить погрешность
 *
 *                                      Значения аргумента
 *      X  |    Y    | № Варианта |  X1   |  X2   |
 *    0.35 | 2.73951 |      15    | 0.482 | 0.555 |
 *    0.44 | 2.30080 |            |       |       |
 *    0.47 | 1.96864 |            |       |       |
 *    0.51 | 1.78776 |            |       |       |
 *    0.56 | 1.56502 |            |       |       |
 *    0.64 | 1.34310 |            |       |       |
 *
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
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        System.out.println("\t\t\t\tЛабораторная работа №3 <<" + PURPLE + "Интерполирование функции многочленом" +
                " Ньютона и многочленом Лагранжа" + RESET + ">>");

        //Открытие потока ввода
        Scanner scanner = new Scanner(System.in);

        //Координаты точек функции задания 1
        double[][] coordinatesTask1 =
                {{0.101, 1.26183},
                {0.106, 1.27644},
                {0.111, 1.29122},
                {0.116, 1.30617},
                {0.121, 1.32130},
                {0.126, 1.33660},
                {0.131, 1.35207},
                {0.136, 1.36773},
                {0.141, 1.38357},
                {0.146, 1.39959},
                {0.151, 1.41579},
                {0.156, 1.43102},
                {0.161, 1.45790}};

        //Xi в которых необходимо найти значения функции для задания 1
        double[] desiredValuesTask1 = {0.156, 0.120, 0.170, 0.089, 0.144};

////////////////////// ПРИМЕР С ПРАКТИКИ /////////////////////////////////
//        //Координаты точек функции задания 1
//        double[][] coordinatesTask1 = {{0.0, 1.272},
//                {0.2, 4.465},
//                {0.4, 5.644},
//                {0.6, 5.809},
//                {0.8, 3.961},
//                {1.0, 2.101}};
//
//        //Xi в которых необходимо найти значения функции для задания 1
//        double[] desiredValuesTask1 = {0.1, 0.7};
//
////////////////////// ПРИМЕР С ПРАКТИКИ /////////////////////////////////
//
//        double[][] coordinatesTask2 =
//                {{0.15, 2.17},
//                {0.3, 3.63},
//                {0.4, 5.07},
//                {0.55, 7.78}};
//
//
//        //Xi в которых необходимо найти значения функции для задания 2
//        double[] desiredValuesTask2 = {0.67};

////////////////////// ПРИМЕР С ПРАКТИКИ /////////////////////////////////

        //Координаты точек функции задания 2
        double[][] coordinatesTask2 = {{0.35, 2.73951},
                                       {0.44, 2.30080},
                                       {0.47, 1.96864},
                                       {0.51, 1.78776},
                                       {0.56, 1.59502},
                                       {0.64, 1.34310}};

        //Xi в которых необходимо найти значения функции для задания 2
        double[] desiredValuesTask2 = {0.428, 0.555};
        //Создание ссылки на объект, реализующий интерфейс
        //SolutionStrategy
        SolutionStrategy strategy;

        //Переменная для хранения результата ввода
        String ch = "";

        List<Double> resList = null;

        //Выбор стратегии решения
        while (!ch.equals("q"))
        {
            System.out.println("Выберите метод:");
            System.out.println("\t1. Интерполяционная формула Ньютона");
            System.out.println("\t2. Многочлен Лагранжа для равноотстоящих узлов");
            System.out.println("\t3. Многочлен Лагранжа для неравноотстоящих узлов");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            //Ввод с повторением
            switch (ch)
            {
                case ("1") ->
                {
                    printConditions(coordinatesTask1, desiredValuesTask1);
                    System.out.println();
                    System.out.println("\tВычисление приблеженных значений функции при помощи " + CYAN +
                            "\nинтерполяционной формулы Ньютона для равноотстоящих узлов\n" + RESET);

                    strategy = new NewtonInterpolation();
                    resList = strategy.getSolution(coordinatesTask1, desiredValuesTask1);
                }
                case ("2") ->
                {
                    printConditions(coordinatesTask1, desiredValuesTask1);
                    System.out.println();
                    System.out.println("\tВычисление приблеженных значений функции при помощи " + YELLOW +
                            "\nинтерполяционного многочлена Лагранжа для равноотстоящих узлов\n" + RESET);

                    strategy = new LagrangianInterpolationEquidistantNodes();
                    resList = strategy.getSolution(coordinatesTask1, desiredValuesTask1);
                }
                case ("3") ->
                {
                    printConditions(coordinatesTask2, desiredValuesTask2);
                    System.out.println();
                    System.out.println("\tВычисление приблеженных значений функции при помощи " + GREEN +
                            "\nинтерполяционного многочлена Лагранжа для неравноотстоящих узлов\n" + RESET);

                    strategy = new LagrangianInterpolationUnequallyNodes();
                    resList = strategy.getSolution(coordinatesTask2, desiredValuesTask2);
                }
                case ("q") ->
                {
                    System.out.println(RED + "Завершение работы..." + RESET);
                    System.exit(0);
                }
                default -> System.out.println(RED + "Неверный ввод!" + RESET);
            }

            assert resList != null;
            //Выводим получившиеся ответы
            System.out.println();
            System.out.print(RED + "Ответ: " + RESET);
            for (int i = 0; i < resList.size() - 1; i++) {
                System.out.printf("y" + (i + 1) + ": %.5f", resList.get(i));
                System.out.print("; ");
            }
            System.out.printf("y" + (resList.size()) + ": %.5f", resList.get(resList.size() - 1));
            System.out.println();

        }
    }

    /**
     * Метод для вывода таблицы заданных точек и значений Xi, в которых
     * необходимо получить значения.
     *
     * @param coordinates   - двумерный массив значений [0][Xi] [1][Yi]
     * @param desiredValues - массив значений Xi, в которых необходимо найти значений функций
     * */
    public static void printConditions(double[][] coordinates, double[] desiredValues)
    {
        System.out.println("Заданные точки: ");
        System.out.println("     f(x)");
        System.out.println("  Xi  |    Yi");
        for (double[] coordinate : coordinates)
        {
            System.out.printf("%.3f", coordinate[0]);
            System.out.print(" | ");
            System.out.printf("%.5f", coordinate[1]);
            System.out.println();
        }
        System.out.println();

        System.out.println("Точки в которых необходимо найти значения функции: ");
        for (int i = 0; i < desiredValues.length - 1; i++)
        {
            System.out.print("X" + (i + 1) + ": ");
            System.out.printf("%.5f", desiredValues[i]);
            System.out.print(", ");
        }
        System.out.print("X" + (desiredValues.length) + ": ");
        System.out.printf("%.5f", desiredValues[desiredValues.length - 1]);
        System.out.println();
    }
}
