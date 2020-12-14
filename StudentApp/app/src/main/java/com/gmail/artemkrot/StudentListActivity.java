package com.gmail.artemkrot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class StudentListActivity extends Activity {
    public static final String APP_PREFERENCES_SEARCH_FILTER = "SEARCH_FILTER";
    private StudentRepository studentRepository = StudentRepository.getInstance();
    private EditText editText;
    private RecyclerView recyclerView;
    private StudentRecyclerViewAdapter adapter;
    private Button studentAddButton;
    private SharedPreferences activityPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initVerbals();
        initEditTextListener();
        initAddListener();
        initRecyclerView();
    }

    private void initVerbals() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        studentAddButton = (Button) findViewById(R.id.student_add_button);
        editText = (EditText) findViewById(R.id.edit_text_search);
        activityPreferences = getPreferences(Activity.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(studentRepository.getAllSortByName());
        if (activityPreferences.contains(APP_PREFERENCES_SEARCH_FILTER)) {
            editText.setText(activityPreferences.getString(APP_PREFERENCES_SEARCH_FILTER, ""));
        }
        adapter.getFilter().filter(editText.getText().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveActivityPreferences();
    }

    protected void saveActivityPreferences() {
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(APP_PREFERENCES_SEARCH_FILTER, editText.getText().toString());
        editor.apply();
    }

    private void initAddListener() {
        View.OnClickListener studentAddButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentListActivity.this, StudentEditActivity.class);
                intent.putExtra(StudentEditActivity.EDIT_STUDENT_ID, StudentEditActivity.ADD_NEW_STUDENT);
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
                intent.putExtra(StudentDetailsActivity.DETAILS_STUDENT_ID, studentId);
                startActivity(intent);
            }
        };
        adapter = new StudentRecyclerViewAdapter(studentRepository.getAllSortByName(), onStudentClickListener);
        recyclerView.setAdapter(adapter);
    }
}