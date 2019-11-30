package com.whh.aop;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 15:09
 **/


public class MathCalculator {
    public int div(int i, int j){
        System.out.println("MathCalculator....div..");
        return i/j;
    }
}
