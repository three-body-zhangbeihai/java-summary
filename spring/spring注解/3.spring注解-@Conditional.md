# spring注解-@Conditional



@Conditional是spring4之后出现的注解。

**作用：** 按照一定条件进行判断，满足条件给容器中注册bean，不满足的即使有@Bean也不注册。

在springboot中有大量运用

<br>

**@Conditional 源码：**

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
    //需要传入一个Condition条件
    Class<? extends Condition >[] value();
}
```

<br>

Condition 条件是一个接口，如下：

```java
public interface Condition {
    boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}
```

我们自定义实现该接口的实现类作为Condition参数。

<br>



假如我们要实现这样一个情况：

```java
如果当前ioc容器的环境运行的系统是windows，就注册 id为 "bill" 的bean。
如果是linux系统，就注册 id为 "linus"的bean。
```

<br>

**创建Person类省略，参考前面的注解，就是构造器两个参数，一个age，一个name**

<br>

**WindowsCondition:**

```java
package com.whh.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


//判断是否为windows系统
public class WindowsCondition implements Condition {

    /**
     *
     * @param conditionContext:判断条件能使用的上下文（环境）
     * @param annotatedTypeMetadata：标注了Condition注解的注释信息
     * @return:符合条件返回true，否则返回false
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String osName = environment.getProperty("os.name");
        if(osName.contains("Windows")){
            return true;
        }
        return false;
    }
}

```

<br>

**LinuxCondition:**

```java
package com.whh.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String osName = environment.getProperty("os.name");
        if(osName.contains("linux")){
            return true;
        }
        return false;
    }
}

```

<br>

```java
package com.whh.config;

import com.whh.bean.Person;
import com.whh.condition.LinuxCondition;
import com.whh.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

public class MainConfig3 {

    /**
     * @Conditional：按照一定条件进行判断，满足条件给容器中注册bean，不满足的即使有@Bean也不注册。
     * 要求：如果当前ioc容器的环境运行的系统是windows，就注册 "bill"
     *      如果是linux系统，就注册 "linus"
     */

    @Conditional(WindowsCondition.class)
    @Bean("bill")
    public Person person1(){
        return new Person(60,"比尔盖茨");
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person2(){
        return new Person(50,"Linus");
    }
}
```

<br>

```java
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
```

<br>

**结果：**

```java
Windows 10
{bill=Person{age=60, name='比尔盖茨'}}
```

注意到：当前的系统是windows10，所以输出了Windows 10 和 比尔盖茨。而因为不是linux系统，不满足条件，所以没有输出linus。

<br>

如果我们通过修改VM参数：

```
-Dos.name=linux
```

修改为linux，输出结果：

```java
linux
{linus=Person{age=50, name='Linus'}}
```

<br>



上面的 @Conditional注解是标在方法上，如果标在类上，那么只有满足指定Condition，该类注册的Bean才会生效，否则都不生效

<br>



