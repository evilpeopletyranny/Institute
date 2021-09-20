package checkers;

import java.util.Vector;

/**
 * Класс реализующий логику игрока
 * COMPUTER.
 * */
public class Computer
{
    //поле для хранения оценок поля
    static MoveEvaluator oracle = new MoveEvaluator();
    static int MAX_DEPTH = 6;    //максимальная глубина дерева
                                 //альфа-бета отсечений

    public static void makeNextBlackMoves()
    {
        //список итоговых ходов
        Vector<Move> resultantMoveSeq = new Vector<>();

        //вызов алгоритма альфа-бета отсечений
        alphaBeta(Game.board, Command.black, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, resultantMoveSeq);

        //применение итоговых ходов к полю
        for(Move m:resultantMoveSeq)
        {
            Game.board.genericMakeBlackMove(m);
        }

        System.out.println();
        System.out.println("Значение ОФ: " + oracle.fieldAssessment(Game.board));
        System.out.print("Ход компьютера: ");
        UserInteractions.DisplayMoveSeq(resultantMoveSeq);
        System.out.println();
    }

    /**
     * Алгоритм alphaBeta отсечений
     *
     * @param board         - текущее состояние доски
     * @param command       - 'команда' для которой обрабывается ход
     * @param depth         - текущий уровень глубины
     * @param alpha         - значение альфа
     * @param beta          - значение бета
     * @param resultMoveSeq - список результирующих ходов
     *
     * @return возвращает значение - оценка текущего,
     *         рассматриваемого положения
     * */
    private static int alphaBeta(Board board, Command command, int depth, int alpha, int beta, Vector<Move> resultMoveSeq)
    {
        //Если мы НЕ можем дальше строить дерево, то
        //оцениваем текущее положение
        if(!canExploreFurther(board, depth))
        {
            return oracle.fieldAssessment(board);
        }

        //создаем список списков возможных ходов и заполняем его
        Vector<Vector<Move>> possibleMoveSeq = expandMoves(board);

        //создаем список возможных состояний доски и заполняем его
        Vector<Board> possibleBoardConf      = getPossibleBoardConf(board, possibleMoveSeq, command);

        //список лучших ходов
        Vector<Move> bestMoveSeq = null;

        if(command == Command.black)
        {
            for(int i=0; i<possibleBoardConf.size(); i++)
            {
                //получаем состояние доски из списка состояний
                Board b              = possibleBoardConf.get(i);

                //получаем список ходов из списка списков возможных ходов
                Vector<Move> moveSeq = possibleMoveSeq.get(i);

                //получаем значение альфа-бета алгоритма
                int value = alphaBeta(b, Command.white, depth+1, alpha, beta, resultMoveSeq);

                //если полученное значение больше альфа,
                //то перезаписываем альфа
                //делаем данный список ходов лучшим
                if(value > alpha)
                {
                    alpha = value;
                    bestMoveSeq = moveSeq;
                }

                //если возникает конфликт интересов,
                //прерываем алгоритм
                if(alpha>beta)
                {
                    break;
                }
            }

            //достигнута конечная глубина, то заносим
            //ходы из списка лучших ходов в список результирующих ходов
            if(depth == 0 && bestMoveSeq!=null)
            {
                resultMoveSeq.addAll(bestMoveSeq);
            }
            return alpha;
        }
        else
            {
            for(int i=0; i<possibleBoardConf.size(); i++){

                //получаем состояние доски из списка состояний
                Board b              = possibleBoardConf.get(i);

                //получаем список ходов из списка списков возможных ходов
                Vector<Move> moveSeq = possibleMoveSeq.get(i);

                //получаем значение альфа-бета алгоритма
                int value = alphaBeta(b, Command.white, depth+1, alpha, beta, resultMoveSeq);

                //если текущее значение меньше бета
                //то перезапись бета,
                //делаем данный список ходов лучшим
                if(value < beta)
                {
                    bestMoveSeq = moveSeq;
                    beta = value;
                }

                //если возникает конфликт интересов,
                //прерываем алгоритм
                if(alpha>beta)
                {
                    break;
                }
            }

            //достигнута конечная глубина, то заносим
            //ходы из списка лучших ходов в список результирующих ходов
            if(depth == 0 && bestMoveSeq!=null){
                resultMoveSeq.addAll(bestMoveSeq);
            }
            return beta;
        }
    }

    /**
     * Метод поиска возможных ходов.
     *
     * @param board - текущее состояние доски
     *
     * @return возвращает список списков ходов.
     * */
    public static Vector<Vector<Move>> expandMoves(Board board)
    {
        //список списков ходов
        Vector<Vector<Move>> outerVector = new Vector<>();

        //список ходов
        Vector<Move> moves;

        //получение списка возможных ходов поедания
        moves = Black.CalculateAllForcedMovesForBlack(board);

        //если список поеданий пуст,
        //то ищем обычные ходы
        if(moves.isEmpty())
        {
            moves = Black.CalculateAllNonForcedMovesForBlack(board);

            for(Move m:moves)
            {
                Vector<Move> innerVector = new Vector<>();
                innerVector.add(m);
                outerVector.add(innerVector);
            }

        }
        //иначе ищем дальнейшие ходы
        //после поедания
        else
            {
            for(Move m:moves){

                int r = m.finalRow;
                int c = m.finalCol;
                Vector<Move> innerVector = new Vector<>();

                innerVector.add(m);

                Board boardCopy = board.duplicate();
                boardCopy.genericMakeBlackMove(m);
                expandMoveRecursivelyForBlack(boardCopy, outerVector, innerVector, r, c);

                innerVector.remove(m);
            }
        }

        return outerVector;
    }

    /**
     * Метод рекурсивного 'разворота' ходов
     * для черных шашек
     *
     * @param board       - текущее состояние доски
     * @param outerVector - список списков ходов - внешний список
     * @param innerVector - внутренний список, который добавляется
     *                      во внешний
     * @param c           - текущее положение по колонне
     * @param r           - текущее положение по столбцу
     * */
    private static void expandMoveRecursivelyForBlack(Board board, Vector<Vector<Move>> outerVector, Vector<Move> innerVector, int r, int c){
        //Список вынужденных ходов
        Vector<Move> forcedMoves = Black.ObtainForcedMovesForBlack(r, c, board);

        //если список вынужденных ходов пуст, то просто
        //вносим внутренний список во внешний
        if(forcedMoves.isEmpty())
        {
            outerVector.add(innerVector);
        }
        //иначе рекурсивно по каждому ходу с поеданием
        //продолжаем 'развертывание' ходов
        else
        {
            for(Move m:forcedMoves)
            {
                Board boardCopy = board.duplicate();
                boardCopy.genericMakeBlackMove(m);
                
                innerVector.add(m);
                expandMoveRecursivelyForBlack(boardCopy, outerVector, innerVector, m.finalRow, m.finalCol);
                innerVector.remove(m);
            }
        }
    }

    /**
     * Вспомогательная функция проверки возможности
     * дальнейшего построения дерева MiniMax'a.
     *
     * @param board   - текущее состояние доски
     * @param depth   - текущий уровень глубины
     * */
    private static boolean canExploreFurther(Board board, int depth)
    {
        //Если игра кончилась или нет невозможный ходов
        //возвращаем false - дальнейшее 'постраение'
        //дерева минимакса невозможно
        if(board.CheckGameComplete())
        {
            return false;
        }

        //если текущая глубина не равна максимальной,
        //то возвращает true и строим дерево минимакса дальше
        return depth != MAX_DEPTH;
    }

    /**
     * Вспомогательный метод,
     * 'эмулирующий' ход того или иного игрока.
     *
     * @param  board             - текущее состояние доски
     * @param  command           - 'команда' для которой обрабывается ход
     * @param  possibleMoveSeq   - список уже проведенных ходов
     *
     * @return possibleBoardConf - список возможных состояний доски
     * */
    private static Vector<Board> getPossibleBoardConf(Board board, Vector<Vector<Move>> possibleMoveSeq, Command command){
        //список для хранения итоговых состояний доски.
        Vector<Board> possibleBoardConf= new Vector<>();


        for(Vector<Move> moveSeq: possibleMoveSeq)
        {
            Board boardCopy = board.duplicate();    //копирование текущего состояния доски
            for(Move move: moveSeq)                 //перебор ходов
            {
                if(command == Command.black)        //если ход 'черных' - комьютера
                {                                   //эмулируем ход компьютера
                    boardCopy.genericMakeBlackMove(move);
                }
                else                                //иначе
                {                                   //эмулируем ход человека
                    boardCopy.genericMakeWhiteMove(move);
                }
            }
            possibleBoardConf.add(boardCopy);       //добавление полученного
                                                    //состояния доски в список
        }
        return possibleBoardConf;
    }  
}