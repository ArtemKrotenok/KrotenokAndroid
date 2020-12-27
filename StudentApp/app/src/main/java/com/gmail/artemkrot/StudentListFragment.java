package com.gmail.artemkrot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.repository.StudentRepository;
import com.gmail.artemkrot.repository.model.Student;

import java.util.List;

public class StudentListFragment extends Fragment {
    public static final String APP_PREFERENCES_SEARCH_FILTER = "SEARCH_FILTER";
    private StudentRepository studentRepository = StudentRepository.getInstance();
    private EditText editText;
    private RecyclerView recyclerView;
    private StudentRecyclerViewAdapter adapter;
    private Button studentAddButton;
    private SharedPreferences activityPreferences;
    private Context context;
    private OnSelectedListener selectedListener;

    public void update() {
        adapter.clear();
        adapter.addAll(studentRepository.getAllSortByName());
        if (activityPreferences.contains(APP_PREFERENCES_SEARCH_FILTER)) {
            editText.setText(activityPreferences.getString(APP_PREFERENCES_SEARCH_FILTER, ""));
            adapter.getFilter().filter(editText.getText().toString());
        }
    }

    public long getFirstStudentId() {
        List<Student> studentList = studentRepository.getAllSortByName();
        if (!studentList.isEmpty()) {
            return studentList.get(0).getId();
        }
        return -1;
    }

    public interface OnSelectedListener {
        void onDetailsStudent(long studentId);

        void onAddStudent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectedListener = (OnSelectedListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_list, container, false);
        context = container.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initVerbals(view);
        initEditTextListener();
        initAddListener();
        initRecyclerView();
    }

    private void initVerbals(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        studentAddButton = (Button) view.findViewById(R.id.student_add_button);
        editText = (EditText) view.findViewById(R.id.edit_text_search);
        activityPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onPause() {
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
                selectedListener.onAddStudent();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        StudentRecyclerViewAdapter.OnStudentClickListener onStudentClickListener = new StudentRecyclerViewAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(long studentId) {
                selectedListener.onDetailsStudent(studentId);
            }
        };
        adapter = new StudentRecyclerViewAdapter(studentRepository.getAllSortByName(), onStudentClickListener);
        recyclerView.setAdapter(adapter);
    }
}