package com.example.myinjection;

public class Course {
    private String name;
    private String level;
    private String duration;

    public Course(String name, String level, String duration) {
        this.name = name;
        this.level = level;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
