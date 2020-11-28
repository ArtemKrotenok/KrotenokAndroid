package com.gmail.artemkrot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.repository.StudentRepository;
import com.gmail.artemkrot.repository.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends Activity {

    public static final String STUDENT_ID = "student_id";
    private StudentRepository studentRepository;
    private EditText editText;
    private List<Student> studentList;
    private RecyclerView recyclerView;
    private StudentRecyclerViewAdapter adapter;
    private Button studentAddButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initVerbals();
        initEditTextListener();
        initStudentAddButtonListener();
        initRecyclerView();
    }

    private void initVerbals() {
        studentRepository = StudentRepository.getInstance();
        studentList = new ArrayList<>();
        studentList.addAll(studentRepository.getAllSortByName());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        studentAddButton = (Button) findViewById(R.id.student_add_button);
        editText = (EditText) findViewById(R.id.edit_text_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentList.clear();
        studentList.addAll(studentRepository.getAllSortByName());
        adapter.notifyDataSetChanged();
    }

    private void initStudentAddButtonListener() {
        View.OnClickListener studentAddButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentListActivity.this, StudentEditActivity.class);
                intent.putExtra(STUDENT_ID, "0");
                startActivity(intent);
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

    private void initRecyclerView() {
        RecyclerView.ItemDecoration itemDecoration = new StudentItemDecoration();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        StudentRecyclerViewAdapter.OnStudentClickListener onStudentClickListener = new StudentRecyclerViewAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(long studentId) {
                Intent intent = new Intent(StudentListActivity.this, StudentDetailsActivity.class);
                intent.putExtra(STUDENT_ID, studentId);
                startActivity(intent);
            }
        };
        adapter = new StudentRecyclerViewAdapter(studentList, onStudentClickListener);
        recyclerView.setAdapter(adapter);
    }
}