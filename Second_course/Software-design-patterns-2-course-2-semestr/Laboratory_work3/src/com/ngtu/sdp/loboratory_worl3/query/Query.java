package com.ngtu.sdp.loboratory_worl3.query;

/**
 * Класс запроса на создание графа.
 * */
public class Query
{
    private final String textOfQuery;    //Поля для хранения запроса

    /**
     * Получение имени графа.
     *
     * @throws IllegalArgumentException - если передан не верный запрос.
     * @return имя графа (корневого узла)
     * */
    public String getGraphName()
    {
        String[] strArr = textOfQuery.split(" ");

        /* Если передано не 3 слова или они не содеражть подстроки create graph,
           то выкидываем исключение */
        if (strArr.length != 3 || !textOfQuery.contains("create graph"))
        {
            try
            {
                throw new IllegalArgumentException("bad request");
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }

        return strArr[strArr.length - 1];
    }

    /**
     * Конструктор с параметром.
     *
     * @param textOfQuery - текст запроса.
     * */
    public Query(String textOfQuery)
    {
        this.textOfQuery = textOfQuery;
    }
}
