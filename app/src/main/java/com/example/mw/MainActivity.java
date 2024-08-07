package com.example.mw;

import android.os.Bundle; // Импортируем класс для работы с данными
import android.view.View; // Импортируем класс для работы с видами
import android.widget.Button; // Импортируем кнопку
import android.widget.EditText; // Импортируем поле ввода текста
import android.widget.Toast; // Импортируем всплывающее уведомление

import androidx.annotation.NonNull; // Импортируем аннотацию
import androidx.appcompat.app.AppCompatActivity; // Импортируем базовую активность
import androidx.recyclerview.widget.ItemTouchHelper; // Импортируем помощник для жестов
import androidx.recyclerview.widget.LinearLayoutManager; // Импортируем менеджер для списка
import androidx.recyclerview.widget.RecyclerView; // Импортируем вид для списка

import com.example.mw.DAO.NoteDao;
import com.example.mw.DAO.NoteDatabase;

import java.util.ArrayList; // Импортируем класс для работы с массивами
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private NoteDatabase db; // База данных
    private NoteDao noteDao; // DAO
    private NoteAdapter adapter; // Адаптер для отображения заметок в списке
    private RecyclerView recyclerView; // Вид для отображения списка
    private EditText editTitle, editDescription; // Поля ввода для заголовка и описания заметки
    private ExecutorService executorService; // Для выполнения операций с базой данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Вызов родительского метода
        setContentView(R.layout.activity_main); // Установка разметки для активности

        editTitle = findViewById(R.id.editTitle); // Находим поле для заголовка
        editDescription = findViewById(R.id.editDescription); // Находим поле для описания
        Button buttonSave = findViewById(R.id.buttonSave); // Находим кнопку сохранения
        recyclerView = findViewById(R.id.recyclerViewNotes); // Находим RecyclerView для заметок

        db = NoteDatabase.getDatabase(this);
        noteDao = db.noteDao();
        executorService = Executors.newSingleThreadExecutor(); // Инициализируем ExecutorService

        adapter = new NoteAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter); // Устанавливаем адаптер для RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Устанавливаем менеджер расположения для списка

        // Загружаем заметки из базы данных
        loadNotes();

        buttonSave.setOnClickListener(new View.OnClickListener() { // Устанавливаем слушатель для кнопки сохранения
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString(); // Получаем текст заголовка
                String description = editDescription.getText().toString(); // Получаем текст описания

                if (!title.isEmpty() && !description.isEmpty()) { // Проверяем, что поля не пустые
                    Note note = new Note(title, description);
                    executorService.execute(() -> {
                        noteDao.insert(note); // Сохраняем заметку в базе данных
                        loadNotes(); // Обновляем список заметок
                    });
                    editTitle.setText(""); // Очищаем поле заголовка
                    editDescription.setText(""); // Очищаем поле описания
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, введите заголовок и описание", Toast.LENGTH_SHORT).show(); // Показываем уведомление
                }
            }
        });

        // Настройка жестов для удаления заметок
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Перемещение не поддерживается
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // Получаем позицию смахнутого элемента
                Note noteToRemove = adapter.getNoteAtPosition(position); // Получаем заметку по позиции

                executorService.execute(() -> {
                    noteDao.delete(noteToRemove); // Удаляем заметку из базы данных
                    loadNotes(); // Обновляем список заметок
                });
            }
        }).attachToRecyclerView(recyclerView); // Привязываем ItemTouchHelper к RecyclerView
    }

    private void loadNotes() {
        executorService.execute(() -> {
            List<Note> notes = noteDao.getAllNotes(); // Загружаем все заметки из базы данных
            runOnUiThread(() -> {
                adapter.updateNotes(notes); // Обновляем адаптер с новыми заметками
            });
        });
    }
}
