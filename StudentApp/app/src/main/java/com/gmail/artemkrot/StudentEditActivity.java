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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentEditActivity extends Activity {
    private static final String TEXT_MESSAGE_ALL_FIELDS_MUST_BE_FILLED = "All fields must be filled";
    private static final String TEXT_MESSAGE_UNCORRECTED_IMAGE_URL = "Uncorrected Image URL";
    private static final String TEXT_MESSAGE_UNCORRECTED_AGE = "Uncorrected AGE";
    private static final String PATTERN_REGEX_IMAGE_URL = "^http.+|^www.+";
    private static final int MIN_STUDENT_AGE = 18;
    private static final int MAX_STUDENT_AGE = 100;
    private static final String TEXT_MESSAGE_STUDENT_CHANGE_SAVED = "Change student data saved";
    private static final String TEXT_MESSAGE_STUDENT_ADD = "New student add";
    private Button buttonSaveStudent;
    private EditText editTextImageUrl;
    private EditText editTextName;
    private EditText editTextAge;
    private StudentRepository studentRepository;
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
        studentRepository = StudentRepository.getInstance();
        Bundle arguments = getIntent().getExtras();
        studentId = Long.parseLong(arguments.get(StudentListActivity.STUDENT_ID).toString());
        if (studentId != 0l) {
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
                handleButtonSaveStudentOnClick();
            }
        };
        buttonSaveStudent.setOnClickListener(buttonSaveStudentOnClickListener);
    }

    private void handleButtonSaveStudentOnClick() {
        if (inputValid()) {
            if (studentId == 0L) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        TEXT_MESSAGE_STUDENT_ADD, Toast.LENGTH_SHORT);
                toast.show();
                addNewStudentInBD();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        TEXT_MESSAGE_STUDENT_CHANGE_SAVED, Toast.LENGTH_SHORT);
                toast.show();
                updateStudentInBD(studentId);
            }
            Intent intent = new Intent(StudentEditActivity.this, StudentListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void updateStudentInBD(long studentId) {
        Student studentBD = studentRepository.findById(studentId);
        studentBD.setName(editTextName.getText().toString());
        studentBD.setImageUrl(editTextImageUrl.getText().toString());
        studentBD.setAge(Integer.parseInt(editTextAge.getText().toString()));
        studentRepository.update(studentBD);
    }

    private void addNewStudentInBD() {
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
            showErrorMessageEmptyField();
            return false;
        }
        int age = Integer.parseInt(editTextAge.getText().toString());
        if (age < MIN_STUDENT_AGE || age > MAX_STUDENT_AGE) {
            showErrorMessageUncorrectedAge();
            return false;
        }
        return true;
    }

    private boolean nameValid() {
        if (editTextName.getText().toString().equals("")) {
            showErrorMessageEmptyField();
            return false;
        }
        return true;
    }

    private boolean imageUrlValid() {
        if (editTextImageUrl.getText().toString().equals("")) {
            showErrorMessageEmptyField();
            return false;
        }
        String textImageUrl = editTextImageUrl.getText().toString();
        Pattern pattern = Pattern.compile(PATTERN_REGEX_IMAGE_URL);
        Matcher matcher = pattern.matcher(textImageUrl);
        if (!matcher.matches()) {
            showErrorMessageUncorrectedImageUrl();
            return false;
        }
        return true;
    }

    private void showErrorMessageUncorrectedImageUrl() {
        Toast toast = Toast.makeText(getApplicationContext(),
                TEXT_MESSAGE_UNCORRECTED_IMAGE_URL, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showErrorMessageEmptyField() {
        Toast toast = Toast.makeText(getApplicationContext(),
                TEXT_MESSAGE_ALL_FIELDS_MUST_BE_FILLED, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showErrorMessageUncorrectedAge() {
        Toast toast = Toast.makeText(getApplicationContext(),
                TEXT_MESSAGE_UNCORRECTED_AGE, Toast.LENGTH_SHORT);
        toast.show();
    }
}