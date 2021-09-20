package checkers;

import java.util.Vector;

/**
 * Класс, реализующий логику ходов
 * черных шашек (игрок - Computer)
 * */
public class White
{   
    static Owner owner;    //поле перечисляемого типа 'игроки'
                           //'хозяин' команды
    /**
     * Метод, отвечающий за ход белых
     * (Computer)
     * */
    public static void Move()
    {   
        UserInteractions.PrintSeparator('-');
        System.out.println("\t\t\t\t\t\t\t\t\t" + "\u001B[34m" + "Ваш ход" + "\u001B[0m");
        UserInteractions.PrintSeparator('-');

        //Человек делает ход
        Human.makeNextWhiteMoves();
    }

    /**
     * Метод, проверяющий возможность поедания
     * за белых.
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает список возможных ходов
     *         (с поеданиями) для белых шашек
     * */
    public static Vector<Move> ObtainForcedMovesForWhite(int r, int c, Board board) 
    {        
        Vector<Move> furtherCaptures = new Vector<>();    //список последующих
                                                          //'поеданий'

        //Если в текущей клетке черная шашкка (любая)
        //проверка на возможность поедания обычной шашкой.
        //Проверка проходит функцией ForwardLeftCaptureForWhite
        //и ForwardRightCaptureForWhite
        //которая возращает объет класса Move
        //если нет позможности съесть, то объект Move = null
        if (board.cell[r][c].equals(CellContents.white) || board.cell[r][c].equals(CellContents.whiteKing))
        {
            if (ForwardLeftCaptureForWhite(r,c,board)!=null)
                furtherCaptures.add(ForwardLeftCaptureForWhite(r,c,board));
            if (ForwardRightCaptureForWhite(r,c,board)!=null)
                furtherCaptures.add(ForwardRightCaptureForWhite(r,c,board));
        }

        //для дамок так же проверям возможноть на поедание
        //в 'нестандартные' для обычных шашек направления
        if(board.cell[r][c].equals(CellContents.whiteKing))
        {
            if (BackwardLeftCaptureForWhite(r,c,board)!=null)
                furtherCaptures.add(BackwardLeftCaptureForWhite(r,c,board));
            if (BackwardRightCaptureForWhite(r,c,board)!=null)
                furtherCaptures.add(BackwardRightCaptureForWhite(r,c,board));
        }
        
        return furtherCaptures;
    }

    /**
     * Метод, аккумулирующий все возможные
     * поедания для белых шашек.
     *
     * @param board - текущее положение поля
     *
     * @return список ходов
     * */
    public static Vector<Move> CalculateAllForcedMovesForWhite(Board board) 
    {
        //список будующих поеданий
        Vector<Move> forcedMovesForWhite = new Vector<>();

        //проход по доске, игнорируя недоступные клетки
        for(int r = 0; r<Board.rows; r++)
        {
            for(int c = (r%2==0)?0:1; c<Board.cols; c+=2)
            {
                //возможные поедания для обычных шашек
                if(board.cell[r][c].equals(CellContents.white) ||
                   board.cell[r][c].equals(CellContents.whiteKing))
                {
                    if (r<Board.rows-2)
                    {
                        //поедание по левой диагонали для белых
                        if (ForwardLeftCaptureForWhite(r,c, board)!=null)
                            forcedMovesForWhite.add(ForwardLeftCaptureForWhite(r,c, board));

                        //поедание по правой диагонали для белых
                        if (ForwardRightCaptureForWhite(r,c, board)!=null)
                            forcedMovesForWhite.add(ForwardRightCaptureForWhite(r,c, board));
                    }                   
                }
                //возможные поедания одля дамок
                if(board.cell[r][c].equals(CellContents.whiteKing))
                {
                    if (r>=2)
                    {
                        //поедание по левым диагоналям
                        if (BackwardLeftCaptureForWhite(r,c,board)!=null)
                            forcedMovesForWhite.add(BackwardLeftCaptureForWhite(r,c, board));

                        //поедаиние по правым диагоналям
                        if (BackwardRightCaptureForWhite(r,c,board)!=null)
                            forcedMovesForWhite.add(BackwardRightCaptureForWhite(r,c,board));                        
                    }
                }
            }    
        }
        
        return forcedMovesForWhite;
    }

    /**
     * Метод, отвечающий за ход, если нет вынужденных
     * ходов (поеданий)
     *
     * @param board - текущее состояние поля
     *
     * @return возвращает список перемещений
     * */
    public static Vector<Move> CalculateAllNonForcedMovesForWhite(Board board){
        
        Vector<Move> allNonForcedMovesForWhite = new Vector<>();

        //проход по доске, игнорируя недоступные клетки
        for(int r = 0; r<Board.rows; r++)
        {
            for(int c = (r%2==0)?0:1; c<Board.cols; c+=2)
            {      
                assert(!board.cell[r][c].equals(CellContents.inaccessible));

                // перемещение вперед для обычной белой шашки
                if( board.cell[r][c].equals(CellContents.white))
                {
                    Move move;

                    move = ForwardLeftForWhite(r, c, board);
                    if(move!=null){
                        allNonForcedMovesForWhite.add(move);   
                    }
                    
                    move = ForwardRightForWhite(r, c, board);
                    if(move!=null){
                        allNonForcedMovesForWhite.add(move);
                    }
                }

                //перемещение вперед и назад для черной дамки
                if(board.cell[r][c] == CellContents.whiteKing){
                    Move move ;

                    move = ForwardLeftForWhite(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForWhite.add(move);
                    }
                    
                    move = ForwardRightForWhite(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForWhite.add(move);
                    }
                    
                    move = BackwardLeftForWhite(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForWhite.add(move);
                    }
                    
                    move = BackwardRightForWhite(r, c, board);
                    if(move!=null)
                    {
                        allNonForcedMovesForWhite.add(move);
                    }                    
                }
            } 
        }
        
        return allNonForcedMovesForWhite;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * влево вперед для белых
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardLeftForWhite(int r, int c, Board board){
        Move forwardLeft = null;
        if(r<Board.rows-1 && c>=1 && board.cell[r+1][c-1] == CellContents.empty)
        {
            forwardLeft = new Move(r,c, r+1, c-1);
        }
        return forwardLeft;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * влево вперед для белых
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardLeftCaptureForWhite(int r, int c, Board board)
    {        
        Move forwardLeftCapture = null;
        
        if(r<Board.rows-2 && c>=2 && (board.cell[r+1][c-1].equals(CellContents.black) ||
                board.cell[r+1][c-1].equals(CellContents.blackKing)) &&
                board.cell[r+2][c-2].equals(CellContents.empty))
        {
             forwardLeftCapture = new Move(r,c,r+2,c-2);
        }        
        
        return forwardLeftCapture;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * вправо вперед для белых
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardRightForWhite(int r, int c, Board board){
        Move forwardRight = null;
        if(r<Board.rows-1 && c<Board.cols-1 &&
           board.cell[r+1][c+1] == CellContents.empty)
        {
            forwardRight = new Move(r,c, r+1, c+1);
        }
        return forwardRight;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * вправо вперед для белых
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move ForwardRightCaptureForWhite(int r, int c, Board board)
    {        
        Move forwardRightCapture = null;
        
        if(r<Board.rows-2 && c<Board.cols-2 && (board.cell[r+1][c+1].equals(CellContents.black) ||
                board.cell[r+1][c+1].equals(CellContents.blackKing)) &&
                board.cell[r+2][c+2].equals(CellContents.empty))
        {
            forwardRightCapture = new Move(r,c,r+2,c+2);
        }
        
        return forwardRightCapture;
    }
    
    private static Move BackwardLeftForWhite(int r, int c, Board board){
        Move backwardLeft = null;
        if( r>=1 && c>=1 &&
                board.cell[r-1][c-1] == CellContents.empty
          )
        {
            backwardLeft = new Move(r,c, r-1, c-1);
        }
        return backwardLeft;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * назад влево для белой дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardLeftCaptureForWhite(int r, int c, Board board)
    {
        Move backwardLeftCapture = null;
        
        if(r>=2 && c>=2 && (board.cell[r-1][c-1].equals(CellContents.black) ||
                board.cell[r-1][c-1].equals(CellContents.blackKing)) &&
                board.cell[r-2][c-2].equals(CellContents.empty))
        {
             backwardLeftCapture = new Move(r,c,r-2,c-2);
        }
        
        return backwardLeftCapture;
    }

    /**
     * Метод, делающий проверку на возмоность хода
     * назад вправо для белой дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardRightForWhite(int r, int c, Board board){
        Move backwardRight = null;
        if(r>=1 && c<Board.cols-1 &&
                board.cell[r-1][c+1] == CellContents.empty
          )
        {
            backwardRight = new Move(r,c,r-1,c+1);
        }
        return backwardRight;
    }

    /**
     * Метод, делающий проверку на возмоность поедания
     * назад влево для белой дамки
     *
     * @param r     - положение по строке
     * @param c     - положение по столбцу
     * @param board - текущее положение доски
     *
     * @return возвращает обьект типа Move(ход)
     * */
    private static Move BackwardRightCaptureForWhite(int r, int c, Board board)
    {        
        Move backwardRightCapture = null;
        
        if(r>=2 && c<Board.cols-2 && (board.cell[r-1][c+1].equals(CellContents.black) ||
                board.cell[r-1][c+1].equals(CellContents.blackKing)) &&
                board.cell[r-2][c+2].equals(CellContents.empty))
        {
            backwardRightCapture = new Move(r,c,r-2,c+2);
        }
        
        return backwardRightCapture;
    }
}