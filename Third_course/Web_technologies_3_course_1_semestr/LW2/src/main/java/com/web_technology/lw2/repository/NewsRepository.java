package com.web_technology.lw2.repository;

import com.web_technology.lw2.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозиторий - выполнение действий с БД
 * Используя JpaRepository Spring позволяет нам не описывать
 * большинство простых CRUD операций
 *
 * @see News
 * */
public interface NewsRepository extends JpaRepository<News, Long>
{
    /**
     * Выбор всех записей из таблицы в БД
     * @return список объектов отображений записей в таблице БД
     * */
    @Override
    List<News> findAll();

    /**
     * Найти запись из таблицы Бд по id
     * @return обертку, содержащую объект - отображение записи в таблице
     *         БД, если такая запись есть
     * */
    @Override
    Optional<News> findById(Long id);

    /**
     * Сохранения записи в таблице БД.
     * @return ново созданный объект-отображение
     * */
    @Override
    News save(News news);

    /**
     * Удалить запись из таблицы Бд по id
     * */
    @Override
    void deleteById(Long id);
}
