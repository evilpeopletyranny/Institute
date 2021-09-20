package checkers;

/**
 * Класс, содержащий точку входа в программу - метод  main.
 * Язык: java
 *
 * Реализация курсовой работы по диспилине: Алгоритмы и структуры данных. Вариант№17.
 * Программа, играющая в Английские шашки 12х12
 *
 * @release: 12.12.20
 * @last_update: 12.12.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 */
public class Checkers
{
    /**
     * Точка входа в программу - ф-ия main
     *
     * @param args - аргументы запуска
     * */
    public static void main(String[] args)
    {
        //если передан 1 аргумент: -h или --help
        //то вывод справки о программе
        if (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help")))
        {
            Game.printGameReference();
        }
        //если передан 1 аргумент: -start
        //то запуск игры
        else if (args.length == 1 && (args[0].equals("-start")))
        {
            Game game = new Game();
            game.PlayGame();
        }
        //во всех остальных случаях
        //запуска - выводим предупреждение
        else
        {
            Game.printStartError();
        }

    }    
}
