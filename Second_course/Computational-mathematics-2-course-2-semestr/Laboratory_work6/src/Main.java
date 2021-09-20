import function.Function;
import function.FirstFunction;
import function.SecondFunction;
import solution_strategy.*;

import java.util.Scanner;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация шестой лабораторной работы по диспилине: Вычислительная математика
 * Вариант №15
 *
 * Текст задания:
 * Задание 1
 *  Используя метод Эйлера и метод Эйлера с пересчетом, составить
 *  таблицу приближенных значений интеграла дифференциального уравнения
 *  y΄=f(x,y), удовлетворяющего начальным условиям y( на отрезке [a,b];
 *  шаг h=0.1. Все вычисления вести с четырехзначными знаками. Проверить
 *  полученные значения, используя метод Рунге-Кутты 4 порядка
 *
 *  Функция:
 *     y'=x+sin(y/pi)       X ∈ [1.7; 2.7]  y0(1.7) = 5.3
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

        //Создание ссылки на объект, реализующий интерфейс
        //SolutionStrategy
        SolutionStrategy strategy = null;

        //Переменная для хранения результата ввода
        String ch = "";

        double x  = 0.0;
        double xn = 0.0;
        double y  = 0.0;

        Function function = null;

        //Выбор стратегии решения
        while (!ch.equals("q"))
        {
            System.out.println("Выберите способ:");
            System.out.println("\t1. Задание1. Метод Эйлера");
            System.out.println("\t2. Задание1. Метод Эйлера с пересчетом");
            System.out.println("\t3. Задание1. Метод Рунге-Кутта 4 порядка");
            System.out.println();
            System.out.println("\t4. Задание2. Метод Адамса");
            System.out.println("\t5. Задание2. Метод Эйлера с пересчетом");
            System.out.println();
            System.out.println("\tВведите q для выхода");
            System.out.print("Ввод: ");
            ch = scanner.nextLine();
            System.out.println();

            //Ввод с повторением
            switch (ch)
            {
                case ("1"):
                {
                    strategy = new EulerSolution();
                    function = new FirstFunction();
                    x  = 1.7;
                    xn = 2.7;
                    y  = 5.3;
                    break;
                }
                case ("2"):
                {
                    strategy = new EulerRecalculationSolution();
                    function = new FirstFunction();
                    x  = 1.7;
                    xn = 2.7;
                    y  = 5.3;
                    break;
                }
                case ("3"):
                {
                    strategy = new RungeKuttSolution();
                    function = new FirstFunction();
                    x  = 1.7;
                    xn = 2.7;
                    y  = 5.3;
                    break;
                }
                case ("4"):
                {
                    strategy = new AdamsSolution();
                    function = new SecondFunction();
                    x  = 0.0;
                    xn = 1.0;
                    y  = 0;
                    break;
                }
                case ("5"):
                {
                    strategy = new EulerRecalculationSolution();
                    function = new SecondFunction();
                    x  = 0.0;
                    xn = 1.0;
                    y  = 0;
                    break;
                }
                case ("q"):
                        {
                            System.out.println(RED + "Завершение работы..." + RESET);
                            System.exit(0);
                        }
                default:
                {
                    System.out.println(RED + "Неверный ввод!" + RESET);
                    break;
                }
            }

            assert strategy != null;
            strategy.getSolution(function, x, xn, y, 0.1);
            System.out.println();
            System.out.println();
        }
    }
}