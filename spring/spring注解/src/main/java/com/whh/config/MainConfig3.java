package com.whh.config;

import com.whh.bean.Color;
import com.whh.bean.Person;
import com.whh.bean.Student;
import com.whh.condition.LinuxCondition;
import com.whh.import_a.MyImportBeanDefinitionRegistrar;
import com.whh.import_a.MyImportSelector;
import com.whh.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 22:31
 **/

//@Import(Color.class)
@Import({Color.class, Student.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
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
