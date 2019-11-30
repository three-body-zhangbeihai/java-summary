package com.whh.import_a;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @program: spring-annotation
 * @description:
 * @author: wenyan
 * @create: 2019-11-29 23:46
 **/

//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {
    /**
     * @param annotationMetadata：当前标注@Import注解的类的所有注解信息
     * @return：返回值是导入到容器中的组件的全类名
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //方法不能返回null值。会报空指针异常。
        return new String[]{"com.whh.bean.Blue", "com.whh.bean.Yellow"};
    }
}
