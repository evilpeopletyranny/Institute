package com.web_technology.lw2.controllers;

import com.web_technology.lw2.DTO.NewsDTO;
import com.web_technology.lw2.entity.News;
import com.web_technology.lw2.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, обратывающие действия, связанные с Entity
 * News
 *
 * @see News
 * @see NewsDTO
 * @see NewsService
 * */
@Controller
@RequestMapping("/laboratory_work")
public class NewsController
{
    //DI сервиса для работы с entity - News
    @Autowired
    NewsService newsService;

    /**
     * Обработка Get запроса url: /laboratory_work/news
     * Передача во view списка со всеми записями в таблице news
     *
     * @param model - интерфейс для передачи данных из контроллера во view
     * @return указанный view
     * */
    @GetMapping("/news")
    public String showAllNews(Model model)
    {
        Map<String, List<News>> map = Map.ofEntries(
                Map.entry("allNewsList", newsService.findAll())
        );
        model.mergeAttributes(map);

        return "index";
    }

    /**
     * Обработка Get запроса url: /laboratory_work/add
     * Передаем во view:
     *  allNewsListSize - кол-во всех записей в таюлице для контроля ограничения
     *  в 50 записей.
     *  newsForm - пустой объект NewsDTO. Данный подход позволяет заполнять
     *  данные сразу в поля объекта.
     *
     * @param model - интерфейс для передачи данных из контроллера во view
     * @return указанный view
     * */
    @GetMapping("/add")
    public String addNews(Model model)
    {
        Map<String, Object> map = Map.ofEntries(
                Map.entry("allNewsListSize", newsService.findAll().size()),
                Map.entry("newsForm", new NewsDTO())
        );

        model.mergeAttributes(map);

        return "addOrUpdate";
    }

    /**
     * Обработка Post запроса url: /laboratory_work/add
     *
     * @param model - интерфейс для передачи данных из контроллера во view
     * @param bindingResult - объект для отловки ошибок валидатора
     * @param newsForm - получение ново-созданного объекта
     *                   @Valid - валидация данных объекта
     *                   @ModelAttribute - получение данных из model
     * @return указанный view
     * */
    @PostMapping("/add")
    public String addNews(@Valid @ModelAttribute("newsForm") NewsDTO newsForm,
                          BindingResult bindingResult, Model model)
    {
        News news;

        //Валидации данных
        //при ошибки возращаемся на страницу и выводим сообщения об ошибках
        if (bindingResult.hasErrors())
        {
            return "addOrUpdate";
        }

        //если id переданного объекта = null, то создается новый объект
        //и нам не требуется указывать id, т.к. он задается автоматически
        if (newsForm.getId() == null)
        {
            news = new News(null, newsForm.getTitle(), newsForm.getAuthor(), newsForm.getContent());
        }
        //иначе происходит обновление записи и мы напрямую указываем id
        else
        {
            news = new News(newsForm.getId(), newsForm.getTitle(), newsForm.getAuthor(), newsForm.getContent());
        }

        //Если не получилось сохранить объект, то добавляем в модель ошибку
        //и возвращаем на страницу, где была получена ошибка
        if (newsService.save(news) == null)
        {
            model.addAttribute("newsError", "При создании новости произошла ошибка");
            return "addOrUpdate";
        }

        return "redirect:/laboratory_work/news";
    }

    /**
     * Обработка Post запроса url: /laboratory_work/update
     *
     * @param model - интерфейс для передачи данных из контроллера во view
     * @param id - id записи, которую необходимо обновить
     *             @RequestParam - параметр получаемый из тела запроса
     * @return указанный view
     * */
    @GetMapping("/update")
    public String updateNews(@RequestParam("newsId") Long id, Model model)
    {
        //если нашли запись, то передаем ее в модель
        newsService.findById(id).ifPresent(news -> model.addAttribute("newsForm", news));
        return "addOrUpdate";
    }

    /**
     * Обработка Get запроса url: /laboratory_work/delete
     *
     * @param model - интерфейс для передачи данных из контроллера во view
     * @param id - id записи, которую необходимо обновить
     *             @RequestParam - параметр получаемый из тела запроса
     * @return указанный view
     * */
    @GetMapping("/delete")
    public String deleteNews(@RequestParam("newsId") Long id, Model model)
    {
        //если нашли запись, то передаем ее в модель
        newsService.findById(id).ifPresent(news -> model.addAttribute("newsForm", news));
        return "delete";
    }

    /**
     * Обработка Get запроса url: /laboratory_work/delete
     * @param id - id записи, которую необходимо обновить
     *             @RequestParam - параметр получаемый из тела запроса
     * @return указанный view
     * */
    @PostMapping("/delete")
    public String deleteNews(@RequestParam(value = "id", defaultValue = "") Long id)
    {
        newsService.deleteById(id);

        return "redirect:/laboratory_work/news";
    }
}
