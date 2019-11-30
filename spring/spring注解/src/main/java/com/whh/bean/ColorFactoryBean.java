package com.whh.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-30 00:33
 **/

//
public class ColorFactoryBean implements FactoryBean<Color> {
    /**
     * @return:返回一个Color对象，该对象会被加到ioc容器中
     * @throws Exception
     */
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...");
        return new Color();
    }

    /**
     * @return：设置对象的类型并返回。
     */
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * @return：
     * true：如果该bean是单实例，在容器中保存一份。
     * false：如果是多实例，每次获取都会创建一个新的bean。
     */
    public boolean isSingleton() {
        return true;
    }
}
