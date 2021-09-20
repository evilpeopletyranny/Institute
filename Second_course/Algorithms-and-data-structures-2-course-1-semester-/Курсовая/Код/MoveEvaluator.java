package checkers;

/**
 * Класс, в котором реализованы все методы
 * для оценки ходов.
 * */
public class MoveEvaluator {
    /**
     * Введем некоторую стоимость для шашек.
     */
    public final int POINT_KING = 4000;
    public final int POINT_NORMAL = 1000;

    /**
     * Оценка поля
     */
    public int fieldAssessment(Board board) {
        int value = 0;

        // проход по доске.
        for (int r = 0; r < Board.rows; r++) {
            // проверка только доступных для хода столбцов
            for (int c = (r % 2 == 0) ? 0 : 1; c < Board.cols; c += 2) {
                CellContents entry = board.cell[r][c];

                //Поскольку COMPUTER играет за черных
                //и пытается максимизировать alpha в алгоритме
                //альфа-бета отсечения, то ведется данная
                //расстановка знаком
                if (entry == CellContents.white) {
                    value -= POINT_NORMAL;
                } else if (entry == CellContents.whiteKing) {
                    value -= POINT_KING;
                } else if
                (entry == CellContents.black) {
                    value += POINT_NORMAL;
                } else if (entry == CellContents.blackKing) {
                    value += POINT_KING;
                }
            }
        }
        return value;
    }
}
