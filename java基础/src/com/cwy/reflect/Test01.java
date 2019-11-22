package com.cwy.reflect;

public class Test01 {
    public static void main(String[] args) throws ClassNotFoundException {
        Person p1 = new Person();
        Person p2 = new Person();

        //第一种方法：getClass
        Class class1 = p1.getClass();
        Class class2 = p1.getClass();
        System.out.println("class1: " + class1);
        System.out.println("class2: " + class2);
        System.out.println(class1 == class2);

        Class class3 = p2.getClass();
        System.out.println("class3: " + class3);
        System.out.println(class1 == class3);

        //第二种方法： 类型.class
        Class class4 = Person.class;
        System.out.println("class4: " + class4);
        //也可以是基本数据类型
        Class class5 = int.class;

        //第三种方法：需要传入类全路径 Class.forName("类型名(全路径名)");
        //需要抛出异常，因为可能找不到类名
        Class class6 = Class.forName("com.cwy.reflect.Person");
        System.out.println(class6 == class1);

    }
}
