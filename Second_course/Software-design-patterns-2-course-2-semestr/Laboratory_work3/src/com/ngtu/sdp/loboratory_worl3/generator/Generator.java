package com.ngtu.sdp.loboratory_worl3.generator;

import com.ngtu.sdp.loboratory_worl3.query.Query;

import java.util.List;

/**
 * Интерфейс генератора.
 *
 * @param <T> - тип данных, который необходимо сгенерировать.
 * */
public interface Generator<T>
{
    T generate(Query query);

    void setResList(List<T> readyList);
}
