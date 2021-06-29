package com.liuh.learn;

import android.util.Log;

public class Student extends Person {

    private static final String TAG = "Student";

    String name;

    public Student(String name) {
        Log.i(TAG, "Student, 构造方法...");
        this.name = name;
    }
}
