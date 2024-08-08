package com.example.mw;

import android.content.Context; // Импортируем класс для работы с контекстом приложения
import android.view.LayoutInflater; // Импортируем класс для создания разметки
import android.view.View; // Импортируем класс для работы с пользовательским интерфейсом
import android.view.ViewGroup; // Импортируем класс для работы с группами видов
import android.widget.TextView; // Импортируем класс для отображения текста

import androidx.annotation.NonNull; // Импортируем аннотацию для обозначения не-null параметров
import androidx.appcompat.app.AppCompatActivity; // Импортируем класс для активностей с поддержкой ActionBar
import androidx.recyclerview.widget.RecyclerView; // Импортируем класс для отображения списков

import java.util.ArrayList; // Импортируем класс для работы с динамическими массивами
import java.util.List; // Импортируем интерфейс для работы со списками

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> { // Класс адаптера для RecyclerView

    private static ArrayList<Note> notesList; // Список заметок, использующийся адаптером
    private Context context; // Контекст приложения

    public NoteAdapter(ArrayList<Note> notes, MainActivity context) { // Конструктор адаптера
        this.notesList = notes; // Инициализируем список заметок
        this.context = context; // Инициализируем контекст
    }

    public void updateNotes(List<Note> notes) { // Метод для обновления списка заметок в адаптере
        notesList.clear(); // Очищаем текущий список заметок
        notesList.addAll(notes); // Добавляем новые заметки в список
        notifyDataSetChanged(); // Уведомляем адаптер о необходимости обновления UI
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Создаем ViewHolder для элемента списка
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false); // Создаем элемент списка
        return new NoteViewHolder(view); // Возвращаем новый ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) { // Делаем привязку элемента списка к данным
        Note note = notesList.get(position); // Получаем заметку по позиции
        holder.textView.setText(note.getTitle()); // Устанавливаем заголовок заметки в TextView

        holder.itemView.setOnClickListener(v -> { // Устанавливаем слушатель для клика по элементу списка
            // Создаем и показываем NoteDetailFragment
            NoteDetailFragment detailFragment = NoteDetailFragment.newInstance(note.getTitle(), note.getDescription());
            detailFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "NoteDetailFragment"); // Показываем детальный фрагмент заметки
        });
    }

    @Override
    public int getItemCount() { // Метод для получения общего числа заметок в списке
        return notesList.size(); // Возвращаем размер списка заметок
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder { // Внутренний класс ViewHolder для элемента списка
        TextView textView; // TextView для отображения заголовка заметки

        NoteViewHolder(@NonNull View itemView) { // Конструктор ViewHolder
            super(itemView); // Вызов конструктора родителя
            textView = itemView.findViewById(android.R.id.text1); // Инициализируем TextView для заголовка
        }
    }

    public Note getNoteAtPosition(int position) { // Метод для получения заметки по позиции
        return notesList.get(position); // Возвращаем заметку из списка
    }
}
