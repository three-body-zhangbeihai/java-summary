# @Scope-@Lazy

[TOC]

<br>



## 1. Scope

scope的作用调整bean的作用域。

socpe可取的值有：

singleton、prototype、request（web环境下）、session（web环境下）。

分别对应：

- ConfigurableBeanFactory#SCOPE_PROTOYPE ：单实例
- ConfigurableBeanFacotry@SCOPE_SINGLETON ：多实例
- org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST ：同一次请求创建一个实例
- org.springframework.web.context.WebApplicationContex#SCOPE_SESSION ：同一个session创建一个实例

<br>

其实注解的scope对应就是bean中scope可选的内容。

```xml
<bean id="person" class="com.whh.bean.Person" scope="singleton"> <!--还有prototype、request、session -->
```

<br>

**MainConfig2:**

```java
package com.whh.config;

import com.whh.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig2 {
    @Scope("prototype")
    @Bean("person")
    public Person person(){
        System.out.println("给ioc容器中添加Person....");
        return new Person(20,"wenhuohuo");
    }
}
```

```java
public class IOCTest {

    @Test
    public void test02(){
        //得到spring上下文对象
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames){
            System.out.println(name);
        }
        System.out.println("------ioc启动完成-------");
        Person bean1 = applicationContext.getBean(Person.class);
        Person bean2 = applicationContext.getBean(Person.class);
        //@Bean注解默认是单实例的，
        //所以是 singleton时为true，prototype时为false。
        System.out.println(bean1 == bean2);
    }
}
```

<br>

**结果**：

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig2
person
------ioc创建完成-------
给ioc容器中添加Person....
给ioc容器中添加Person....
false
```

<br>

值得注意的是：

- 设置为singleton时：ioc容器启动会调用方法去创建对象放到ioc容器中，以后每次获取bean就是直接从容器中

  （**map.get()** ）拿。

- 设置为prototype时：ioc容器并不会去调用方法创建对象放到容器中，而是获取bean的时候才会调用方法去创建对象（getBean）。

<br>

## 2. @Lazy 懒加载

@Lazy是针对单实例bean的。我们知道单实例bean默认在容器启动的时候创建对象。

懒加载就是让：容器启动不创建对象，第一次使用（获取）bean的时候才创建对象，并初始化。

还是上面的代码，加载@Lazy之后（单例情况下），当调用

```java
Person bean1 = applicationContext.getBean(Person.class);
```

时，才创建bean并初始化。

<br>

