package com.gmail.artemkrot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gmail.artemkrot.repository.StudentRepository;
import com.gmail.artemkrot.repository.model.Student;
import com.squareup.picasso.Picasso;

public class StudentDetailsActivity extends Activity {
    private static final String TEXT_MESSAGE_STUDENT_DELETE = "Student was deleted";
    private Button buttonEditStudent;
    private Button buttonDeleteStudent;
    private TextView textViewStudentName;
    private TextView textViewStudentAge;
    private ImageView imageViewStudent;
    private StudentRepository studentRepository;
    private long studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        initVerbals();
        initListeners();
        setValue();
    }

    private void initVerbals() {
        imageViewStudent = (ImageView) findViewById(R.id.image_view_student_details);
        textViewStudentName = (TextView) findViewById(R.id.text_view_student_name_details);
        textViewStudentAge = (TextView) findViewById(R.id.text_view_student_age_details);
        buttonEditStudent = (Button) findViewById(R.id.button_student_edit);
        buttonDeleteStudent = (Button) findViewById(R.id.button_student_delete);
        studentRepository = StudentRepository.getInstance();
        Bundle arguments = getIntent().getExtras();
        studentId = Long.parseLong(arguments.get(StudentListActivity.STUDENT_ID).toString());
    }

    private void setValue() {
        Student student = studentRepository.findById(studentId);
        textViewStudentName.setText(student.getName());
        textViewStudentAge.setText(student.getAge().toString());
        Picasso.get()
                .load(student.getImageUrl())
                .into(imageViewStudent);
    }

    private void initListeners() {
        View.OnClickListener buttonDeleteStudentOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonDeleteStudentOnClick();
            }
        };

        buttonDeleteStudent.setOnClickListener(buttonDeleteStudentOnClickListener);

        View.OnClickListener buttonEditStudentOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonEditStudentOnClick();
            }
        };
        buttonEditStudent.setOnClickListener(buttonEditStudentOnClickListener);
    }

    private void handleButtonEditStudentOnClick() {
        Intent intent = new Intent(StudentDetailsActivity.this,
                StudentEditActivity.class);
        intent.putExtra(StudentListActivity.STUDENT_ID, studentId);
        startActivity(intent);
    }

    private void handleButtonDeleteStudentOnClick() {
        studentRepository.delete(studentId);
        Toast toast = Toast.makeText(getApplicationContext(),
                TEXT_MESSAGE_STUDENT_DELETE, Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(StudentDetailsActivity.this,
                StudentListActivity.class);
        startActivity(intent);
        finish();
    }
}