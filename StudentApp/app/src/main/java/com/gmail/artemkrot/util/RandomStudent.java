package com.gmail.artemkrot.util;

import com.gmail.artemkrot.repository.model.Student;

import java.util.ArrayList;
import java.util.List;

public class RandomStudent {
    private static final int MIN_STUDENT_AGE = 18;
    private static final int MAX_STUDENT_AGE = 100;

    public static Student getRandomStudent() {
        Student randomStudent = new Student();
        randomStudent.setAge(getRandomStudentAge());
        randomStudent.setName(getRandomStudentName());
        randomStudent.setImageUrl(getRandomStudentImageUrl());
        return randomStudent;
    }

    private static String getRandomStudentName() {
        return "Name" + Math.abs(RandomUtil.getRandomNumber());
    }

    private static Integer getRandomStudentAge() {
        return RandomUtil.getRandomNumber(MIN_STUDENT_AGE, MAX_STUDENT_AGE);
    }

    private static String getRandomStudentImageUrl() {
        List<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511862_48.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511784_4.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511801_1.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511852_17.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511791_8.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511883_39.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511774_9.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511880_29.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511816_38.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551515640_40.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511867_41.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511901_42.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551515768_51.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511896_49.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511825_37.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511889_44.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511829_46.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511836_43.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511875_45.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511868_36.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511849_35.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511819_33.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511900_34.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511827_30.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551515619_53.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511876_31.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511862_28.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511845_24.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511818_27.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511820_23.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511856_25.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511862_19.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511873_18.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511789_7.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511835_22.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511851_21.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551515594_15.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511846_20.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511784_10.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551515501_13.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511793_14.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511866_11.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511808_5.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511809_6.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511823_2.jpg");
        imageUrlList.add("https://klike.net/uploads/posts/2019-03/1551511804_3.jpg");
        return imageUrlList.get(RandomUtil.getRandomNumber(0, imageUrlList.size() - 1));
    }
}