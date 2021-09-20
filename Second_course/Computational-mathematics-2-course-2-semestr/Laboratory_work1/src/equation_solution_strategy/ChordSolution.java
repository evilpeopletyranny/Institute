package equation_solution_strategy;

import equation.Equation;
import validator.Validator;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение методом хорд.
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class ChordSolution implements SolutionStrategy
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

            //Конец a интервала [a;b] неподвижен
            if (equation.getValueAtX(interval.get(0)) * equation.getSecDerivativeAtX(interval.get(1)) > 0)
            {
                //Поскольку a не подвижен, то изменять будет b
                xI = interval.get(1);

                //Изначально задается такое значение, которое не пройдет условие валидатора
                prevValue = (-Double.MAX_VALUE);

                //Пока не сработает условие валидатора
                //В предыдущее значение записываем текущее значение Xi
                //Получаем новое значение Xi+1 = xi - (f(xi)*(xi-a)/(f(xi)-f(a)))
                while (!validator.isValid(prevValue, equation.getValueAtX((xI)))) {
                    prevValue = xI;
                    xI = prevValue - ((equation.getValueAtX(prevValue) * (prevValue - interval.get(0))) /
                            (equation.getValueAtX(prevValue) - equation.getValueAtX(interval.get(0))));
                }
            }
            //Конец b интервала [a;b] неподвижен
            else
            {
                //Поскольку b не подвижен, то изменять будет a
                xI = interval.get(0);

                //Изначально задается такое значение, которое не пройдет условие валидатора
                prevValue = (-Double.MAX_VALUE);

                //Пока не сработает условие валидатора
                //В предыдущее значение записываем текущее значение Xi
                //Получаем новое значение Xi+1 = xi - (f(xi)*(xi-b)/(f(xi)-f(b)))
                while (!validator.isValid(equation.getValueAtX(prevValue), equation.getValueAtX((xI))))
                {
                    prevValue = xI;
                    xI = prevValue - ((equation.getValueAtX(prevValue) * (prevValue - interval.get(1))) /
                            (equation.getValueAtX(prevValue) - equation.getValueAtX(interval.get(1))));
                }
            }

            //Записываем полученный ответ в результирующий список
            resList.add(xI);
        }
        return resList;
    }

    /**
     * Конструктор без параметров.
     * */
    public ChordSolution()
    {
    }
}
