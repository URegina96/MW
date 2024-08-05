package com.example.mw;

import android.os.Bundle; // Для работы с данными
import android.view.LayoutInflater; // Для создания разметки
import android.view.View; // Для работы с видами
import android.view.ViewGroup; // Для работы с группами видов
import android.widget.TextView; // Для отображения текста
import androidx.annotation.NonNull; // Импорт аннотации
import androidx.annotation.Nullable; // Импорт аннотации
import androidx.fragment.app.DialogFragment; // Импорт фрагмента

public class NoteDetailFragment extends DialogFragment {

    private static final String ARG_NOTE_TITLE = "note_title"; // Аргумент для заголовка заметки
    private static final String ARG_NOTE_DESCRIPTION = "note_description"; // Аргумент для описания заметки

    public static NoteDetailFragment newInstance(String title, String description) {
        NoteDetailFragment fragment = new NoteDetailFragment(); // Создаем новый экземпляр фрагмента
        Bundle args = new Bundle(); // Создаем Bundle для передачи данных
        args.putString(ARG_NOTE_TITLE, title); // Добавляем заголовок
        args.putString(ARG_NOTE_DESCRIPTION, description); // Добавляем описание
        fragment.setArguments(args); // Устанавливаем аргументы для фрагмента
        return fragment; // Возвращаем новый фрагмент
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false); // Создаем разметку для фрагмента

        TextView titleView = view.findViewById(R.id.noteTitle); // Находим TextView для заголовка
        TextView descriptionView = view.findViewById(R.id.noteDescription); // Находим TextView для описания

        if (getArguments() != null) { // Проверяем, есть ли аргументы
            titleView.setText(getArguments().getString(ARG_NOTE_TITLE)); // Устанавливаем заголовок
            descriptionView.setText(getArguments().getString(ARG_NOTE_DESCRIPTION)); // Устанавливаем описание
        }

        return view; // Возвращаем разметку фрагмента
    }
}
