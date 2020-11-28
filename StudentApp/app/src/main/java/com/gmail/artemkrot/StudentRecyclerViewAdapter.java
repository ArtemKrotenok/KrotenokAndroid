package com.gmail.artemkrot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.artemkrot.repository.model.Student;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentRecyclerViewAdapter
        extends RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolder>
        implements Filterable {
    private List<Student> studentList;
    private List<Student> filteredStudentList;
    private OnStudentClickListener onStudentClickListener;

    StudentRecyclerViewAdapter(List<Student> studentList, OnStudentClickListener onStudentClickListener) {
        this.studentList = studentList;
        this.filteredStudentList = studentList;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, final int position) {
        final Student student = filteredStudentList.get(position);
        Picasso.get()
                .load(student.getImageUrl())
                .into(holder.studentImage);
        holder.studentName.setText(student.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStudentClickListener.onStudentClick(student.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredStudentList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredStudentList = studentList;
                } else {
                    List<Student> filteredList = new ArrayList<>();
                    for (Student student : studentList) {
                        if (student.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(student);
                        }
                    }
                    filteredStudentList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredStudentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredStudentList = (ArrayList<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private ImageView studentImage;
        private TextView studentName;

        StudentViewHolder(View itemView) {
            super(itemView);
            studentImage = itemView.findViewById(R.id.item_student_image);
            studentName = itemView.findViewById(R.id.item_student_name);
        }
    }

    public interface OnStudentClickListener {
        void onStudentClick(long studentId);
    }
}