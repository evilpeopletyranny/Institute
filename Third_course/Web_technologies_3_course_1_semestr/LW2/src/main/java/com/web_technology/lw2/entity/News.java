package com.web_technology.lw2.entity;

import javax.persistence.*;

/**
 * Класс - отображение записи в таблице БД - entity
 * */
@Entity
@Table(schema = "public", name = "news")
public class News
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "content", nullable = false)
    private String content;

    public News()
    {
    }

    public News(Long id, String title, String author, String content)
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
