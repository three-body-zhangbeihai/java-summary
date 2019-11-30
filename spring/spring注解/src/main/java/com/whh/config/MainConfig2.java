package com.whh.config;

import com.whh.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 20:47
 **/

@Configuration
public class MainConfig2 {

    /**
     * ConfigurableBeanFactory#SCOPE_PROTOYPE prototype
     * ConfigurableBeanFacotry@SCOPE_SINGLETON singleton
     * org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST request
     * org.springframework.web.context.WebApplicationContex#SCOPE_RESPONSE response
     * @return
     */
//    @Scope("prototype")
    @Bean("person")
    @Lazy
    public Person person(){
        System.out.println("给ioc容器中添加Person....");
        return new Person(20,"wenhuohuo");
    }
}
