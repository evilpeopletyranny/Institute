package equation_solution_strategy;

import equation.Equation;
import validator.Validator;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение методом Простой итерации.
 * Реализует интерфейс SolutionStrategy
 *
 * WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING!
 *    Подобранная функция: x - 0.1 * (x^3+0.1x^2+0.4x+1.2)
 *    подходит только для ур-ия Варианта 15
 * WARNING! WARNING! WARNING! WARNING! WARNING! WARNING! WARNING!
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class SimpleIterationSolution implements SolutionStrategy
{
    /**
     * Метод для получения решений методом хорд.
     *
     * @param equation  - ур-ие, которое необходимо решить
     * @param validator - валидатор, с заданным параметром проверки
     * @return список значений, являющимися решениями данного уравнения.
     * */
    @Override
    public List<Double> getSolution(Equation equation, Validator validator)
    {
        //список, в который будут заноится ответы
        List<Double> resList = new LinkedList<>();

        //Значение, для хранения X(i-1) - ответ полученный на пред. итерации
        //Изначально задается такое значение, которое не пройдет условие валидатора
        double prevValue;

        //Переменная для хранения значения Xi
        double xI;

        //Получение списка интервалов монотонности, которые содержат
        //решения
        List<List<Double>> intervals = equation.getIntervalsOfMonotony();

        //Проход по каждому интервалу [a;b]
        for (List<Double> interval: intervals)
        {
            //Инициализируем x0 значением из промежутка [a;b]
            //в данном случае [0;1]
            //Math.random() возвращает числа от 0 до 1, что
            //соотвествует данному промежутку
            prevValue = (interval.get(0) + interval.get(1)) / 2;

            for (;;)
            {
                //Данная сжимающая функция подобрана только для варианта 15
                //  g(x) = x - b * f(x), где b = 0.1
                xI = prevValue - 0.1*(Math.pow(prevValue, 3) + 0.1*Math.pow(prevValue, 2) + 0.4*prevValue -1.2);

                //Проверка критерием остановки
                if (validator.isValid(equation.getValueAtX(prevValue), equation.getValueAtX((xI)))) break;
                prevValue = xI;
            }
            resList.add(xI);
        }
        return resList;
    }

    /**
     * Конструктор без параметров.
     * */
    public SimpleIterationSolution()
    {
    }
}
