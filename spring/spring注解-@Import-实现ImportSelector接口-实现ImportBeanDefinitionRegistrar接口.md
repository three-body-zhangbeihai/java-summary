# @Import，实现ImportSelector，实现ImportBeanDefinitionRegistrar接口

[TOC]

<br>

Import导入组件主要有三种用法：

- @Import快速导入组件
- 实现ImportSelector接口
- 实现ImportBeanDefinitionRegistrar接口： 手动注册

<br>



## 1. @Import 快速导入组件

在之前的注解中，我们导入组件的方式是:

- @ComponentScan包扫描+组件标识注解【@Controller、@Service、@Repository、@Component】
- @Bean导入第三方组件（缺点是麻烦，每次都要手动创建一个方法）

<br>

我们可以在配置类上用**@Import快速导入组件。**

- **用法**：@Import(参数：要导入到容器中的组件，也可以是数组形式多个导入)
- **输出**：输出组件名时是组件的全路径。

<br>

**定义两个bean类**：

```java
package com.whh.bean;
public class Color {
}
```

```java
package com.whh.bean;
public class Student {
}
```

<br>

在之前的MainConfig3中添加@Import。

```java
//@Import(Color.class)
@Import({Color.class, Student.class})
public class MainConfig3 {
}
```

**结果**：注意，在没加入@Import之前，输出的组件名是没有Color和Student 的。

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalRequiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig3
com.whh.bean.Color
com.whh.bean.Student
bill

```

<br>



## 2.实现 ImportSelector接口



ImportSelector：返回需要导入的组件的全类名数组。

**用法**：可以通过自定义实现了 ImportSelector接口的类，在selectorImports方法中传入要导入的组件的全类名，然后@Import注解中加入自定义的 实现类.class。

<br>

**ImportSelector**：

```java
public interface ImportSelector {
    String[] selectImports(AnnotationMetadata var1);
}
```

<br>

```java
package com.whh.bean;
public class Blue {
}
```

```java
package com.whh.bean;
public class Yellow {
}
```

<br>

```java
package com.whh.import_a;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {
    /**
     * @param annotationMetadata：当前标注@Import注解的类的所有注解信息
     * @return
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //返回值是导入到容器中的组件的全类名。
        //方法不能返回null值。会报空指针异常。
        return new String[]{"com.chh.bean.Blue", "com.whh.bean.Yellow"};
    }
}
```

<br>

```java
@Import({Color.class, Student.class, MyImportSelector.class})
public class MainConfig3 {
}
```

<br>

**输出**：

```java
com.whh.bean.Blue
com.whh.bean.Yellow
```

<br>

## 3. 实现ImportBeanDefinitionRegistrar接口

手动注册

**用法**： 自定义一个实现了 ImportBeanDefinitionRegistrar的接口，在实现的 registerBeanDefinitions 方法中，将指定的bean手动注册到容器中。

<br>
**要注册的bean**：

```java
package com.whh.bean;
public class Green {
}
```

**MyImportBeanDefinitionRegistrar**：

```java
package com.whh.import_a;

import com.whh.bean.Green;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

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
```

**MainConfig3**:

```java
@Import({Color.class, Student.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig3 {
}
```

<br>

**结果**：

结果也输出了 **`green`**。

<br>

