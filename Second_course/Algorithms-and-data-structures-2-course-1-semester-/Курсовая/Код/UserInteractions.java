package checkers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Класс реализующий взаимодействие пользователя
 * и программы.
 * */
public class UserInteractions
{
    /**
     * Получение координат хода от игрока.
     * */
    public static Move getNextMove()
    {
        return TakeUserInput(-1,-1);
    }
    
    //Передаем r1 как -1 и c1 как -1 если мы хотим принять ход от игрока.
    public static Move TakeUserInput(int r1, int c1){
        //прорисовка доски
        Game.board.Display();
        PrintSeparator('-'); 
        
        // Просьба о вводе
        System.out.println("Введите свой ход.");
        System.out.println("Начало:");
        
        System.out.print("\tСтр(1-12): ");
        if (r1==-1){            
            r1 = TakeInput();            
        }
        else{
            System.out.println(r1);
        }
        
        System.out.print("\tКол(1-12): ");
        if (c1==-1){            
            c1 = TakeInput();
        }
        else{
            System.out.println(c1);
        }
        
                
        System.out.println("Конец:");
        System.out.print("\tСтр(1-12): ");
        int r2 = TakeInput();
        
        System.out.print("\tКол(1-12): ");
        int c2 = TakeInput();
        
        return new Move(r1,c1,r2,c2);
    }

    /**
     *  Ввод координат с проверкой.
     *  Если необходимо то предалагется повторный ввод.
     * */
    private static int TakeInput(){
        
        int num;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        while (true)
        {
            try
            {
                num = Integer.parseInt(br.readLine());
                num -= 1;
                
                if (num>=0 && num < Board.rows){
                    break;                
                }
            } catch (Exception ignored) {}
            
            System.out.print("Неправильный ввод... Повторите попытку: ");
        }
        
        return num;
    }

    /**
     * Вывод 'декораций'.
     *
     * @param ch - символ для вывода.
     * */
    public static void PrintSeparator(char ch)
    {
        switch (ch) {
            case '_' -> System.out.println("___________________________________________________________________________");
            case '-' -> System.out.println("--------------------------------------------------------------------------------");
            case '#' -> System.out.println("###########################################################################");
        }
    }

    /**
     * Вывод информации о ходе.
     *
     * @param moveSeq - список с координатами хода.
     * */
    public static void DisplayMoveSeq(Vector<Move> moveSeq){
        for(Move m:moveSeq)
        {
            m.display();
            System.out.print(", ");
        }
 
        System.out.println();
    }
}