package equation_solution_strategy;

import equation.Equation;
import validator.Validator;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение методом Ньютона (касательных).
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class NewtonSolution implements SolutionStrategy
{
    /**
     * Метод для получения решений методом Ньютона.
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
            //Изначально задается такое значение, которое не пройдет условие валидатора
            prevValue = 0;
            xI = interval.get(1) - 0.001;

            //Пока не сработает условие валидатора
            //В предыдущее значение записываем текущее значение Xi
            //Получаем новое значение Xi+1 = f(xi)/f'(xi)
            while (!validator.isValid(equation.getValueAtX(prevValue), equation.getValueAtX((xI))))
            {
                prevValue = xI;
                xI -= (equation.getValueAtX(prevValue)/equation.getFstDerivativeAtX(prevValue));
            }

            //Записываем полученный ответ в результирующий список
            resList.add(xI);
        }
        return resList;
    }

    /**
     * Конструктор без параметров.
     * */
    public NewtonSolution()
    {
    }
}
