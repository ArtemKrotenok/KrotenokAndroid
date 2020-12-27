package com.gmail.artemkrot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gmail.artemkrot.repository.StudentRepository;
import com.gmail.artemkrot.repository.model.Student;
import com.squareup.picasso.Picasso;

public class StudentDetailsFragment extends Fragment {
    public static final String DETAILS_STUDENT_ID = "details_student_id";
    public static final long DEFAULT_VALUE_STUDENT_ID = 0L;
    private Button buttonEditStudent;
    private Button buttonDeleteStudent;
    private TextView textViewStudentName;
    private TextView textViewStudentAge;
    private ImageView imageViewStudent;
    private StudentRepository studentRepository = StudentRepository.getInstance();
    private long studentId;
    private Context context;
    private OnSelectedListener selectedListener;

    public static StudentDetailsFragment newInstance(long detailsStudentId) {
        StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(DETAILS_STUDENT_ID, detailsStudentId);
        studentDetailsFragment.setArguments(bundle);
        return studentDetailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectedListener = (OnSelectedListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_details, container, false);
        context = container.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initVerbals(view);
        initListeners();
        setValue();
    }

    private void initVerbals(View view) {
        imageViewStudent = (ImageView) view.findViewById(R.id.image_view_student_details);
        textViewStudentName = (TextView) view.findViewById(R.id.text_view_student_name_details);
        textViewStudentAge = (TextView) view.findViewById(R.id.text_view_student_age_details);
        buttonEditStudent = (Button) view.findViewById(R.id.button_student_edit);
        buttonDeleteStudent = (Button) view.findViewById(R.id.button_student_delete);
        studentId = getArguments().getLong(DETAILS_STUDENT_ID);
    }

    private void setValue() {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.text_message_student_not_found),
                    Toast.LENGTH_SHORT);
            toast.show();
            textViewStudentName.setText("Student not selected");
            textViewStudentAge.setText("");
            imageViewStudent.setImageDrawable(null);
        } else {
            textViewStudentName.setText(student.getName());
            textViewStudentAge.setText(student.getAge().toString());
            Picasso.get()
                    .load(student.getImageUrl())
                    .into(imageViewStudent);
        }
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
        Intent intent = new Intent(getActivity(), StudentEditActivity.class);
        intent.putExtra(StudentEditActivity.EDIT_STUDENT_ID, studentId);
        startActivity(intent);
    }

    private void handleDeleteOnClick() {
        studentRepository.delete(studentId);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                getString(R.string.text_message_student_delete),
                Toast.LENGTH_SHORT);
        toast.show();
        selectedListener.onFinishStudentDetailFragment();
    }

    public void update(long studentId) {
        this.studentId = studentId;
        setValue();
    }

    public interface OnSelectedListener {
        void onFinishStudentDetailFragment();
    }
}