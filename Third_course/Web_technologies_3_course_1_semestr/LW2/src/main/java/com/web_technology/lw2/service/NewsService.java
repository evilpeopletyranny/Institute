package com.web_technology.lw2.service;

import com.web_technology.lw2.entity.News;
import com.web_technology.lw2.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис - надстройка над Репозиторием.
 * Выполняем функции проверки пережде чем репозиторий обращаться
 * к БД
 *
 * @see News
 * @see NewsRepository
 * */
@Service
public class NewsService {
    //DI необходимого репозитория
    @Autowired
    private NewsRepository newsRepository;

    /**
     * Выбор всех записей из таблицы в БД
     * @return список объектов отображений записей в таблице БД
     * */
    public List<News> findAll()
    {
        return newsRepository.findAll();
    }

    /**
     * Найти запись из таблицы Бд по id
     * @return обертку, содержащую объект - отображение записи в таблице
     *         БД, если такая запись есть
     * */
    public Optional<News> findById(Long id)
    {
        return newsRepository.findById(id);
    }

    /**
     * Сохранения записи в таблице БД.
     * @return ново созданный объект-отображение
     * */
    public News save(News news)
    {
        return newsRepository.save(news);
    }

    /**
     * Удалить запись из таблицы Бд по id
     * @return true если запись удалена
     * */
    public Boolean deleteById(Long id)
    {
        //проверка наличия записи в таблице БД
        if (newsRepository.findById(id).isPresent())
        {
            newsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
