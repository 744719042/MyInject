package com.example.myinjection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Inject
    Student student;

    @Inject
    Teacher teacher;

    @Inject
    IDriver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerMyComponent.builder().myModule(new MyModule()).build().inject(this);
        Log.e(TAG, student.toString());
        Log.e(TAG, teacher.toString());
        Log.e(TAG, driver.toString());
    }
}
