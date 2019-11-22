package com.cwy.reflect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;

public class Test02 {
    public static void main(String[] args) throws Exception{
        //1.读取配置文件
        FileReader f = new FileReader("src/com/cwy/reflect/path.properties");
        BufferedReader br = new BufferedReader(f);
        //得到路径名
        String pathName = br.readLine();
        System.out.println(pathName);

        //2.通过字节码对象的 `构造方法对象` 得到对象
        //用字节码文件对象创建一个类对象
        Class clazz = Class.forName(pathName);

        //得到类的全部构造方法对象
        Constructor[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);    //2

        //public com.cwy.reflect.Person()
        System.out.println(constructors[0]);
        //public com.cwy.reflect.Person(int,java.lang.String)
        System.out.println(constructors[1]);

        Constructor c = constructors[0];
        //用构造方法对象 得到 常见的类对象
        Object obj = c.newInstance();
        //强转为我们想要的Person对象
        Person p = (Person)obj;
        p.setAge(10);
        p.setName("wenhuohuo");
        System.out.println(p.toString());
    }
}
