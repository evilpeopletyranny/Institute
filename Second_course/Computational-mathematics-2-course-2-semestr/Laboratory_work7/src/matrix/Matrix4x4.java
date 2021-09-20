package matrix;

/**
 * Класс матриц размером 4х4
 *
 * @see Matrix
 * */
public class Matrix4x4 implements Matrix
{
    //поле для хранений значений матрицы
    private final double[][] matrix = new double[4][4];

    /**
     * Конструктор с параметрами.
     *
     * @param matrix - массив значений матрицы
     * */
    public Matrix4x4(double[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, matrix[i].length);
        }
    }

    /**
     * Умножение матрицы на вектор.
     *
     * @param vector - вектор, на который необходимо умножить матрицу
     * @return полученный вектор
     * */
    public double[] multiplyByVector(double[] vector)
    {
        double[] result = new double[vector.length];

        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++)
            {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    /**
     * Перегруженный метод вывода матрицы в консоль.
     * */
    @Override
    public String toString()
    {
        return matrix[0][0] + " " + matrix[0][1] + " " + matrix[0][2] + " " + matrix[0][3] + "\n"
             + matrix[1][0] + " " + matrix[1][1] + " " + matrix[1][2] + " " + matrix[1][3] + "\n"
             + matrix[2][0] + " " + matrix[2][1] + " " + matrix[2][2] + " " + matrix[2][3] + "\n"
             + matrix[3][0] + " " + matrix[3][1] + " " + matrix[3][2] + " " + matrix[3][3] + "\n";
    }
}
