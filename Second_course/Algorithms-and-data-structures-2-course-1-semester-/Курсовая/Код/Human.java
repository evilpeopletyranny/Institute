package checkers;

import java.util.Vector;

/**
 * Класс, реализующий логику игры человека.
 * */
public class Human {

    /**
     *  Сделать слудеющий ход для белых шашек.
     * */
    public static void makeNextWhiteMoves()
    {
        while(true)
        {
            //Получение координат хода
            Move move = UserInteractions.getNextMove();

            //Проверка правильности хода
            if (CheckValidMoveForWhiteHuman(move.initialRow, move.initialCol,
                move.finalRow, move.finalCol))
            {
                break;
            }
        }
    }

    private static void printWarning(Vector<Move> vector)
    {
        UserInteractions.PrintSeparator('-');

        System.out.println("Существует вынужденный ход!!!");
        System.out.println("Есть слудеющие варинты ходов.");
        for (int i=0; i < vector.size(); i++)
        {
            System.out.print((i+1) + ". ");
            System.out.print("(r1: " + (vector.elementAt(i).initialRow + 1)       + ", ");
            System.out.print("c1: " + (vector.elementAt(i).initialCol + 1)        + ")" );
            System.out.print("------> (r2: " + (vector.elementAt(i).finalRow + 1) + ", ");
            System.out.println("c2: " + (vector.elementAt(i).finalCol + 1)        + ")" );
        }

        UserInteractions.PrintSeparator('-');
    }

    /**
     * Проверка правильности хода для белых шашек.
     *
     * @param r1, c1 - координаты начала хода
     *
     * @param r2, c2 - координаты конца  хода
     * */
    private static boolean CheckValidMoveForWhiteHuman(int r1, int c1, int r2, int c2) 
    {
        // выбор и проверка правильности хода
        if (
                Game.board.cell[r1][c1].equals(CellContents.inaccessible)   ||
                !(Game.board.cell[r1][c1].equals(CellContents.white) ||  Game.board.cell[r1][c1].equals(CellContents.whiteKing))
                || !Game.board.cell[r2][c2].equals(CellContents.empty)
            )
        {
            UserInteractions.PrintSeparator('-');
            System.out.println("Невозможный ход!");
            UserInteractions.PrintSeparator('-');
            return false;
        }       
        
        //Проверка на вынужденые ходы
        Vector<Move> forcedMovesAtR1C1 = White.ObtainForcedMovesForWhite(r1,c1,Game.board);        
        
        //Если есть вынужденные ходы
        if (!forcedMovesAtR1C1.isEmpty())
        {   
            Move move = new Move(r1,c1,r2,c2);

            // проверка: является ли ход вынужденным
            if (move.existsInVector(forcedMovesAtR1C1))
            {  
                //Проверка на съедание
                while (true)
                {
                    //съедание черной шашки
                    Game.board.CaptureBlackPiece(r1,c1,r2,c2);

                    //запись перемещения
                    r1 = r2;
                    c1 = c2;
                    
                    //вычисление след. позиций в которых необходимо съесть
                    Vector<Move> furtherCapture = White.ObtainForcedMovesForWhite(r1, c1, Game.board);

                    //дальше некого есть
                    if (furtherCapture.isEmpty()) {
                        break;
                    }
                    
                    //Предупрежедение о том, что есть ещё необходимость
                    //съесть шашки, просьба поторного ввода координат
                    boolean incorrectOption = true;
                    while (incorrectOption) 
                    {
                        printWarning(furtherCapture);
                        UserInteractions.PrintSeparator('-');
                            
                        //запись хода
                        Move furtherMove = UserInteractions.TakeUserInput(r1,c1);

                        //проверка на верность хода
                        if (furtherMove.existsInVector(furtherCapture)) {
                            // обновление координ
                            r2 = furtherMove.finalRow;
                            c2 = furtherMove.finalCol;                            
                            incorrectOption = false;
                        }                        
                    }
                }

                return true;
            }
            else 
            {
                printWarning(forcedMovesAtR1C1);

                UserInteractions.PrintSeparator('-');
                return false;
            }
        }
        
        // если принудительных ходов нет
        // расчет всех возможных ходов для белых шашек на данной доске
        Vector<Move> forcedMoves = White.CalculateAllForcedMovesForWhite(Game.board);

        // если нет возможных ходов для белых шашек
        if (forcedMoves.isEmpty())
        {
            // ход
            if (r2 - r1 == 1 && Math.abs(c2 - c1) == 1)
            {
                Game.board.MakeMove(r1, c1, r2, c2);
                return true;
            }

            // ход для короля
            else if (Game.board.cell[r1][c1].equals(CellContents.whiteKing))
            {
                if (r2 - r1 == -1 && Math.abs(c2 - c1) == 1)
                {
                    Game.board.MakeMove(r1, c1, r2, c2);
                    return true;
                }
            }

            else{
                UserInteractions.PrintSeparator('-');
                System.out.println("Проверте правильность хода!\n");
                UserInteractions.PrintSeparator('-');
                return false;
            }
        }
        else
        {
            printWarning(forcedMoves);
            return false;
        }

        return false;
    }
}
