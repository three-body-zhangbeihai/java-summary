package com.whh.bean;

import org.springframework.stereotype.Component;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 16:55
 **/

public class Person {
    private Integer age;
    private String name;

    public Person(){
        super();
    }
    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
