import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Интерфейс содержащий методы необходимые
 * для внешней сортировки.
 *
 * Предназначен для работы только с целочисленными значениями.
 * */
public interface Sort
{
    /**
     * Сортировка вставками.
     * На вход подается не сортированный целочисленный массив.
     * На выходе получается массив, все эелементы которого
     * отсортированны, т.е. удовлетворяют условию:
     *               A1 < A2 < ... < An
     *  @param list - входной целочисленный массив.
     * */
    static void insertionSort(LinkedList<Integer> list)
    {
        for (int i = 1; i < list.size(); i++)    //проход по входному
        {                                        //массиву

            int value = list.get(i);             //'вытаскиваем' значение для
                                                 //сортировки

            int j = i - 1;                       //перемещение по элементам
            for (; j >= 0; j--)                  //перед 'вытащенным' значением
            {
                if (value < list.get(j))         //если 'вытащенный' элемент меньше
                {                                //элемента отсортированной части,
                    list.set(j+1, list.get(j));  //то смещаем все следущие элементы
                }                                //отсортированной части вправо
                else
                {                                //если 'вытащенный' элемент больше
                    break;                       //всех элементов отсортированной части,
                }                                //то - прервать
            }

            // В освободившееся место вставляем вытащенное значение
            list.set(j+1, value);
        }
    }

    /**
     * Метод, производящий слияние двух отсортированных
     * частей в новый файл.
     *
     * @throws IOException - исключение потока ввода-вывода
     *
     * @param list1  - первый список для слияния в новый файл
     * @param list2  - второй список для слияния в новый файл
     * @param writer - поток вывода в файл
     * */
    static void merge(FileWriter writer, LinkedList<Integer> list1, LinkedList<Integer> list2) throws IOException {
        if (list1.isEmpty())                        //если 1ый список уже пуст
        {                                           //дозаписываем 2ой список
            for (Integer integer : list2)           //в файл и прерываем рекурсию
            {
                writer.write(integer + " ");
            }
            return;
        }
        else if (list2.isEmpty())                   //если 2ой список уже пуст
        {                                           //дозаписываем 1ый список
            for (Integer integer : list1)           //в файл и прерываем рекурсию
            {
                writer.write(integer + " ");
            }
            return;
        }

        for (int j = 0; j < list1.size();j ++)
        {
            for (int k = 0; k < list2.size(); k++)
            {
                if (list1.get(j) <= list2.get(k))            //если элемент из 1ого
                {                                            //списка меньше или равен элемента
                    writer.write(list1.get(j) + " ");    //из 2ого списка, то записываем
                    list1.remove(j);                         //элемент из 1ого списка и
                    break;                                   //удалем её из спика. Прерывание
                }
                else
                {
                    writer.write(list2.get(k) + " ");    //если элемент из 2ого
                    list2.remove(k);                         //списка меньше элемента из 1ого списка,
                    break;                                   //то записываем элемент из второго спика
                }                                            //и удаляем его из списка
            }
            break;
        }
        merge(writer, list1, list2);                         //рекурсивный вызов пока оба списка
    }                                                        //не окажутся пустыми

    /**
     * Алгоритм внешней сортировки.
     *
     * @throws IOException - исключение потока ввода-вывода
     *
     * @param fileNames - список файлов отсортированных
     *                    на 1ом этапе сортировки: разделение
     *                    общего файла и сортировка его элементов
     *                    сортировкой вставками.
     *
     * @param ch        - счетчик для изменения имен новых файлов.
     *                    необходим для рекурсивного вызова
     * */
    static void externalSort(LinkedList<String> fileNames, char ch) throws IOException {
        ch = (char)((int)ch + 1);                                //увеличение счетчика

        LinkedList<Scanner> scannerList = new LinkedList<>();    //список потоков чтения

        for (String fileName : fileNames)                        //заполнение потока
        {                                                        //чтения на основе
            scannerList.add(new Scanner(new File(fileName)));    //списка файлами
        }
                                                                 //очищаем список имен
        fileNames.clear();                                       //файлов для нового
                                                                 //заполнения

        //цикл работы с потоками чтения
        for (int i = 0, j = 1; i < scannerList.size()-1; i += 2, j++)
        {
            String fileName;                            //формирование имени
            if (scannerList.size() == 2)                //нового файла
            {                                           //если это конечное слияние
                fileName = "output.txt";                //то задаем имя output.txt
            }
            else
            {                                           //иначе формируем имя
                fileName = "buff" + ch + j + ".txt";    //ипользуя индексы
            }

            FileWriter writer = new FileWriter(fileName);    //открытие потока ввода в файл

            fileNames.add(fileName);                         //добавление имени файла в список
                                                             //имен файла

            LinkedList<Integer> list1 = new LinkedList<>();    //списки для записи элементов
            LinkedList<Integer> list2 = new LinkedList<>();

            //элементы из i-ого потока ввода записываем в 1ый список
            while(scannerList.get( i ).hasNextInt())
            {
                list1.add(scannerList.get( i ).nextInt());
            }

            //элементы из i+1 потока ввода записываем во 2ой список
            while(scannerList.get(i+1).hasNextInt())
            {
                list2.add(scannerList.get(i+1).nextInt());
            }

            merge(writer, list1, list2);    //производим слияние списков в новый файл

            writer.close();                 //закрываем поток ввода

            //рекурсивный вызов пока в списке файлов не будет 1 список
            while (fileNames.size() != 1)
            {
                externalSort(fileNames, ch);
            }
        }

        //закрытие потоков чтения
        for (Scanner scanner : scannerList)
        {
            scanner.close();
        }
    }

    /**
     * Метод внешней сортировки для файла
     *
     * @param path - путь к файлу для сортировки
     * */
    static void externalFileSorting(String path) throws IOException
    {
        //1ый этап - разделение исходного файла
        //и их сортировки вставками
        LinkedList<String> fileNames = readAndWrite.splitAndSort(path);

        //внешняя сортировка
        externalSort(fileNames, 'A');
    }
}
