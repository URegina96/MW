package com.example.mw.DAO;
import androidx.room.Dao;  // Импорт аннотации Dao из библиотеки Room
import androidx.room.Insert;  // Импорт аннотации для вставки данных
import androidx.room.Query;  // Импорт аннотации для выполнения запросов к базе данных
import androidx.room.Delete;  // Импорт аннотации для удаления данных

import com.example.mw.Note;  // Импорт класса Note, который представляет объект заметки

import java.util.List;  // Импорт списка, который будет использоваться для хранения заметок

@Dao  // Указывает, что этот интерфейс является DAO (Data Access Object)
public interface NoteDao {
    @Insert  // Указывает, что метод предназначен для вставки новой заметки в базу данных
    void insert(Note note);  // Метод для вставки заметки, принимает объект Note в качестве параметра

    @Query("SELECT * FROM notes ORDER BY id DESC")  // Запрос для получения всех заметок из таблицы 'notes', отсортированных по id в порядке убывания
    List<Note> getAllNotes();  // Метод возвращает список всех заметок

    @Delete  // Указывает, что метод предназначен для удаления заметки из базы данных
    void delete(Note note);  // Метод для удаления заметки, принимает объект Note в качестве параметра
}