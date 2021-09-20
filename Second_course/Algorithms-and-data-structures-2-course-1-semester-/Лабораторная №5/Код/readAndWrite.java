import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Интерфейс, реализующий методы для чтения
 * и записи файлов.
 * */
public interface readAndWrite
{
    /**
     * Вспомогательный метод, записывающий переданный список (List)
     * в новый файл.
     *
     * @throws IOException - генерация исключения потока ввода-вывода.
     *
     * @param list         - список для записи файл.
     * @param filenames    - список, в который заносятся имена.
     *                       созданных файлов.
     * @param counter      - счетчик созданных файлов.
     * */
    static void writeListToFile(LinkedList<Integer> list, LinkedList<String> filenames, int counter) throws IOException
    {
        String path = "buffA" + counter + ".txt";    //формирование имени нового файла
        FileWriter writer = new FileWriter(path);    //открытие потока на запись в новый файл

        //цикл для посимвольной записи списка в файл
        for (Integer integer : list)
        {
            writer.write(integer + " ");
        }

        filenames.add(path);                         //добавление нового файла в список

        writer.close();                              //закрытие потока вывода
    }

    /**
     * Вспомогательный метод, возвращающий кол-во элементов в файле.
     *
     * @throws FileNotFoundException  - генерация исключения - файл не найден.
     * @throws InputMismatchException - генерация исключения - неверный.
     *                                  тип считанных данных.
     *
     * @param path                    - путь к файлу.
     *
     * @return возвращает целочисленное значение - кол-во элементов в файле.
     * */
    static int getFileSize(String path) throws FileNotFoundException, InputMismatchException
    {
        Scanner scanner = new Scanner(new File(path));    //открытие потока на чтение

        int size = 0;

        //перебор элементов файла в цикле.
        while(scanner.hasNext())
        {
            scanner.nextInt();
            size++;
        }

        scanner.close();

        return size;
    }

    /**
     * Метод, разделяющий переданный файл на сортированные
     * четверти. Необходимо для дальнейшей сортировки и слияния.
     * Слияние происходит значительно быстрее если данные для слияния
     * заранее отсортированы.
     *
     * Ограничение на кол-во разделяемых файлов - 4 было утсановленно
     * для более компактного предсталвения работы. При работе с
     * объемами данных, которые не могут поместиться в оперативную память
     * рационально делать ограничение равное размеру оперативной памяти.
     *
     * @throws FileNotFoundException  - генерация исключения - файл не найден.
     * @throws InputMismatchException - генерация исключения - неверный.
     *                                  тип считанных данных.
     *
     * @param path                    - путь к файлу.
     *
     * @return возвращает список созданных файлов.
     * */
    static LinkedList<String> splitAndSort(String path) throws IOException, InputMismatchException
    {
        Scanner scanner = new Scanner(new File(path));        //открытие потока чтения

        int inputSize = getFileSize(path);                    //получаем общее кол-во элементов
                                                              //в файле

        int limit = 4;
        int fileSize  = inputSize/limit;                      //ограничиваем кол-во элементов для чтения
                                                              //четвертью от общего кол-ва элементов

        LinkedList<Integer> list     = new LinkedList<>();    //создание списка для записи прочтенных
                                                              //элементов из файла.

        LinkedList<String> fileNames = new LinkedList<>();    //создание списка для записи имен созданных
                                                              //файлов

        int counter = 1;                                      //счетчик созданных файлов.

        //цикл посимвольного чтения из файла
        while(scanner.hasNextInt())
        {
            //Если достигнуто ограничение и считаваемый
            //промежуток не последний, то записываем сортируем
            //список, записываем в файл и очищаем список
            if (list.size() == fileSize && counter != limit)
            {
                writeListToFile(list, fileNames, counter);
                list.clear();
                counter++;
            }
            //иначе читаем до конца файла.
            //если кол-во элементов в файле на кратно ограничению,
            //то в последний промежуток записываем остаточные данные
            list.add(scanner.nextInt());

            Sort.insertionSort(list);                           //сортировка вставками
        }
        writeListToFile(list, fileNames, counter);              //запись в файл

        scanner.close();                                        //закрытие потока чтения

        return fileNames;                                       //вернуть список файлов.
    }
}
