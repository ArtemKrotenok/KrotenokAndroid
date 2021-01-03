package com.gmail.artemkrot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.gmail.artemkrot.repository.StudentRepository;

public class StudentListActivity extends FragmentActivity
        implements OnStudentListListener, OnStudentDetailsListener {

    private StudentListFragment studentListFragment;
    private boolean isLand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        isLand = findViewById(R.id.fragment_student_details_placeholder) != null;
        if (savedInstanceState == null) {
            initFragments();
        }
    }

    @Override
    public void onDetailsStudent(long studentId) {
        if (isLand) {
            startNewStudentDetailsFragment(studentId);
        } else {
            StudentDetailsActivity.start(this, studentId);
        }
    }

    @Override
    public void onAddStudent() {
        StudentEditActivity.start(this, StudentEditActivity.ADD_NEW_STUDENT);
    }

    @Override
    public void onFinishStudentDetailFragment() {
        studentListFragment.update();
        startNewStudentDetailsFragment(StudentRepository.getInstance().getFirstStudentId());
    }

    private void initFragments() {
        studentListFragment = new StudentListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_student_list_placeholder, studentListFragment);
        if (isLand) {
            StudentDetailsFragment studentDetailsFragment =
                    StudentDetailsFragment.newInstance(StudentRepository.getInstance().getFirstStudentId());
            fragmentTransaction.replace(R.id.fragment_student_details_placeholder, studentDetailsFragment);
        }
        fragmentTransaction.commit();
    }

    private void startNewStudentDetailsFragment(long studentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        StudentDetailsFragment studentDetailsFragment =
                StudentDetailsFragment.newInstance(studentId);
        fragmentTransaction.replace(R.id.fragment_student_details_placeholder, studentDetailsFragment);
        fragmentTransaction.commit();
    }
}