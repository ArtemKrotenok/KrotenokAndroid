package com.gmail.artemkrot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class StudentDetailsActivity extends FragmentActivity implements StudentDetailsFragment.OnSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        long studentId = getIntent().getLongExtra(StudentDetailsFragment.DETAILS_STUDENT_ID, StudentDetailsFragment.DEFAULT_VALUE_STUDENT_ID);
        StudentDetailsFragment studentDetailsFragment = StudentDetailsFragment.newInstance(studentId);
        fragmentTransaction.add(R.id.fragment_student_details_placeholder, studentDetailsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFinishStudentDetailFragment() {
        finish();
    }
}