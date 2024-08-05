package com.example.mw;   

public class Note {
    private String title; // Заголовок заметки
    private String description; // Описание заметки

    public Note(String title, String description) { // Конструктор класса Note
        this.title = title; // Инициализация заголовка
        this.description = description; // Инициализация описания
    }

    public String getTitle() { // Метод для получения заголовка
        return title; // Возвращаем заголовок
    }

    public String getDescription() { // Метод для получения описания
        return description; // Возвращаем описание
    }

    @Override
    public String toString() {
        return title; // Отображаем только заголовок в списке
    }
}
