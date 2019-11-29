# spring注解-@Bean-@Configuration-@ComponentScan

[TOC]

<br>

## 1. @Bean、@Configuration

### 1.1 传统配置文件的方式：

**beans.xml**：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="person" class="com.whh.bean.Person">
        <property name="age" value="20" />
        <property name="name" value="wenhuohuo"/>
    </bean>
</beans>
```

**MainTest:**

```java
package com.whh.test;
import com.whh.bean.Person;
import com.whh.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        //传统xml文件获取的方式
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Object person = applicationContext.getBean("person");
        System.out.println(person); //Person{age=20, name='wenhuohuo'}
        System.out.println(person.getClass()); //class com.whh.bean.Person
    }
}

```

<br>

## 1.2 注解的方式

@Configuration：这是标识配置类的注解，对应xml配置文件。

@Bean("value")：标识这是一个bean组件。value是bean的id，默认是以方法名为id。

```java
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    //指定id名
    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

    Autowire autowire() default Autowire.NO;

    String initMethod() default "";

    String destroyMethod() default "(inferred)";
}
```



**MainConfig：**

```java
package com.whh.config;

import com.whh.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//标识这是一个配置类，配置等同于配置文件
@Configuration
public class MainConfig {

    //标识这是一个bean，括号的value指定bean的id，默认是以方法名作为id
    //将方法返回值放到ioc容器中。
    @Bean("Person")
    public Person person(){
        return new Person(20,"wenhuohuo");
    }
}
```

**MainTest**：

```java
package com.whh.test;

import com.whh.bean.Person;
import com.whh.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {
        //得到spring上下文对象。
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
```

<br>

## 2. 包扫描注解 @ComponentScan(value="")

### 2.1ComponentScan

包扫描，只要标注了 **@Controller、@Service、@Repository、@Component** 的组件会被扫描出来。

**xml方式**： 是在beans.xml文件中添加

```xml
<context:component-scan base-package="com.whh" />
```

<br>

**注解的方式**： 

```java
@ComponentScan(vlaue = "com.whh") // value 指定要扫描的包
```

我们在com.whh 包下新建 controller、service、dao的包，并新建相应的BookController、BookService、BookDao，并标注对应的@Controller、@Service、@Repository。

```java
package com.whh.test;

import com.whh.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
    }

}
```

**结果**：

```java
//spring 自带的组件
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory

//Configuration注解中也带了@Component注解
mainConfig
bookController
bookDao
bookService
Person
```

<br>

**Configuration** 注解源码，带了@Component注解

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
    String value() default "";
}
```

**事实上，@Controller、@Service、@Repository 都有@Component注解**：

```java
//@Controller注解源码
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    String value() default "";
}

/*@Service 注解源码*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    String value() default "";
}

/*@Repository注解源码 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository {
    String value() default "";
}

```

<br>

我们还可以为指定 扫描时只要的组件、要排除扫描的组件。通过**excludeFilters** 和 **includeFilters** 指定，两个均为数组。

**ComponentScan 注解源码**：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
//重复注解
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    @AliasFor("basePackages")
    String[] value() default {};	
    
    /* 此处省略一些 */
	//指定只要扫描的组件
	ComponentScan.Filter[] includeFilters() default {};
	//指定要排除扫描的组件
    ComponentScan.Filter[] excludeFilters() default {};

	//Filter组件
    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Filter {
        //要排除的类型：是注解类型（具体类型在 枚举类FilterType 中定义）
        FilterType type() default FilterType.ANNOTATION;

        @AliasFor("classes")
        Class<?>[] value() default {};

        @AliasFor("value")
        Class<?>[] classes() default {};

        String[] pattern() default {};
    }
```

<br>

**excludeFilters:**

```java
@ComponentScan(value = "com.whh",
    //排除扫描的组件
    excludeFilters = {
        //type：排序的类型，这里为注解类型
        //classes：排除的具体注解，数组可以传入多个，这里排除Controller和Service注解
        @ComponentScan.Filter(type = FilterType.ANNOTATION,
                              classes = {Controller.class,Service.class})
    }
/)
```

**测试结果**：

```
bookController和bookService没有输出
```

<br>

**includeFilters**: 

其中：**useDefaultFilters** 要设置为 **false**，这个对于在配置文件中的  **`use-default-filters="false"`**

```java
<context:component-scan base-package="com.whh" use-default-filters="false"/>
```

<br>

```java
@ComponentScan(value = "com.whh",
        //扫描时指定只要扫描的组件
        //userDefaultFilters:默认是true，需要设置为false才能用includeFilters
        includeFilters = {
                //type：指定只要扫描的类型，这里为注解类型
                //classes：指定的具体注解，数组可以传入多个，这里Controller和Service注解
                @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = 
                                      {Controller.class,Service.class})
        },useDefaultFilters = false
)
```

**测试结果**：

```
bookDAO没有输出
```

<br>

另外，在JDK1.8，ComponentScan源码中有一个注解为 **@Repeatable** ，表示可以重复使用注解，但是如果是低版本，要重复注解，其实也可以用 @ComponentScans，带s的注解。

注解如下：其中，value是一个数组，填充单个ComponentScan。

```java
@ComponentScans(value = {
    @ComponentScan(value = "com.whh",
        //排除扫描的组件
        excludeFilters = {
            //type：排序的类型，这里为注解类型
            //classes：排除的具体注解，数组可以传入多个，这里排除Controller和Service注解
            @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = 
                                  {Controller.class,Service.class})
        }
    )
})
```

<br>

### 2.2 Filter.Type 自定义类型 @ComponentScan.Filter

在上面的@ComponentScan注解中，Filter 类型 type有多种类型，在FilterType枚举类中定义如下：

**枚举类FilterType：**

```java
public enum FilterType {
    //注解类型，如：Controller.class
    ANNOTATION,
    //按照给定的类型，如：BookService.class，那么不管是该类的子类或是父类都会被加载到容器中或不加载到容器中。（加不加载由includeFilters或excludeFilters决定）
    ASSIGNABLE_TYPE,
    //AspcetJ类型
    ASPECTJ,
    //正则表达式类型
    REGEX,
    //自定义类型。如果自定义一个实现TypeFilter的实现类。如：MyTypeFilter.class
    CUSTOM;

    private FilterType() {
    }
}

```

**MyTypeFilter**：

```java
 package com.whh.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;


public class MyTypeFilter implements TypeFilter {
    //匹配成功返回true，失败返回false。

    /**
     * @param metadataReader:读取到的正在扫描的类的信息
     * @param metadataReaderFactory：可以获取到其他任何类的信息
     * @return
     * @throws IOException
     */
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
            throws IOException {
        //获取当前注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        //获取当前正在扫描的类的类信息，如类型是什么，实现了什么接口，父类、子类是什么等
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        //获取当前类的资源（类的路径）
        Resource resource = metadataReader.getResource();

        /* 使用getClassMatadata*/
        //获取当前正在扫描的类的类名
        String className = classMetadata.getClassName();
        System.out.println("-------->"+className);
        return false;
    }
}
```

```java
@ComponentScan(value = "com.whh",
        //扫描时指定只要扫描的组件
        //userDefaultFilters:默认是true，需要设置为false才能用includeFilters
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        },useDefaultFilters = false
)
```

<br>

**结果：**

```java
-------->com.whh.bean.Person
-------->com.whh.config.MyTypeFilter
-------->com.whh.controller.BookController
-------->com.whh.dao.BookDao
-------->com.whh.service.BookService
-------->com.whh.test.IOCTest
-------->com.whh.test.MainTest
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
person
myTypeFilter
bookController
bookService
Person
```

其中，**mainConfig** 和 **person** 并不是包扫描的结果，它是一个配置类，一个bean，人为给容器添加的组件。

除此之外的类，只要是在com.whh包下类都会如匹配是否符合 **包含“er“的规则**，匹配的就加到容器中。

<br>



<br>

