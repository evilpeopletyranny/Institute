package checkers;

import java.util.Vector;

/**
 * Класс, реализующий логику ходов
 * черных шашек (игрок - Computer)
 * */
public class Black
{
    static Owner owner;    //поле перечисляемого типа 'игроки'
                           //'хозяин' команды

    /**
     * Метод, отвечающий за ход черных
     * (Computer)
     * */
    public static void Move()
    {
        UserInteractions.PrintSeparator('-');
        System.out.println("\t\t\t\t\t\t\t\t\t" + "\u001B[31m" + "Ход компьютера" + "\u001B[0m");
        UserInteractions.PrintSeparator('-');

        //Компьютер делает ход
        Computer.makeNextBlackMoves();
    }

    /**
     * Метод, проверяющий возможность поедания
     * за черных.
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает список возможных ходов
     *         (с поеданиями) для черных шашек
     * */
    public static Vector<Move> ObtainForcedMovesForBlack(int r, int c, Board board) 
    {        
        Vector<Move> furtherCaptures = new Vector<>();    //список последующих
                                                          //'поеданий'

        //Если в текущей клетке черная шашкка (любая)
        //проверка на возможность поедания обычной шашкой.
        //Проверка проходит функцией ForwardLeftCaptureForBlack
        //и ForwardRightCaptureForBlack
        //которая возращает объет класса Move
        //если нет позможности съесть, то объект Move = null
        if (board.cell[r][c].equals(CellContents.black) || board.cell[r][c].equals(CellContents.blackKing))
        {
            if (ForwardLeftCaptureForBlack(r,c,board)!=null)
                furtherCaptures.add(ForwardLeftCaptureForBlack(r,c,board));
            if (ForwardRightCaptureForBlack(r,c,board)!=null)
                furtherCaptures.add(ForwardRightCaptureForBlack(r,c,board));
        }        

        //для дамок так же проверям возможноть на поедание
        //в 'нестандартные' для обычных шашек направления
        if(board.cell[r][c].equals(CellContents.blackKing))
        {
            if (BackwardLeftCaptureForBlack(r,c,board)!=null)
                furtherCaptures.add(BackwardLeftCaptureForBlack(r,c,board));
            if (BackwardRightCaptureForBlack(r,c,board)!=null)
                furtherCaptures.add(BackwardRightCaptureForBlack(r,c,board));
        }

        return furtherCaptures;
    }

    /**
     * Метод, аккумулирующий все возможные
     * поедания для черных шашек.
     *
     * @param board - текущее положение поля
     *
     * @return список ходов
     * */
    public static Vector<Move> CalculateAllForcedMovesForBlack(Board board) 
    {
        //список будующих поеданий
        Vector<Move> forcedMovesForBlack = new Vector<>();
        
        //проход по доске, игнорируя недоступные клетки
        for(int r = 0; r<Board.rows; r++)
        {
            for(int c = (r%2==0)?0:1; c<Board.cols; c+=2)
            {
                //возможные поедания для обычных шашек
                if(board.cell[r][c].equals(CellContents.black) || board.cell[r][c].equals(CellContents.blackKing))
                {
                    if (r>=2)
                    {
                        //поедание по левой диагонали для черных
                        if (ForwardLeftCaptureForBlack(r,c, board)!=null)
                            forcedMovesForBlack.add(ForwardLeftCaptureForBlack(r,c, board));

                        //поедание по правой диагонали для черных
                        if (ForwardRightCaptureForBlack(r,c, board)!=null)
                            forcedMovesForBlack.add(ForwardRightCaptureForBlack(r,c, board));
                    }                   
                }
                //возможные поедания одля дамок
                if(board.cell[r][c].equals(CellContents.blackKing))
                {
                    if (r<Board.rows-2)
                    {          
                        //поедание по левым диагоналям
                        if (BackwardLeftCaptureForBlack(r,c,board)!=null)
                            forcedMovesForBlack.add(BackwardLeftCaptureForBlack(r,c, board));
                        
                        //поедаиние по правым диагоналям
                        if (BackwardRightCaptureForBlack(r,c,board)!=null)
                            forcedMovesForBlack.add(BackwardRightCaptureForBlack(r,c,board));                        
                    }
                }
            }    
        }
        
        return forcedMovesForBlack;
    }

    /**
     * Метод, отвечающий за ход, если нет вынужденных
     * ходов (поеданий)
     *
     * @param board - текущее состояние поля
     *
     * @return возвращает список перемещений
     * */
    public static Vector<Move> CalculateAllNonForcedMovesForBlack(Board board){
        Vector<Move> allNonForcedMovesForBlack = new Vector<>();

        //проход по доске, игнорируя недоступные клетки
        for(int r = 0; r<Board.rows; r++)
        {
            for(int c = (r%2==0)?0:1; c<Board.cols; c+=2)
            {
                // перемещение вперед для обычной черной шашки
                if( board.cell[r][c].equals(CellContents.black))
                {
                    Move move;

                    move = ForwardLeftForBlack(r, c, board);
                    if(move!=null){
                        allNonForcedMovesForBlack.add(move);   
                    }
                    
                    move = ForwardRightForBlack(r, c, board);
                    if(move!=null){
                        allNonForcedMovesForBlack.add(move);
                    }
                }
                
                //перемещение вперед и назад для черной дамки
                if(board.cell[r][c] == CellContents.blackKing){
                    Move move;

                    move = ForwardLeftForBlack(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForBlack.add(move);
                    }
                    
                    move = ForwardRightForBlack(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForBlack.add(move);
                    }
                    
                    move = BackwardLeftForBlack(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForBlack.add(move);
                    }
                    
                    move = BackwardRightForBlack(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForBlack.add(move);
                    }
                }
            }
        }

        return allNonForcedMovesForBlack;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * влево вперед для черных
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardLeftForBlack(int r, int c, Board board){
        Move forwardLeft = null;

        if(r>=1 && c<Board.cols-1 && board.cell[r-1][c+1] == CellContents.empty)
        {
            forwardLeft = new Move(r,c,r-1, c+1);
        }
        return forwardLeft;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * влево вперед для черных
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardLeftCaptureForBlack(int r, int c, Board board)
    {        
        Move forwardLeftCapture = null;
        
        if(r>=2 && c<Board.cols-2 && (board.cell[r-1][c+1].equals(CellContents.white) ||
                board.cell[r-1][c+1].equals(CellContents.whiteKing)) &&
                board.cell[r-2][c+2].equals(CellContents.empty))
        {
             forwardLeftCapture = new Move(r,c,r-2,c+2);
        }        
        
        return forwardLeftCapture;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * вправо вперед для черных
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardRightForBlack(int r, int c, Board board){
        Move forwardRight = null;
        if(r>=1 && c>=1 && board.cell[r-1][c-1] == CellContents.empty)
        {
            forwardRight = new Move(r,c, r-1, c-1);
        }
        return forwardRight;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * вправо вперед для черных
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardRightCaptureForBlack(int r, int c, Board board){
        
        Move forwardRightCapture = null;
        
        if(r>=2 && c>=2 && (board.cell[r-1][c-1].equals(CellContents.white) ||
                board.cell[r-1][c-1].equals(CellContents.whiteKing)) &&
                board.cell[r-2][c-2].equals(CellContents.empty))
        {
            forwardRightCapture = new Move(r,c,r-2,c-2);
        }
        
        return forwardRightCapture;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * назад влево для черной дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardLeftForBlack(int r, int c, Board board){
        Move backwardLeft = null;
        
        assert(board.cell[r][c].equals(CellContents.blackKing));
        if(r<Board.rows-1 && c<Board.cols-1 && board.cell[r+1][c+1] == CellContents.empty)
        {
            backwardLeft = new Move(r,c, r+1, c+1);
        }
        
        return backwardLeft;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * назад влево для черной дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardLeftCaptureForBlack(int r, int c, Board board){
        
        Move backwardLeftCapture = null;
        
        if(r<Board.rows-2 && c<Board.cols-2 && (
                board.cell[r+1][c+1].equals(CellContents.white)
                || board.cell[r+1][c+1].equals(CellContents.whiteKing)
                )
                && board.cell[r+2][c+2].equals(CellContents.empty)
                )
        {
             backwardLeftCapture = new Move(r,c,r+2,c+2);
        }
        
        return backwardLeftCapture;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * назад вправо для черной дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardRightForBlack(int r, int c, Board board){
        Move backwardRight = null;
        
        assert(board.cell[r][c].equals(CellContents.blackKing));
        
        if(r<Board.rows-1 && c>=1 &&
           board.cell[r+1][c-1].equals(CellContents.empty)
          )
        {
            backwardRight = new Move(r,c, r+1, c-1);
        }
        return backwardRight;
    }


    /**
     * Метод, делающий проверку на возмоность поедания
     * назад вправо для черной дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardRightCaptureForBlack(int r, int c, Board board){
        
        Move backwardRightCapture = null;
        
        if(r<Board.rows-2 && c>=2 && (board.cell[r+1][c-1].equals(CellContents.white) ||
                board.cell[r+1][c-1].equals(CellContents.whiteKing)) &&
                board.cell[r+2][c-2].equals(CellContents.empty))
        {
            backwardRightCapture = new Move(r,c,r+2,c-2);
        }
        
        return backwardRightCapture;
    }
}