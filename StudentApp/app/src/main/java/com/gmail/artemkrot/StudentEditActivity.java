package com.gmail.artemkrot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gmail.artemkrot.repository.StudentRepository;
import com.gmail.artemkrot.repository.model.Student;
import com.gmail.artemkrot.util.ValidUtil;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class StudentEditActivity extends Activity {
    public static final String EDIT_STUDENT_ID = "edit_student_id";
    public static final long ADD_NEW_STUDENT = -1L;
    private static final long DEFAULT_VALUE_STUDENT_ID = 0L;
    private static final int MIN_STUDENT_AGE = 18;
    private static final int MAX_STUDENT_AGE = 100;
    private Button buttonSaveStudent;
    private EditText editTextImageUrl;
    private EditText editTextName;
    private EditText editTextAge;
    private StudentRepository studentRepository = StudentRepository.getInstance();
    private long studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        initButtonSaveStudent();
        initVerbals();
    }

    private void initVerbals() {
        editTextImageUrl = (EditText) findViewById(R.id.edit_text_image_url);
        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextAge = (EditText) findViewById(R.id.edit_text_age);
        studentId = getIntent().getLongExtra(EDIT_STUDENT_ID, DEFAULT_VALUE_STUDENT_ID);
        if (studentId != ADD_NEW_STUDENT) {
            setValue();
        }
    }

    private void setValue() {
        Student student = studentRepository.findById(studentId);
        editTextImageUrl.setText(student.getImageUrl());
        editTextName.setText(student.getName());
        editTextAge.setText(student.getAge().toString());
    }

    private void initButtonSaveStudent() {
        buttonSaveStudent = (Button) findViewById(R.id.student_save_button);
        View.OnClickListener buttonSaveStudentOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveOnClick();
            }
        };
        buttonSaveStudent.setOnClickListener(buttonSaveStudentOnClickListener);
    }

    private void handleSaveOnClick() {
        if (inputValid()) {
            if (studentId == ADD_NEW_STUDENT) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.text_message_student_add),
                        Toast.LENGTH_SHORT);
                toast.show();
                addNewStudent();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.text_message_student_change_saved),
                        Toast.LENGTH_SHORT);
                toast.show();
                updateStudent(studentId);
            }
            Intent intent = new Intent(StudentEditActivity.this, StudentListActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void updateStudent(long studentId) {
        Student student = studentRepository.findById(studentId);
        student.setName(editTextName.getText().toString());
        student.setImageUrl(editTextImageUrl.getText().toString());
        student.setAge(Integer.parseInt(editTextAge.getText().toString()));
        studentRepository.update(student);
    }

    private void addNewStudent() {
        Student newStudent = new Student();
        newStudent.setName(editTextName.getText().toString());
        newStudent.setImageUrl(editTextImageUrl.getText().toString());
        newStudent.setAge(Integer.parseInt(editTextAge.getText().toString()));
        studentRepository.add(newStudent);
    }

    private boolean inputValid() {
        return imageUrlValid() & nameValid() & ageValid();
    }

    private boolean ageValid() {
        if (editTextAge.getText().toString().equals("")) {
            showEmptyField();
            return false;
        }
        int age = Integer.parseInt(editTextAge.getText().toString());
        if (age < MIN_STUDENT_AGE || age > MAX_STUDENT_AGE) {
            showUncorrectedAge();
            return false;
        }
        return true;
    }

    private boolean nameValid() {
        if (editTextName.getText().toString().equals("")) {
            showEmptyField();
            return false;
        }
        return true;
    }

    private boolean imageUrlValid() {
        if (editTextImageUrl.getText().toString().equals("")) {
            showEmptyField();
            return false;
        }
        String textImageUrl = editTextImageUrl.getText().toString();
        if (!ValidUtil.isValidUrl(textImageUrl)) {
            showUncorrectedImageUrl();
            return false;
        }
        return true;
    }

    private void showUncorrectedImageUrl() {
        Toast toast = Toast.makeText(getApplicationContext(),
                getString(R.string.text_message_uncorrected_image_url), Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showEmptyField() {
        Toast toast = Toast.makeText(getApplicationContext(),
                getString(R.string.text_message_all_fields_must_be_filled), Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showUncorrectedAge() {
        Toast toast = Toast.makeText(getApplicationContext(),
                getString(R.string.text_message_uncorrected_age), Toast.LENGTH_SHORT);
        toast.show();
    }
}