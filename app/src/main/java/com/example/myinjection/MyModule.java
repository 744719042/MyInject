package com.example.myinjection;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyModule {

    @Provides
    public Student getStudent() {
        return new Student(1, "zhangsan", 18);
    }

    @Provides
    @Singleton
    public Teacher getTeacher(Course course) {
        return new Teacher("教授", "Thomas", course);
    }

    @Provides
    public IDriver getDatabasesDriver() {
        return new SQLiteDriver();
    }

    @Provides
    public Course getCourse() {
        return new Course("计算机科学导论", "简单", "60");
    }
}
