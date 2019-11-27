# java反射

[TOC]

<br>



## 简介

反射库（reflection library) 提供了一个非常丰富且精心设计的工具集， 以便编写能够 **动态** 操纵 Java 代码的程序。这项功能被大量地应用于 **JavaBeans** 中， 它是 Java组件的体系结构。

使用反射， Java 可以支持 Visual Basic 用户习惯 使用的工具。特别是在设计或运行中添加新类时， 能够快速地应用开发工具动态地查询新添加类的能力。

 **能够分析类能力的程序称为反射（reflective)。**反射机制的功能极其强大，在下面可以看到， 反射机制可以用来： 

- 在运行时分析类的能力。
- 在运行时查看对象， 例如， 编写一个 toString方法供所有类使用。
- 实现通用的数组操作代码。 
- 用 Method 对象， 这个对象很像中的函数指针。 

<br>

## Class类

### 字节码文件对象

《java核心技术卷》是这么描述的

在程序运行期间，Java运行时系统始终为所有的对象维护一个被称为运行时的类型标识。 这个信息跟踪着每个对象所属的类。虚拟机利用运行时类型信息选择相应的方法执行。 然而，可以通过专门的 Java 类访问这些信息。保存这些信息的类被称为 Class。

<br>

我们可以这么理解：

jvm 把字节码文件加载到jvm内存去之后，jvm就认为这个字节码文件是一个 **字节码文件对象**。也就是Class类的对象。

比如有一个类 **`Person.java`** ，编译成 **字节码文件**，是 **`Person.class`** ，jvm加载该class文件，认为该文件就是 **字节码文件对象**。是Class类的对象。

<br>



### 获得字节码文件对象

有三种方法：

- Object类的 **getClass** 方法： **对象.getClass();**
- **类型.class** 属性
- **Class.forName("类的路径");**

<br>

**上代码演示**：

```java
package com.cwy.reflect;

public class Test01 {
    public static void main(String[] args) throws ClassNotFoundException {
        Person p1 = new Person();
        Person p2 = new Person();

        //第一种方法：getClass
        Class class1 = p1.getClass();
        Class class2 = p1.getClass();
        System.out.println("class1: " + class1); //class1: class com.cwy.reflect.Person
        System.out.println("class2: " + class2); //class2: class com.cwy.reflect.Person
        System.out.println(class1 == class2); //true

        Class class3 = p2.getClass();
        System.out.println("class3: " + class3); //class3: class com.cwy.reflect.Person
        System.out.println(class1 == class3); //true

        //第二种方法： 类型.class
        Class class4 = Person.class;
        System.out.println("class4: " + class4); //class4: class com.cwy.reflect.Person
        //也可以是基本数据类型
        Class class5 = int.class;

        //第三种方法：需要传入类全路径 Class.forName("类型名(全路径名)");
        //需要抛出异常，因为可能找不到类名。throws ClassNotFoundException
        Class class6 = Class.forName("com.cwy.reflect.Person");
        System.out.println(class6 == class1); //true
    }
}

```

<br>

**结果：**

```java
class1: class com.cwy.reflect.Person
class2: class com.cwy.reflect.Person
true
class3: class com.cwy.reflect.Person
true
class4: class com.cwy.reflect.Person
true
```

<br>



## 字节码文件对象的组成和使用

我们知道，java类的组成由 构造方法、成员变量、成员方法等组成。而在字节码文件对象中也有对象的对象。

| 类       | 字节码文件对象                      |
| -------- | ----------------------------------- |
| 构造方法 | **构造器对象（类型：Constructor）** |
| 成员变量 | **成员变量对象（类型：Field）**     |
| 成员方法 | **成员方法对象（类型：Method）**    |

<br>

知道了这些，我们可以来使用反射了。

平时成绩对象，如：**`Person p = new Person()`** ,注意 **`new Person()`** ,其实，我们是通过类的构造方法来创建对象的。同样的，用字节码文件对象创建 Class类对象，就要用到 **构造方法对象（Constructor）**。

-------------------------

我们知道，反射创建类，通过类的全路径作为参数，构建出一个类的。

那么，我们可以通过配置文件的方式，要构造出不同的类时，就修改配置文件，这样一来，也体现了多态的功能。

<br>

1. 类全路径的配置文件 path.properties

```properties
com.cwy.reflect.Person
```

2. 我们新建一个Person类：

```java
package com.cwy.reflect;

public class Person {
    private int age;
    private String name;
    public Person(){
    }
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

```

3. 新建Test02类来测试

```java
package com.cwy.reflect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;

public class Test02 {
    public static void main(String[] args) throws Exception{
        //1.读取配置文件
        FileReader f = new FileReader("src/com/cwy/reflect/path.properties");
        BufferedReader br = new BufferedReader(f);
        //得到路径名
        String pathName = br.readLine();
        System.out.println(pathName);

        //2.通过字节码对象的 `构造方法对象` 得到对象
        //用字节码文件对象创建一个类对象
        Class clazz = Class.forName(pathName);

        //得到类的全部构造方法对象
        Constructor[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);    //2

        //public com.cwy.reflect.Person()
        System.out.println(constructors[0]);
        //public com.cwy.reflect.Person(int,java.lang.String)
        System.out.println(constructors[1]);

        Constructor c = constructors[0];
        //用构造方法对象 得到 常见的类对象
        Object obj = c.newInstance();
        //强转为我们想要的Person对象
        Person p = (Person)obj;
        p.setAge(10);
        p.setName("wenhuohuo");
        System.out.println(p.toString());
    }
}

```

<br>

**结果**：

```
com.cwy.reflect.Person
2
public com.cwy.reflect.Person()
public com.cwy.reflect.Person(int,java.lang.String)
Person{age=10, name='wenhuohuo'}
```

这样我们就使用了反射，而且，如果我们将 配置文件 **path.properties** 中的Preson 类改为其他类，那么就可以构造出其他类，这样实现了多态。



<br>



## 深入字节码文件对象

我们前面说过，字节码文件对象可以得到几个常用的对象，构造器对象，成员变量对象，成员方法对象。

### 构造器对象

- Class.forName() ：可以得到字节码文件对象
- getConstructors()：可以得到所有的 **公有** 构造器对象
- getDeclaredConstructors：可以得到所有的构造器对象（**无论公有私有**），但是**得到私有的不能用来创建对象**
- newInstance：用来创建对象
- setAccessable()：用来设置可以访问创建的私有构造器对象。

```java
package com.cwy.reflect;

public class Student {
    private int age;
    private String name;

    Student(){}
    public Student(int age){
        this.age = age;
    }
    protected Student(String name){
        this.name = name;
    }
    private Student(int age, String name){
        this.age = age;
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
```



```java
package com.cwy.reflect;

import java.lang.reflect.Constructor;

public class Test03 {
    public static void main(String[] args) throws Exception {
        //得到字节码文件对象
        Class clazz = Class.forName("com.cwy.reflect.Student");

        //得到字节码文件对象的构造器对象
        /* getConstructors() 只能得到公共的构造器 public修饰的 */
        Constructor[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);// Student中public修饰的公共构造器只有一个，所以length是1

        /* getDeclaredConstructors() 得到不管是共有的还是私有的构造器 */
        Constructor[] constructors2 = clazz.getDeclaredConstructors();
        System.out.println(constructors2.length); //所以length是 4

        /* 得到指定的构造器
        *   public Student(int age){}
        * */
        Constructor constructor = clazz.getConstructor(int.class);
        Object newInstance = constructor.newInstance(18);//传实参
        System.out.println(newInstance); //Student{age=18, name='null'}

        Constructor constructor1 = clazz.getDeclaredConstructor(int.class,String.class);
        //会报错。虽然得到私有构造器，但是并不能创建对象
        //Object newInstance1 = constructor1.newInstance(18, "wenhuohuo");
        // 设置暴力访问(允许访问设为true)
        constructor1.setAccessible(true);
        //这时就可以访问创建对象了
        Object newInstance1 = constructor1.newInstance(18, "wenhuohuo");
        System.out.println(newInstance1);//Student{age=18, name='wenhuohuo'}
    }
}

```

<br>

### 成员方法对象

- clazz.getMethods：得到所有的public方法，包括父类继承的public方法
- clazz.getDeclaredMethods：得到当前类的所有方法（包括私有的方法），不包括父类的方法
- clazz.newInstance()：直接创建对象，但注意当前类需要有对象的构造方法，这里newInstance不带参数，所以要有无参构造方法。
- method.invoke(对象，实参)：反射方式的方法调用
- method.setAccessable(true)：设置方法可以访问。

```java
package com.cwy.reflect;

public class Car {
    private int price;
    private String name;
    public Car(){};
    public Car(int price, String name){
        this.price = price;
        this.name = name;
    }

    public String run(String name){
        System.out.println(name);
        return "run";
    }
    private String stop(String name){
        System.out.println(name);
        return "stop";
    }
}
```



```java
package com.cwy.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test04 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Class.forName("com.cwy.reflect.Car");

        //得到字节码文件对象中的所有Method对象
        //不带Declared，是得到所有的public修饰的method，包括从父类继承过来的public的method方法
        Method[] methods = clazz.getMethods();
        /**
         * public java.lang.String com.cwy.reflect.Car.run(java.lang.String)
         * public java.lang.String com.cwy.reflect.Car.toString()
         * public java.lang.String com.cwy.reflect.Car.getName()
         * public void com.cwy.reflect.Car.setName(java.lang.String)
         * public void com.cwy.reflect.Car.setPrice(int)
         * public int com.cwy.reflect.Car.getPrice()
         * public final void java.lang.Object.wait() throws java.lang.InterruptedException
         * public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
         * public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
         * public boolean java.lang.Object.equals(java.lang.Object)
         * public native int java.lang.Object.hashCode()
         * public final native java.lang.Class java.lang.Object.getClass()
         * public final native void java.lang.Object.notify()
         * public final native void java.lang.Object.notifyAll()
         */
        for(Method method:methods){
            System.out.println(method);
        }
        System.out.println("---------------------------------");
        //带Declared是得到当前类中所有的Method方法
        Method[] methods1 = clazz.getDeclaredMethods();
        /**
         * public java.lang.String com.cwy.reflect.Car.run(java.lang.String)
         * public java.lang.String com.cwy.reflect.Car.toString()
         * public java.lang.String com.cwy.reflect.Car.getName()
         * public void com.cwy.reflect.Car.setName(java.lang.String)
         * private java.lang.String com.cwy.reflect.Car.stop(java.lang.String)
         * public void com.cwy.reflect.Car.setPrice(int)
         * public int com.cwy.reflect.Car.getPrice()
         */
        for(Method method: methods1){
            System.out.println(method);
        }
        System.out.println("----------------------------");
        //得到特定的方法
        Method method1 = clazz.getMethod("run",String.class);
        System.out.println(method1);
        //反射得到对象
        //直接clazz.newInstance得到对象，底层和前面先得到构造器对象，在得到普通类对象一样，
        //不过如果类中没有空参数的构造器，那么，clazz.newInstance会报错。
        Object newInstance = clazz.newInstance();

        //调用方法
        //传统方式：对象.方法(实参)
        //反射方法：方法.invoke(对象，实参)
        Object res = method1.invoke(newInstance,"特斯拉");
        System.out.println(res);

        //同样的，需要通过Declared的方式得到私有的方法
        //同样需要设置访问权限
        Method method2 = clazz.getDeclaredMethod("stop",String.class);
        method2.setAccessible(true);
        Object newInstance2 = clazz.newInstance();
        Object res2 = method2.invoke(newInstance2,"特斯拉");
        System.out.println(res2);
    }
}
```

<br>

### 成员变量对象

- clazz.getDeclaredField()：得到属性对象
- 调用属性：属性对象.set(对象,"value")

<br>

**Car类和前面一样。**

```java
package com.cwy.reflect;

import java.lang.reflect.Field;

public class Test05 {
    public static void main(String[] args) throws ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, InstantiationException {
        //同样以Car类来举例说明
        Class clazz = Class.forName("com.cwy.reflect.Car");

        //得到指定的Field。Declared的方式和其他两个一样
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);

        //调用属性：
        //传统方式：对象.属性名
        //反射方式：属性对象.set(对象,"value");
        Object newInstance = clazz.newInstance();
        name.set(newInstance,"特斯拉");
        System.out.println(newInstance);//Car{price=0, name='特斯拉'}

    }
}

```

<br>

<br>

