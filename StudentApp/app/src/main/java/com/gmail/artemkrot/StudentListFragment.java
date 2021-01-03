package com.gmail.artemkrot;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.repository.StudentRepository;

public class StudentListFragment extends Fragment {

    private final StudentRepository studentRepository = StudentRepository.getInstance();
    private PreferencesRepository preferencesRepository;
    private StudentRecyclerViewAdapter adapter;
    private OnStudentListListener selectedListener;

    private RecyclerView recyclerView;
    private EditText editText;
    private Button studentAddButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initVerbals(view);
        initEditTextListener();
        initAddListener();
        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferencesRepository = new PreferencesRepository(getContext().getApplicationContext());
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof OnStudentDetailsListener) {
            selectedListener = (OnStudentListListener) fragmentActivity;
        } else throw new IllegalArgumentException("not implement interface");
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onPause() {
        super.onPause();
        preferencesRepository.saveSearchValue(editText.getText().toString());
    }

    public void update() {
        adapter.clear();
        adapter.addAll(studentRepository.getAllSortByName());
        setSearchValue();
    }

    private void initVerbals(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        studentAddButton = (Button) view.findViewById(R.id.student_add_button);
        editText = (EditText) view.findViewById(R.id.edit_text_search);
    }

    private void initRecyclerView() {
        RecyclerView.ItemDecoration itemDecoration = new StudentItemDecoration();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        StudentRecyclerViewAdapter.OnStudentClickListener onStudentClickListener = new StudentRecyclerViewAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(long studentId) {
                selectedListener.onDetailsStudent(studentId);
            }
        };
        adapter = new StudentRecyclerViewAdapter(studentRepository.getAllSortByName(), onStudentClickListener);
        recyclerView.setAdapter(adapter);
    }

    private void setSearchValue() {
        String searchValue = preferencesRepository.getSearchValue();
        editText.setText(searchValue);
        adapter.getFilter().filter(searchValue);
    }


    private void initAddListener() {
        View.OnClickListener studentAddButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedListener.onAddStudent();
            }
        };
        studentAddButton.setOnClickListener(studentAddButtonOnClickListener);
    }

    private void initEditTextListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = editText.getText().toString();
                adapter.getFilter().filter(query);
            }
        });
    }
}