package com.gmail.artemkrot.repository;

import com.gmail.artemkrot.repository.model.Student;
import com.gmail.artemkrot.util.RandomStudent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentRepository {
    private static final int COUNT_RANDOM_STUDENTS = 10;
    private final static StudentRepository instance = new StudentRepository();
    private final List<Student> students = new ArrayList<>();
    private long id = 0L;

    private StudentRepository() {

        for (int i = 0; i < COUNT_RANDOM_STUDENTS; i++) {
            Student randomStudent = RandomStudent.getRandomStudent();
            add(randomStudent);
        }
    }

    public static StudentRepository getInstance() {
        return instance;
    }

    public Student add(Student student) {
        id++;
        student.setId(id);
        students.add(student);
        return student;
    }

    public Student findById(Long id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllSortByName() {
        List<Student> sortedStudents = new ArrayList<>(students);
        Collections.sort(sortedStudents, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return sortedStudents;
    }

    public void delete(Long id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                break;
            }
        }
    }

    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                break;
            }
        }
    }

    public long getFirstStudentId() {
        if (!students.isEmpty()) {
            return students.get(0).getId();
        }
        return -1;
    }
}