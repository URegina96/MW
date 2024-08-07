package com.example.mw;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id; // Уникальный ID заметки
    private String title; // Заголовок заметки
    private String description; // Описание заметки

    public Note(String title, String description) { // Конструктор класса Note
        this.title = title; // Инициализация заголовка
        this.description = description; // Инициализация описания
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
