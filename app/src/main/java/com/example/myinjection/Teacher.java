package com.example.myinjection;

public class Teacher {
    private String title;
    private String name;
    private Course course;

    public Teacher(String title, String name, Course course) {
        this.title = title;
        this.name = name;
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
