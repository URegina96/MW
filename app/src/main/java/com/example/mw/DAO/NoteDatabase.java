package com.example.mw.DAO;

import androidx.room.Database;  // Импорт аннотации для работы с базой данных
import androidx.room.Room;  // Импорт класса Room для создания базы данных
import androidx.room.RoomDatabase;  // Импорт класса RoomDatabase, который является абстрактным классом для работы с базой данных
import android.content.Context;  // Импорт класса Context, который используется для получения доступа к приложениям и ресурсам

import com.example.mw.Note;  // Импорт класса Note, который используется в базе данных

@Database(entities = {Note.class}, version = 1)  // Указывает на сущности, которые будут включены в базу данных, и её версию
public abstract class NoteDatabase extends RoomDatabase {  // Определение абстрактного класса NoteDatabase, который расширяет RoomDatabase
    public abstract NoteDao noteDao();  // Абстрактный метод, который возвращает объект NoteDao

    private static volatile NoteDatabase INSTANCE;  // Переменная для одноэкземплярного объекта NoteDatabase

    public static NoteDatabase getDatabase(final Context context) {  // Метод для получения единственного экземпляра базы данных
        if (INSTANCE == null) {  // Если экземпляр базы данных еще не создан
            synchronized (NoteDatabase.class) {  // Синхронизация для предотвращения многопоточности
                if (INSTANCE == null) {  // Проверка еще раз внутри заблокированного блока
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),  // Создание базы данных с помощью Room
                                    NoteDatabase.class, "note_database")  // Указание класса базы данных и имени базы данных
                            .build();  // Строит базу данных
                }
            }
        }
        return INSTANCE;  // Возвращает единственный экземпляр базы данных
    }
}