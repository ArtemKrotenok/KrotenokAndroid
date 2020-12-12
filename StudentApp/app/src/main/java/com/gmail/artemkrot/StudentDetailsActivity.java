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
    public static final String DETAILS_STUDENT_ID = "details_student_id";
    private static final long DEFAULT_VALUE_STUDENT_ID = 0L;
    private Button buttonEditStudent;
    private Button buttonDeleteStudent;
    private TextView textViewStudentName;
    private TextView textViewStudentAge;
    private ImageView imageViewStudent;
    private StudentRepository studentRepository = StudentRepository.getInstance();
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
        studentId = getIntent().getLongExtra(DETAILS_STUDENT_ID, DEFAULT_VALUE_STUDENT_ID);
    }

    private void setValue() {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.text_message_student_not_found),
                    Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
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
                handleDeleteOnClick();
            }
        };

        buttonDeleteStudent.setOnClickListener(buttonDeleteStudentOnClickListener);

        View.OnClickListener buttonEditStudentOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditOnClick();
            }
        };
        buttonEditStudent.setOnClickListener(buttonEditStudentOnClickListener);
    }

    private void handleEditOnClick() {
        Intent intent = new Intent(StudentDetailsActivity.this,
                StudentEditActivity.class);
        intent.putExtra(StudentEditActivity.EDIT_STUDENT_ID, studentId);
        startActivity(intent);
    }

    private void handleDeleteOnClick() {
        studentRepository.delete(studentId);
        Toast toast = Toast.makeText(getApplicationContext(),
                getString(R.string.text_message_student_delete),
                Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}