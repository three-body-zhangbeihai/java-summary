package com.whh.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 20:28
 **/


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

        //如果类名包含“er”，那么这些类就会被扫描进ioc容器中
        if(className.contains("er")){
            return true;
        }
        return false;
    }
}
