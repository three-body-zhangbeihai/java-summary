package com.whh.config;

import com.whh.bean.ColorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 00:41
 **/

@Configuration
public class MainConfig4 {

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
