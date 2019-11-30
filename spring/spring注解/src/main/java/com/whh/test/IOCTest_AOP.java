package com.whh.test;

import com.whh.aop.MathCalculator;
import com.whh.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * @program: spring_annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 17:16
 **/


public class IOCTest_AOP {
    @Test
    public void testAOP(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator mc = ac.getBean(MathCalculator.class);
        mc.div(1,1);
    }
}
