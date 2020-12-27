package com.gmail.artemkrot;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class StudentListActivity extends FragmentActivity
        implements StudentListFragment.OnSelectedListener, StudentDetailsFragment.OnSelectedListener {
    private StudentListFragment studentListFragment;
    private StudentDetailsFragment studentDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentListFragment = new StudentListFragment();
        setContentView(R.layout.activity_student_list);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_student_list_placeholder, studentListFragment);
        if (isLandscape()) {
            studentDetailsFragment = StudentDetailsFragment.newInstance(studentListFragment.getFirstStudentId());
            fragmentTransaction.add(R.id.fragment_student_details_placeholder, studentDetailsFragment);
        }
        fragmentTransaction.commit();
    }

    private boolean isLandscape() {
        int orientation = this.getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onDetailsStudent(long studentId) {
        if (studentDetailsFragment != null || isLandscape()) {
            studentDetailsFragment.update(studentId);
        } else {
            Intent intent = new Intent(StudentListActivity.this, StudentDetailsActivity.class);
            intent.putExtra(StudentDetailsFragment.DETAILS_STUDENT_ID, studentId);
            startActivity(intent);
        }
    }

    @Override
    public void onAddStudent() {
        Intent intent = new Intent(StudentListActivity.this, StudentEditActivity.class);
        intent.putExtra(StudentEditActivity.EDIT_STUDENT_ID, StudentEditActivity.ADD_NEW_STUDENT);
        startActivity(intent);
    }

    @Override
    public void onFinishStudentDetailFragment() {
        studentListFragment.update();
        studentDetailsFragment.update(studentListFragment.getFirstStudentId());
    }
}