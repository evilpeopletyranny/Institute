package matrix;

/**
 * Интерфейс матриц
 *
 * @see Matrix4x4
 * */
public interface Matrix
{
     /**
      * Умножение матрицы на вектор.
      *
      * @param vector - вектор, на который необходимо умножить матрицу
      * @return полученный вектор
      * */
     double[] multiplyByVector(double[] vector);
}
