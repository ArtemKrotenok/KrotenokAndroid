package com.gmail.artemkrot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class StudentDetailsActivity extends FragmentActivity implements OnStudentDetailsListener {
    private static final String DETAILS_STUDENT_ID = "DETAILS_STUDENT_ID";
    private static final long DEFAULT_VALUE_STUDENT_ID = 0L;

    public static void start(Context context, long studentId) {
        Intent intent = new Intent(context, StudentDetailsActivity.class);
        intent.putExtra(StudentDetailsActivity.DETAILS_STUDENT_ID, studentId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        long studentId = getIntent().getLongExtra(StudentDetailsActivity.DETAILS_STUDENT_ID,
                DEFAULT_VALUE_STUDENT_ID);
        if (savedInstanceState == null) {
            initFragment(studentId);
        }
    }

    @Override
    public void onFinishStudentDetailFragment() {
        finish();
    }

    private void initFragment(long studentId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        StudentDetailsFragment studentDetailsFragment = StudentDetailsFragment.newInstance(studentId);
        fragmentTransaction.add(R.id.fragment_student_details_placeholder, studentDetailsFragment);
        fragmentTransaction.commit();
    }

}