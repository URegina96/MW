package com.example.mw;

import android.os.Bundle; // Импортируем класс для работы с данными
import android.view.LayoutInflater; // Импортируем класс для создания разметки
import android.view.View; // Импортируем класс для работы с пользовательским интерфейсом
import android.view.ViewGroup; // Импортируем класс для работы с группами видов
import android.widget.TextView; // Импортируем класс для отображения текста

import androidx.annotation.NonNull; // Импортируем аннотацию для не-null параметров
import androidx.annotation.Nullable; // Импортируем аннотацию для nullable параметров
import androidx.fragment.app.DialogFragment; // Импортируем класс для создания диалоговых фрагментов

public class NoteDetailFragment extends DialogFragment { // Класс фрагмента для отображения деталей заметки

    private static final String ARG_NOTE_TITLE = "note_title"; // Аргумент для заголовка заметки
    private static final String ARG_NOTE_DESCRIPTION = "note_description"; // Аргумент для описания заметки

    public static NoteDetailFragment newInstance(String title, String description) { // Статический метод для создания нового экземпляра фрагмента
        NoteDetailFragment fragment = new NoteDetailFragment(); // Создаем новый экземпляр фрагмента
        Bundle args = new Bundle(); // Создаем Bundle для передачи параметров
        args.putString(ARG_NOTE_TITLE, title); // Сохраняем заголовок заметки в аргументы
        args.putString(ARG_NOTE_DESCRIPTION, description); // Сохраняем описание заметки в аргументы
        fragment.setArguments(args); // Устанавливаем аргументы для фрагмента
        return fragment; // Возвращаем созданный фрагмент
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // Метод для создания представления фрагмента
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false); // Создаем разметку для фрагмента

        TextView titleView = view.findViewById(R.id.noteTitle); // Находим TextView для заголовка заметки
        TextView descriptionView = view.findViewById(R.id.noteDescription); // Находим TextView для описания заметки

        if (getArguments() != null) { // Проверяем, были ли переданы аргументы
            titleView.setText(getArguments().getString(ARG_NOTE_TITLE)); // Устанавливаем заголовок в TextView
            descriptionView.setText(getArguments().getString(ARG_NOTE_DESCRIPTION)); // Устанавливаем описание в TextView
        }

        return view; // Возвращаем созданное представление фрагмента
    }
}
