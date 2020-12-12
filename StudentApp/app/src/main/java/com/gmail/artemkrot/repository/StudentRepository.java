package com.gmail.artemkrot.repository;

import com.gmail.artemkrot.repository.model.Student;
import com.gmail.artemkrot.util.RandomStudent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentRepository {
    private static final int COUNT_RANDOM_STUDENTS = 10;
    private static StudentRepository instance;
    private List<Student> students = new ArrayList<>();
    private long id = 0L;

    private StudentRepository() {

        for (int i = 0; i < COUNT_RANDOM_STUDENTS; i++) {
            Student randomStudent = RandomStudent.getRandomStudent();
            add(randomStudent);
        }
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    public void add(Student student) {
        id++;
        student.setId(id);
        students.add(student);
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
        Student student = findById(id);
        students.remove(student);
    }

    public void update(Student student) {
        Student savedStudent = findById(student.getId());
        students.remove(savedStudent);
        students.add(student);
    }
}