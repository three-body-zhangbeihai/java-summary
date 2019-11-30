package com.whh.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 22:49
 **/

//判断是否为windows系统
public class WindowsCondition implements Condition {

    /**
     *
     * @param conditionContext:判断条件能使用的上下文（环境）
     * @param annotatedTypeMetadata：标注了Condition注解的注释信息
     * @return:符合条件返回true，否则返回false
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取ioc使用bean工厂
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();

        //获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();

        //获取bean定义的注册类。通过该注册类，可以查询有没有哪个bean的定义，或者用来注册一个bean
        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        //获取当前环境
        Environment environment = conditionContext.getEnvironment();
        String osName = environment.getProperty("os.name");
        if(osName.contains("Windows")){
            return true;
        }
        return false;
    }
}
