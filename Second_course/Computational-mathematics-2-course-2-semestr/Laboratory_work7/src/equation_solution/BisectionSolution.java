package equation_solution;

import equation.Equation;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс реализующий решение методом биссекций.
 * Реализует интерфейс SolutionStrategy
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * @see SolutionStrategy
 * */
public class BisectionSolution implements SolutionStrategy
{
    /**
     * Метод для получения решений методом бисекций.
     *
     * @param equation  - ур-ие, которое необходимо решить
     * @return список значений, являющимися решениями данного уравнения.
     * */
    @Override
    public List<Double> getSolution(Equation equation)
    {
        //список, в который будут заноится ответы
        List<Double> resList = new LinkedList<>();

        //Переменная для хранения значения Xi
        double xI;

        //Получение списка интервалов монотонности, которые содержат
        //решения
        List<List<Double>> intervals = equation.getIntervals();

        //Проход по каждому интервалу [a;b]
        for (List<Double> interval: intervals)
        {
            //Изначально задается такое значение, которое не пройдет условие валидатора
            xI = 0;

            //Пока не сработает условие валидатора
            //В предыдущее значение записываем текущее значение Xi
            //Получаем новое значение Xi = (a+b)/2
            while (!(Math.abs(equation.getValueAtX(xI)) < 0.001))
            {
                xI = ((interval.get(0)) + interval.get(1)) / 2.0;

                //Если значение функии при Xi и при X=a имеет разные знаки,
                //то меняем b из промежутка [a;b] на Xi
                //иначе меняем a на Xi
                if (equation.getValueAtX(xI) * equation.getValueAtX(interval.get(0)) < 0)
                {
                    interval.set(1, xI);
                }
                else
                {
                    interval.set(0, xI);
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
    public BisectionSolution()
    {
    }
}
