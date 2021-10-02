package com.web_technology.lw2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBoot приложение - выполнение
 * Лабораторной работы 2. Создание просто веб-сервиса
 *     согласно паттерну MVC.
 * */
@SpringBootApplication
public class Lw2Application extends SpringBootServletInitializer
{
    /**
     * Настройки для запуска приложения в отдельном контейнере
     * */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Lw2Application.class);
    }

    /**
     * Точка входа в программу
     * */
    public static void main(String[] args)
    {
        SpringApplication.run(Lw2Application.class, args);
    }

}
