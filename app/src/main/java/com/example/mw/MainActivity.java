package com.example.mw;

import android.os.Bundle; // Импортируем класс для работы с данными активности
import android.view.View; // Импортируем класс для управления пользовательским интерфейсом
import android.widget.Button; // Импортируем класс для работы с кнопками
import android.widget.EditText; // Импортируем класс для работы с полями ввода текста
import android.widget.Toast; // Импортируем класс для отображения всплывающих уведомлений

import androidx.annotation.NonNull; // Импортируем аннотацию для обозначения не-null параметров
import androidx.appcompat.app.AppCompatActivity; // Импортируем класс для создания активностей с поддержкой ActionBar
import androidx.recyclerview.widget.ItemTouchHelper; // Импортируем класс для обработки жестов взаимодействия с элементами списка
import androidx.recyclerview.widget.LinearLayoutManager; // Импортируем менеджер, который располагает элементы в вертикальном/горизонтальном списке
import androidx.recyclerview.widget.RecyclerView; // Импортируем класс для отображения списков

import com.example.mw.DAO.NoteDao; // Импортируем интерфейс для доступа к данным заметок
import com.example.mw.DAO.NoteDatabase; // Импортируем класс для работы с базой данных заметок

import java.util.ArrayList; // Импортируем класс для работы с динамическими массивами
import java.util.List; // Импортируем интерфейс для работы со списками
import java.util.concurrent.ExecutorService; // Импортируем интерфейс для управления потоком выполнения
import java.util.concurrent.Executors; // Импортируем класс для создания потоков выполнения

public class MainActivity extends AppCompatActivity { // Основная активность приложения
    private NoteDatabase db; // Переменная для хранения базы данных
    private NoteDao noteDao; // Переменная для доступа к методам DAO
    private NoteAdapter adapter; // Адаптер для отображения списка заметок
    private RecyclerView recyclerView; // Объект для отображения списка заметок
    private EditText editTitle, editDescription; // Поля для ввода заголовка и описания заметки
    private ExecutorService executorService; // Сервис для выполнения операций с базой данных в фоновом потоке

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Метод вызывается при создании активности
        super.onCreate(savedInstanceState); // Вызов метода родительского класса
        setContentView(R.layout.activity_main); // Установка разметки для активности

        editTitle = findViewById(R.id.editTitle); // Находим поле для заголовка заметки
        editDescription = findViewById(R.id.editDescription); // Находим поле для описания заметки
        Button buttonSave = findViewById(R.id.buttonSave); // Находим кнопку сохранения заметки
        recyclerView = findViewById(R.id.recyclerViewNotes); // Находим RecyclerView для отображения списка заметок

        db = NoteDatabase.getDatabase(this); // Инициализируем базу данных
        noteDao = db.noteDao(); // Получаем экземпляр DAO для работы с заметками
        executorService = Executors.newSingleThreadExecutor(); // СоздаемExecutorService для выполнения задач в фоновом потоке

        adapter = new NoteAdapter(new ArrayList<>(), this); // Инициализируем адаптер с пустым списком заметок
        recyclerView.setAdapter(adapter); // Устанавливаем адаптер для RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Устанавливаем менеджер для отображения списка в вертикальном формате

        loadNotes(); // Загружаем заметки из базы данных

        buttonSave.setOnClickListener(new View.OnClickListener() { // Устанавливаем слушатель для кнопки сохранения заметки
            @Override
            public void onClick(View v) { // Метод, который будет вызван при нажатии кнопки
                String title = editTitle.getText().toString(); // Получаем текст заголовка из поля ввода
                String description = editDescription.getText().toString(); // Получаем текст описания из поля ввода

                if (!title.isEmpty() && !description.isEmpty()) { // Проверяем, что оба поля не пустые
                    Note note = new Note(title, description); // Создаем новый объект заметки с заголовком и описанием
                    executorService.execute(() -> { // Запускаем задачу в фоновом потоке
                        noteDao.insert(note); // Сохраняем заметку в базе данных
                        loadNotes(); // Обновляем список заметок после сохранения
                    });
                    editTitle.setText(""); // Очищаем поле заголовка после сохранения
                    editDescription.setText(""); // Очищаем поле описания после сохранения
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите заголовок и описание", Toast.LENGTH_SHORT).show(); // Показываем ошибку, если поля пустые
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { // Создаем помощник для обработки смахивания
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Не поддерживаем перемещение элементов
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { // Метод, вызываемый при смахивании
                int position = viewHolder.getAdapterPosition(); // Получаем позицию смахнутого элемента
                Note noteToRemove = adapter.getNoteAtPosition(position); // Получаем заметку по позиции

                executorService.execute(() -> { // Запускаем удаление в фоновом потоке
                    noteDao.delete(noteToRemove); // Удаляем заметку из базы данных
                    loadNotes(); // Обновляем список заметок после удаления
                });
            }
        }).attachToRecyclerView(recyclerView); // Привязываем ItemTouchHelper к RecyclerView
    }

    private void loadNotes() { // Метод для загрузки заметок из базы данных
        executorService.execute(() -> { // Запускаем задачу в фоновом потоке
            List<Note> notes = noteDao.getAllNotes(); // Загружаем все заметки из базы данных
            runOnUiThread(() -> { // Возвращаемся к основному потоку для обновления UI
                adapter.updateNotes(notes); // Обновляем адаптер с новыми заметками
            });
        });
    }
}
