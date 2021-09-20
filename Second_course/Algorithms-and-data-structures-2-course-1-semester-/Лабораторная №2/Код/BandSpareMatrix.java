/**
 * Класс реализующий хранение матрицы в ленточно строчном формате.
 * Может хранить только матрицы целочисленных значений.
 *
 * @release: 27.06.20
 * @last_update: 27.06.20
 *
 * @author Vladislav Sapozhnikov 19-IVT-3
 * */

public class BandSpareMatrix {
    private int[][] matrix;         //массив для хранения матрацы в ленточно строчном формате

    private int p;                  //поле, хранящее значение нижней  ширины ленты
    private int q;                  //поле, хранящее значение верхней ширины ленты
    private int n;                  //поле, хранящее размер квадратной матрицы
    private int m;                  //поле, хранящее общую ширину ленты

    /**
     * Внутриклассовый метод, получающий значения полей - характеристик матрицы.
     *
     * @param initMatrix - двумерный массив ссылочного типа Integer.
     * */
    private void getParameters(Integer[][] initMatrix)
    {
        int buffQ = 0;
        int buffP = 0;
        for (Integer[] integers : initMatrix) {
            for (int j = 0; j < integers.length; j++) {
                if ((j < n) & (integers[j] != 0)) buffP = n - j;
                if ((j > n) & (integers[j] != 0)) buffQ = j - n;

                if (buffQ > q) q = buffQ;
                if (buffP > p) p = buffP;
            }
            n++;
        }
        m = p + q + 1;
    }

    /**
     * Конструтор класса. Создает пустую матрицу с нулевыми параметрами.
     * */
    BandSpareMatrix()
    {
        matrix = null;
        p = 0;
        m = 0;
        q = 0;
        n = 0;
    }

    /**
     * Метод, заполняющий уже ранее созданнаую ленто строчную матрицу.
     *
     * @param initMatrix - двумерный массив ссылочного типа Integer.
     * */
    public void fillMatrix(Integer[][] initMatrix)
    {
        getParameters(initMatrix);      //перед заполенением необходимо получить храктеристики матрицы
        matrix = new int[n][m];

        for (int i = 0; i < initMatrix.length; i++)
        {
            for (int j = 0; j < initMatrix[i].length; j++)
            {
                //Алгоритм заполнения матрицы ленточно строчного формата.
                if (((i <= j) && ((j - i) <= q )) || ((i > j) && ((i - j) <= p)))
                {
                    System.out.println("Элемент исходной матрицы[" + i + "][" + j + "]: " +initMatrix[i][j] + " заносим в ["
                            + i + "][" + (j -i + p) + "] упакованной матрицы.");
                    matrix[i][j-i+p] = initMatrix[i][j];
                }
            }
        }
        System.out.println("Остальные в остальные поля упакованной матрицы заносим 0");
        System.out.println();
    }

    /**
     * Метод, выводящий данную матрицу ленточно строчно формата
     * в стандартный поток вывода.
     * */
    public void printMatrix()
    {
        for (int[] i: matrix)
        {
            for (int j: i)
            {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    /**
     * МетодБ позволяющий получить сумму всех элементов данной матрицы.
     *
     * @return size - целочисленное значение равняющееся сумме всех
     *                элементов в матрице.
     * */
    public int getSumMatrixElement()
    {
        int sum = 0;

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                sum += matrix[i][j];
            }
        }

        return sum;
    }
}