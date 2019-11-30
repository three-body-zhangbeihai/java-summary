package com.whh.import_a;

import com.whh.bean.Green;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 00:11
 **/


public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @param annotationMetadata：当前类的注解信息
     * @param beanDefinitionRegistry：BeanDefinition注册类，把所有需要添加到容器的bean，调用
     *                              BeanDefinitionRegistrar.registerBeanDefinition手动注册进来。
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {
        //是否包含该指定的bean
        boolean b1 = beanDefinitionRegistry.containsBeanDefinition("com.whh.bean.Blue");
        boolean b2 = beanDefinitionRegistry.containsBeanDefinition("com.whh.bean.Yellow");
        //两个bean都存在
        if(b1 && b2){
            //指定bean定义信息
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Green.class);
            beanDefinitionRegistry.registerBeanDefinition("green",rootBeanDefinition);
        }


    }
}
