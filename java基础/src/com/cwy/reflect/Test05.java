package com.cwy.reflect;

import java.lang.reflect.Field;

public class Test05 {
    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, InstantiationException {
        //同样以Car类来举例说明
        Class clazz = Class.forName("com.cwy.reflect.Car");

        //得到指定的Field。Declared的方式和其他两个一样
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);

        //调用属性：
        //传统方式：对象.属性名
        //反射方式：属性对象.set(对象,"value");
        Object newInstance = clazz.newInstance();
        name.set(newInstance,"特斯拉");
        System.out.println(newInstance);//Car{price=0, name='特斯拉'}

    }
}
