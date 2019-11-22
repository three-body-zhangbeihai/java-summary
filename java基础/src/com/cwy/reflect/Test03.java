package com.cwy.reflect;

import java.lang.reflect.Constructor;

public class Test03 {
    public static void main(String[] args) throws Exception {
        //得到字节码文件对象
        Class clazz = Class.forName("com.cwy.reflect.Student");

        //得到字节码文件对象的构造器对象
        /* getConstructors() 只能得到公共的构造器 public修饰的 */
        Constructor[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);// Student中public修饰的公共构造器只有一个，所以length是1

        /* getDeclaredConstructors() 得到不管是共有的还是私有的构造器 */
        Constructor[] constructors2 = clazz.getDeclaredConstructors();
        System.out.println(constructors2.length); //所以length是 4

        /* 得到指定的构造器
        *   public Student(int age){}
        * */
        Constructor constructor = clazz.getConstructor(int.class);
        Object newInstance = constructor.newInstance(18);//传实参
        System.out.println(newInstance); //Student{age=18, name='null'}

        Constructor constructor1 = clazz.getDeclaredConstructor(int.class,String.class);
        //会报错。虽然得到私有构造器，但是并不能创建对象
        //Object newInstance1 = constructor1.newInstance(18, "wenhuohuo");
        // 设置暴力访问(允许访问设为true)
        constructor1.setAccessible(true);
        //这时就可以访问创建对象了
        Object newInstance1 = constructor1.newInstance(18, "wenhuohuo");
        System.out.println(newInstance1);//Student{age=18, name='wenhuohuo'}
    }
}
