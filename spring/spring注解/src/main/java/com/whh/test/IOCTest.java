package com.whh.test;

import com.whh.bean.Person;
import com.whh.config.MainConfig;
import com.whh.config.MainConfig2;
import com.whh.config.MainConfig3;
import com.whh.config.MainConfig4;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 17:42
 **/


public class IOCTest {

    @Test
    public void testFactoryBean(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig4.class);
        //得到ioc容器中定义的所有组件
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name:beanDefinitionNames){
            System.out.println(name); //得到了colorFactorBean的id，并没有得到color这个bean的id
        }

        //而根据工厂bean的id获取得到的是 调用了getObject() 方法得到的bean，也就是color这个bean
        Object bean1 = applicationContext.getBean("colorFactoryBean");
        System.out.println("bean的类型:" + bean1);//com.whh.bean.Color@667a738
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        System.out.println(bean1 == bean2); //true

        //得到工厂bean的实例,加入 & 字符。& 在BeanFactory接口中定义。
        Object bean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean3); //com.whh.bean.ColorFactoryBean@36f0f1be

    }



    @Test
    public void testImport(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig3.class);
        //得到ioc容器中定义的所有组件
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name:beanDefinitionNames){
            //在@Import没加入之前是没有Color和Student这两个组件的
            System.out.println(name);
        }
    }


    @Test
    public void test3(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig3.class);

        //获取ioc的环境
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        //动态获取环境变量的值。根据环境获取属性，获取操作系统的名称
        String osName = environment.getProperty("os.name");
        System.out.println(osName);

        //根据类型获取所有的bean
        Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
        System.out.println(persons);

    }


    @Test
    public void test02(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames){
            System.out.println(name);
        }
        System.out.println("------ioc启动完成-------");

        Person bean1 = applicationContext.getBean(Person.class);
        Person bean2 = applicationContext.getBean(Person.class);
//        //@Bean注解默认是单实例的，
//        //所以是 singleton时为true，prototype时为false。
        System.out.println(bean1 == bean2);
    }



    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        //得到容器中所有定义的bean的名字
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
    }

}
