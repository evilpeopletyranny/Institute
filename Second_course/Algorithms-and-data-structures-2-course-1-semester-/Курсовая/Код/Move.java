package checkers;

import java.util.Vector;

/**
 * Класс 'ходов'.
 * */
public class Move
{
    int initialRow;    //начальная строка хода
    int initialCol;    //начальная колона хода
    int finalRow;      //конечная  строка хода
    int finalCol;      //конечная  колона хода

    /**
     * Конструктор с параметрами.
     * Создает объект - ход с переданными параметрами.
     *
     * @param r1, c1 - координаты начала хода
     *
     * @param r2, c2 - координаты конца  хода
     * */
    Move(int r1, int c1, int r2, int c2)
    {
        this.initialRow = r1;
        this.initialCol = c1;
        this.finalRow = r2;
        this.finalCol = c2;
    }

    /**
     * Проверка на соответсвие данного хода переданному ходу.
     *
     *
     * */
    public boolean conformity(Move move)
    {
        return this.initialRow == move.initialRow
                && this.initialCol == move.initialCol
                && this.finalRow == move.finalRow
                && this.finalCol == move.finalCol;
    }

    /**
     * Проверка: есть ли данный ход в
     * списке возможных ходов.
     *
     * @param moves - список возможных ходов.
     * */
    public boolean existsInVector(Vector<Move> moves)
    {
        for (int i = 0; i<moves.size(); i++)
        {
            if (this.conformity(moves.elementAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Вывод информации о ходе на экран.
     * */
    public void display(){
        System.out.print("(" + (this.initialRow + 1) + "," + (this.initialCol + 1) + ") -->"
                      + " ("+ (this.finalRow + 1) + ", " + (this.finalCol + 1) + ")");
    }
}
