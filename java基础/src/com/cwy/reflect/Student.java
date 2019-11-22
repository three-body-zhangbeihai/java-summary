package com.cwy.reflect;

public class Student {
    private int age;
    private String name;

    Student(){}

    public Student(int age){
        this.age = age;
    }

    protected Student(String name){
        this.name = name;
    }

    private Student(int age, String name){
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
