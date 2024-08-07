package com.example.mw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static ArrayList<Note> notesList; // Список заметок для адаптера
    private Context context; // Контекст приложения

    public NoteAdapter(ArrayList<Note> notes, MainActivity context) { // Конструктор адаптера
        this.notesList = notes; // Инициализация списка заметок
        this.context = context; // Инициализация контекста
    }
    public void updateNotes(List<Note> notes) {
        notesList.clear(); // Очищаем текущий список
        notesList.addAll(notes); // Добавляем все новые заметки
        notifyDataSetChanged(); // Уведомляем адаптер о изменениях
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false); // Создаем элемент списка
        return new NoteViewHolder(view); // Возвращаем ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position); // Получаем заметку по позиции
        holder.textView.setText(note.getTitle()); // Устанавливаем заголовок в TextView

        holder.itemView.setOnClickListener(v -> {
            // Создаем и показываем NoteDetailFragment
            NoteDetailFragment detailFragment = NoteDetailFragment.newInstance(note.getTitle(), note.getDescription());
            detailFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "NoteDetailFragment"); // Показываем детальный фрагмент заметки
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size(); // Возвращаем размер списка заметок
    }

    public void removeItem(Note note) {
        notesList.remove(note); // Удаляем элемент из списка
        notifyDataSetChanged(); // Уведомляем адаптер о изменениях
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textView; // TextView для отображения заголовка

        NoteViewHolder(@NonNull View itemView) {
            super(itemView); // Вызов конструктора родителя
            textView = itemView.findViewById(android.R.id.text1); // Инициализируем TextView
        }
    }
    public Note getNoteAtPosition(int position) {
        return notesList.get(position); // Возвращаем заметку по позиции
    }
}
