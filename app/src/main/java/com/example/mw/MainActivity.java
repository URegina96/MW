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

import java.util.ArrayList; // Импортируем класс для работы с массивами

public class MainActivity extends AppCompatActivity {

    private ArrayList<Note> notes; // Список для хранения заметок
    private NoteAdapter adapter; // Адаптер для отображения заметок в списке
    private RecyclerView recyclerView; // Вид для отображения списка
    private EditText editTitle, editDescription; // Поля ввода для заголовка и описания заметки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Вызов родительского метода
        setContentView(R.layout.activity_main); // Установка разметки для активности

        editTitle = findViewById(R.id.editTitle); // Находим поле для заголовка
        editDescription = findViewById(R.id.editDescription); // Находим поле для описания
        Button buttonSave = findViewById(R.id.buttonSave); // Находим кнопку сохранения
        recyclerView = findViewById(R.id.recyclerViewNotes); // Находим RecyclerView для заметок

        notes = new ArrayList<>(); // Инициализируем список заметок
        adapter = new NoteAdapter(notes, this); // Создаем адаптер с пустым списком
        recyclerView.setAdapter(adapter); // Устанавливаем адаптер для RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Устанавливаем менеджер расположения для списка

        buttonSave.setOnClickListener(new View.OnClickListener() { // Устанавливаем слушатель для кнопки сохранения
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString(); // Получаем текст заголовка
                String description = editDescription.getText().toString(); // Получаем текст описания

                if (!title.isEmpty() && !description.isEmpty()) { // Проверяем, что поля не пустые
                    notes.add(0, new Note(title, description)); // Создаем и добавляем новую заметку в начало списка
                    adapter.notifyDataSetChanged(); // Уведомляем адаптер о изменении данных
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
                adapter.removeItem(position); // Удаляем элемент из адаптера
            }
        }).attachToRecyclerView(recyclerView); // Привязываем жесты к RecyclerView
    }
}
