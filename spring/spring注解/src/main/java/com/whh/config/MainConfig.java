package com.whh.config;

import com.whh.bean.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 16:56
 **/

//标识这是一个配置类，配置等同于配置文件
@Configuration
//@ComponentScans(value = {
//    @ComponentScan(value = "com.whh",
//        //排除扫描的组件
//        excludeFilters = {
//            //type：排序的类型，这里为注解类型
//            //classes：排除的具体注解，数组可以传入多个，这里排除Controller和Service注解
//            @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class,Service.class})
//        }
//    )
//})
@ComponentScan(value = "com.whh",
        //扫描时指定只要扫描的组件
        //userDefaultFilters:默认是true，需要设置为false才能用includeFilters
        includeFilters = {
                //type：指定只要扫描的类型，这里为注解类型
                //classes：指定的具体注解，数组可以传入多个，这里Controller和Service注解
                //@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class,Service.class})
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
        },useDefaultFilters = false
)
public class MainConfig {

    //标识这是一个bean，括号的value指定bean的id，默认是以方法名作为id
    @Bean("Person")
    public Person person(){
        return new Person(20,"wenhuohuo");
    }
}
