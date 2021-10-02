package com.web_technology.lw2.DTO;

import com.web_technology.lw2.entity.News;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object entity News -
 * облегченные объекты для передачи во view
 *
 * @see News
 * */
public class NewsDTO
{
    private Long id;

    @NotBlank(message = "Поле не может быть пустым!")
    @Size(min = 4, max = 150, message = "Должно содержать от 4 до 150 символов!")
    private String title;

    @NotBlank(message = "Поле не может быть пустым!")
    @Size(min = 4, max = 150, message = "Должно содержать от 4 до 50 символов!")
    private String author;

    @NotBlank(message = "Поле не может быть пустым!")
    private String content;

    public NewsDTO()
    {
    }

    public NewsDTO(Long id, String title, String author, String content)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
