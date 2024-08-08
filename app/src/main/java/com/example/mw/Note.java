package com.example.mw;

import androidx.room.Entity; // Импортируем аннотацию для определения сущности в базе данных
import androidx.room.PrimaryKey; // Импортируем аннотацию для определения первичного ключа сущности

@Entity(tableName = "notes") // Указываем имя таблицы для сущности Note
public class Note { // Класс для представления заметки
    @PrimaryKey(autoGenerate = true) // Указываем, что id будет уникальным и будет генерироваться автоматически
    private int id; // Уникальный ID заметки
    private String title; // Заголовок заметки
    private String description; // Описание заметки

    public Note(String title, String description) { // Конструктор класса Note
        this.title = title; // Инициализируем заголовок заметки
        this.description = description; // Инициализируем описание заметки
    }

    public int getId() { // Метод для получения ID заметки
        return id; // Возвращаем ID
    }

    public void setId(int id) { // Метод для установки ID заметки
        this.id = id; // Устанавливаем ID
    }

    public String getTitle() { // Метод для получения заголовка заметки
        return title; // Возвращаем заголовок
    }

    public String getDescription() { // Метод для получения описания заметки
        return description; // Возвращаем описание
    }

    @Override
    public String toString() { // Переопределяем метод toString для отображения заголовка заметки
        return title; // Возвращаем только заголовок для отображения в списке
    }
}
