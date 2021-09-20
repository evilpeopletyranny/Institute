package checkers;
/**
 * Класс отвечающий за игровое поле
 * */
public class Board
{
    private final MoveEvaluator oracle = new MoveEvaluator();
    int blackCheckers;               //кол-во черных
    int whiteCheckers;               //кол-во белых

    static final int rows = 12;      //всего строк доски
    static final int cols = 12;      //всего колон доски
    CellContents[][]    cell;           //двумерный массив клеток

    /**
     * Конструктор по умолчанию.
     * Доска в начальном положении
     * */
    Board()
    {
        this.blackCheckers = 30;    //начальное кол-во черных
        this.whiteCheckers = 30;    //начальное кол-во белых

        //Инициализация клеток поля
        //CellContents.white   - в клетке белая шашка
        //CellContents.inaccessible - неиспользуемая клетка (в нее невозможно сделать ход)
        //CellContents.empty   - пустая клетка (в нее возможно сделать ход)
        //CellContents.black,  - в клетке черная шашка
        this.cell = new CellContents[][]
        {
            {CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,       },
            {CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,       },
            {CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible, CellContents.white,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,       },
            {CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible, CellContents.empty,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,       },
            {CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,       },
            {CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible,},
            {CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black,        CellContents.inaccessible, CellContents.black        }
        };
    }

    /**
     * Конструктор копирования.
     * Необходим для постороения COMPUTER
     * возможных ходов.
     *
     * @param board - доска, котурую необходимо скопировать.
     * */
    Board(CellContents[][] board)
    {
        this.blackCheckers = this.whiteCheckers = 30;

        this.cell = new CellContents[rows][cols];
        for(int i = 0; i < rows; i++)
        {
            System.arraycopy(board[i], 0, this.cell[i], 0, cols);
        }
    }

    /**
     * Ход - перемещение шашки
     *       по полю.
     *
     * @param r1, c1 - начальная координата хода
     *
     * @param r2, c2 - конечная  координата хода
     * */
    public void MakeMove(int r1, int c1, int r2, int c2) 
    {
        this.cell[r2][c2] = this.cell[r1][c1];
        this.cell[r1][c1] = CellContents.empty;

        //если шашка белая и достигла верхнего конца поля
        //она становится дамкой
        if(this.cell[r2][c2].equals(CellContents.white) && r2==rows-1)
        {
            this.cell[r2][c2] = CellContents.whiteKing;
        }

        //иначе если шашка черная и достигла низа поля
        //она становится дамкой
        else if(this.cell[r2][c2].equals(CellContents.black) && r2==0)
        {
            this.cell[r2][c2] = CellContents.blackKing;
        }
    }
    
    /**
     * Поедание черной шашки.
     *
     * @param r1, c1 - начальная координата хода
     *
     * @param r2, c2 - конечная  координата хода
     * */
    public void CaptureBlackPiece(int r1, int c1, int r2, int c2)
    {
        //Определяем направление захвата
        MoveDir dir = r2>r1?(c2>c1?MoveDir.forwardRight:MoveDir.forwardLeft)
                :(c2>c1?MoveDir.backwardRight:MoveDir.backwardLeft);

        //удаление черной шашки с доски
        switch (dir)
        {
            case forwardLeft   -> this.cell[r1 + 1][c1 - 1] = CellContents.empty;
            case forwardRight  -> this.cell[r1 + 1][c1 + 1] = CellContents.empty;
            case backwardLeft  -> this.cell[r1 - 1][c1 - 1] = CellContents.empty;
            case backwardRight -> this.cell[r1 - 1][c1 + 1] = CellContents.empty;
        }

        //уменьшение кол-ва черных шашек на поле
        this.blackCheckers--;

        //перемещение шашки по полю
        this.MakeMove(r1, c1, r2, c2);
    }

    /**
     * Поедание белой шашки.
     *
     * @param r1, c1 - начальная координата хода
     *
     * @param r2, c2 - конечная  координата хода
     * */
    public void CaptureWhitePiece(int r1, int c1, int r2, int c2)
    {
        //Определяем направление захвата
        MoveDir dir = r2<r1?(c2<c1?MoveDir.forwardRight:MoveDir.forwardLeft)
                :(c2<c1?MoveDir.backwardRight:MoveDir.backwardLeft);

        //удаление белой шашки с доски
        switch (dir)
        {
            case forwardLeft   -> this.cell[r1 - 1][c1 + 1] = CellContents.empty;
            case forwardRight  -> this.cell[r1 - 1][c1 - 1] = CellContents.empty;
            case backwardLeft  -> this.cell[r1 + 1][c1 + 1] = CellContents.empty;
            case backwardRight -> this.cell[r1 + 1][c1 - 1] = CellContents.empty;
        }

        //уменьшение кол-ва белых шашек на поле
        this.whiteCheckers--;

        //перемещение шашки по полю
        this.MakeMove(r1, c1, r2, c2);
    }
    
    /**
     * Перебор возможных ходов человека.
     * Метод необходим для COMPUTER в момент проверки возможных ходов.
     *
     * @param move - направление хода
     * */
    public void genericMakeWhiteMove(Move move)
    {
        int r1 = move.initialRow;
        int c1 = move.initialCol;
        int r2 = move.finalRow;
        int c2 = move.finalCol;
        
        if((Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2))
        {
            CaptureBlackPiece(r1, c1, r2, c2);
            
        }
        else
        {
            MakeMove(r1, c1, r2, c2);
        }
    }

    /**
     * Перебор возможных ходов робота.
     * Метод необходим для COMPUTER в момент проверки возможных ходов.
     *
     * @param move - направление хода
     * */
    public void genericMakeBlackMove(Move move)
    {
        int r1 = move.initialRow;
        int c1 = move.initialCol;
        int r2 = move.finalRow;
        int c2 = move.finalCol;
        
        if(Math.abs(r2-r1)==2 && Math.abs(c2-c1)==2)
        {
            CaptureWhitePiece(r1, c1, r2, c2);
        }
        else
        {
            MakeMove(r1, c1, r2, c2);
        }

    }

    /**
     * Прорисовка поля.
     * */
    public void Display()
    {
        System.out.println("Черных шашек: " + blackCheckers);
        System.out.println("Белых  шашек: " + whiteCheckers);
        System.out.println("ОФ: " + oracle.fieldAssessment(this));

        this.DisplayColIndex();
        this.DrawHorizontalLine();
        
        for(int i = rows-1; i >= 0; i--)
        {
            this.DisplayRowIndex(i);           
            this.DrawVerticalLine();
            
            for(int j = 0; j< cols; j++)
            {
                System.out.print(this.BoardCell(i,j));
                this.DrawVerticalLine();
            }   
            
            this.DisplayRowIndex(i);
            System.out.println();
            this.DrawHorizontalLine();
        }
        
        this.DisplayColIndex();
        System.out.println();
    }    

    /**
     * Вспомогательный метод - прорисовка отдельной клетки.
     *                         Заполнение клетки зависит от её
     *                         содержимого в массиве cell.
     *
     * @param i, j           - элемент массива cell/
     * */
    private String BoardCell(int i, int j)
    {
        assert(i>0 && i<rows && j>0 && j< cols);
        String str = "";
        
        if(this.cell[i][j] == CellContents.inaccessible)
        {
            str = "     ";
        }
        else if(this.cell[i][j] == CellContents.empty)
        {
            str = "  _  ";
        }
        else if(this.cell[i][j] == CellContents.black)
        {
            str = "  ⛀  ";
        }        
        else if(this.cell[i][j] == CellContents.white)
        {
            str = "  ⛂  ";
        }
        else if(this.cell[i][j] == CellContents.blackKing)
        {
            str = "  ⛁  ";
        }
        else if(this.cell[i][j] == CellContents.whiteKing)
        {
            str = "  ⛃  ";
        }
        
        return str;
    }

    /**
     * Вспомогательный метод - прорисовка горизонтальной линни
     *                         на поле.
     * */
    private void DrawHorizontalLine()
    {
        System.out.println("    _______________________________________________________________________");
    }

    /**
     * Вспомогательный метод - прорисовка вертикальной линни
     *                         на поле.
     * */
    private void DrawVerticalLine()
    {
        System.out.print("|");
    }

    /**
     * Вспомогательный метод - вывод индексов колонн
     * */
    private void DisplayColIndex()
    {
        System.out.print("   ");
        for(int colIndex = 1; colIndex <= cols; colIndex++)
        {
            System.out.print("   " + colIndex + "  " );
        }
        System.out.println();
    }

    /**
     * Вспомогательный метод - вывод индексов линий
     *
     * @param index - индекс текущей строки
     * */
    private void DisplayRowIndex(int index)
    {
        if (index < 9)
        {
            System.out.print(" " + (index + 1) + " ");
        }
        else
        {
            System.out.print("" + (index + 1) + " ");
        }

    }
    
    /**
     * Дублирование текущей доски.
     *
     * @return newBoard - копия текущей доски.
     * */
    public Board duplicate()
    {
        Board newBoard = new Board(this.cell);
        newBoard.blackCheckers = this.blackCheckers;
        newBoard.whiteCheckers = this.whiteCheckers;

        return newBoard;
    }
    
    /**
     * Проверка: кончалась ли игра или нет?
     *
     * @return true  - игра закончилась
     *         False - игра продолжается
     * */
    public boolean CheckGameComplete()
    {
        return this.blackCheckers == 0 || this.whiteCheckers == 0;
    }
}