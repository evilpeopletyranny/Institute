CREATE USER "TEST_USER" WITH PASSWORD 'TEST_USER1';
GRANT ALL PRIVILEGES ON DATABASE postgres TO "TEST_USER";

CREATE DATABASE WEB_TECHNOLOGY;

-- Вариант 7:
-- Написать приложение агентство недвижимости:
--     • Приложение должно отображать список новостей в виде: Title, Author, Content.
--     • Так же должна присутствовать страница добавления новости.
--     • Так же должна присутствовать страница удаления новости.
--     • Хранилище реализовать в памяти с ограничением в 50 записей.
--     • Должны быть реализованы кнопки навигации.
--     • Должно быть реализовано интерактивное взаимодействие с пользователем.
CREATE TABLE news (
    ID SERIAL NOT NULL PRIMARY KEY,             -- автоинкрементирующееся числовое значение
    TITLE VARCHAR(150) NOT NULL,
    AUTHOR VARCHAR(50) NOT NULL,
    CONTENT TEXT NOT NULL           -- текст произвольной длины
);

SELECT * FROM news;

DROP TABLE news;
DROP DATABASE WEB_TECHNOLOGY;

INSERT INTO news
VALUES (1,'Тестовая запись', 'Автор', 'Текст новости');
INSERT INTO news
VALUES (2,'Тестовая запись', 'Автор', 'Текст новости');
INSERT INTO news
VALUES (3,'Тестовая запись', 'Автор', 'Текст новости');
INSERT INTO news
VALUES (4,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (5,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (6,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (7,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (8,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (9,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (10,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (11,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (12,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (13,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (14,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (15,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (16,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (17,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (18,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (19,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (20,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (21,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (22,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (23,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (24,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (25,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (26,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (27,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (28,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (29,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (30,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (31,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (32,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (33,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (34,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (35,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (36,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (37,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (38,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (39,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (40,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (41,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (42,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (43,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (44,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (45,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (46,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (47,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (48,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (49,'Тестовая запись', 'Автор', 'Текст новости');INSERT INTO news
VALUES (50,'Тестовая запись', 'Автор', 'Текст новости');

SELECT COUNT(*) FROM news;

COMMIT;