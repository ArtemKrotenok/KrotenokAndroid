package com.gmail.artemkrot.repository;

import com.gmail.artemkrot.repository.model.Student;
import com.gmail.artemkrot.util.RandomStudent;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StudentRepositoryTest {
    private static final int COUNT_RANDOM_STUDENT = 10;
    private static final String TEST_STUDENT_NAME = "Test Student Name";
    private static final int TEST_STUDENT_AGE = 88;
    private static final String TEST_STUDENT_URL = "https://nn.by/?c=ar&i=264821";
    private final StudentRepository studentRepository = StudentRepository.getInstance();

    @Test
    public void getInstance_isNotNull() {
        StudentRepository studentRepository = StudentRepository.getInstance();
        assertNotNull(studentRepository);
    }

    @Test
    public void add() {
        Student student = RandomStudent.getRandomStudent();
        Student studentDB = studentRepository.add(student);
        assertNotNull(student.getId());
        assertEquals(student.getName(), studentDB.getName());
        assertEquals(student.getAge(), studentDB.getAge());
        assertEquals(student.getImageUrl(), studentDB.getImageUrl());
    }

    @Test
    public void findById() {
        Student student = studentRepository.add(RandomStudent.getRandomStudent());
        Student studentDB = studentRepository.findById(student.getId());
        assertEquals(student.getId(), studentDB.getId());
        assertEquals(student.getName(), studentDB.getName());
        assertEquals(student.getAge(), studentDB.getAge());
        assertEquals(student.getImageUrl(), studentDB.getImageUrl());
    }

    @Test
    public void getAllSortByName() {
        for (int i = 0; i < COUNT_RANDOM_STUDENT; i++) {
            studentRepository.add(RandomStudent.getRandomStudent());
        }
        List<Student> studentList = studentRepository.getAllSortByName();
        assertNotNull(studentList);
        assertTrue(isSortedList(studentList));
    }

    private boolean isSortedList(List<Student> studentList) {
        Student lastStudent;
        Student nextStudent;
        for (int i = 0; i < studentList.size() - 1; i++) {
            lastStudent = studentList.get(i);
            nextStudent = studentList.get(i + 1);
            if (lastStudent.getName().compareToIgnoreCase(nextStudent.getName()) >= 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void delete() {
        Student student = studentRepository.add(RandomStudent.getRandomStudent());
        assertNotNull(studentRepository.findById(student.getId()));
        studentRepository.delete(student.getId());
        assertNull(studentRepository.findById(student.getId()));
    }

    @Test
    public void update() {
        Student student = studentRepository.add(RandomStudent.getRandomStudent());
        Student studentUpdate = new Student();
        studentUpdate.setId(student.getId());
        studentUpdate.setName(TEST_STUDENT_NAME);
        studentUpdate.setAge(TEST_STUDENT_AGE);
        studentUpdate.setImageUrl(TEST_STUDENT_URL);
        studentRepository.update(studentUpdate);
        Student studentDB = studentRepository.findById(studentUpdate.getId());
        assertEquals(studentUpdate.getId(), studentDB.getId());
        assertEquals(studentUpdate.getAge(), studentDB.getAge());
        assertEquals(studentUpdate.getName(), studentDB.getName());
        assertEquals(studentUpdate.getImageUrl(), studentDB.getImageUrl());
    }
}