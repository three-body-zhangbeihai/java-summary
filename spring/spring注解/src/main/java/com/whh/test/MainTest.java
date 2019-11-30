package com.whh.test;

import com.whh.bean.Person;
import com.whh.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 16:58
 **/


public class MainTest {

    public static void main(String[] args) {
        //传统xml文件获取的方式
//        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//        Object person = applicationContext.getBean("person");
//        System.out.println(person); //Person{age=20, name='wenhuohuo'}
//        System.out.println(person.getClass()); //class com.whh.bean.Person


        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        //获取实例对象
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);

        //通过类型找到bean的名称
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType){
            System.out.println(name); //Person
        }

    }
}
