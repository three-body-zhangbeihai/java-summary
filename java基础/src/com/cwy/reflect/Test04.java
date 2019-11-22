package com.cwy.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Class.forName("com.cwy.reflect.Car");

        //得到字节码文件对象中的所有Method对象
        //不带Declared，是得到素有的public修饰的method，包括从父类继承过来的public的method方法
        Method[] methods = clazz.getMethods();
        /**
         * public java.lang.String com.cwy.reflect.Car.run(java.lang.String)
         * public java.lang.String com.cwy.reflect.Car.toString()
         * public java.lang.String com.cwy.reflect.Car.getName()
         * public void com.cwy.reflect.Car.setName(java.lang.String)
         * public void com.cwy.reflect.Car.setPrice(int)
         * public int com.cwy.reflect.Car.getPrice()
         * public final void java.lang.Object.wait() throws java.lang.InterruptedException
         * public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
         * public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
         * public boolean java.lang.Object.equals(java.lang.Object)
         * public native int java.lang.Object.hashCode()
         * public final native java.lang.Class java.lang.Object.getClass()
         * public final native void java.lang.Object.notify()
         * public final native void java.lang.Object.notifyAll()
         */
        for(Method method:methods){
            System.out.println(method);
        }
        System.out.println("---------------------------------");
        //带Declared是得到当前类中所有的Method方法
        Method[] methods1 = clazz.getDeclaredMethods();
        /**
         * public java.lang.String com.cwy.reflect.Car.run(java.lang.String)
         * public java.lang.String com.cwy.reflect.Car.toString()
         * public java.lang.String com.cwy.reflect.Car.getName()
         * public void com.cwy.reflect.Car.setName(java.lang.String)
         * private java.lang.String com.cwy.reflect.Car.stop(java.lang.String)
         * public void com.cwy.reflect.Car.setPrice(int)
         * public int com.cwy.reflect.Car.getPrice()
         */
        for(Method method: methods1){
            System.out.println(method);
        }
        System.out.println("----------------------------");
        //得到特定的方法
        Method method1 = clazz.getMethod("run",String.class);
        System.out.println(method1);
        //反射得到对象
        //直接clazz.newInstance得到对象，底层和前面先得到构造器对象，在得到普通类对象一样，
        //不过如果类中没有空参数的构造器，那么，clazz.newInstance会报错。
        Object newInstance = clazz.newInstance();

        //调用方法
        //传统方式：对象.方法(实参)
        //反射方法：方法.invoke(对象，实参)
        Object res = method1.invoke(newInstance,"特斯拉");
        System.out.println(res);

        //同样的，需要通过Declared的方式得到私有的方法
        //同样需要设置访问权限
        Method method2 = clazz.getDeclaredMethod("stop",String.class);
        method2.setAccessible(true);
        Object newInstance2 = clazz.newInstance();
        Object res2 = method2.invoke(newInstance2,"特斯拉");
        System.out.println(res2);
    }
}
