# 问题：
1. JDK 和 JRE的区别
2. == 和 equals 区别
3. 两个对象的 hashCode()相同，则 equals()也一定为 true吗？
4. final 在 java 中有什么作用？
5. java 中的 Math.round(-1.5) 等于多少？
6. java的数据类型
7. int和Integer
8. 可以将int强转为byte类型么?会产生什么问题?
9. java中的操作字符串类，有何区别？ 
10. 实现字符串反转 reserve()
11. String 类的常用方法
12. 抽象类必须要有抽象方法吗？
13. 普通类和抽象类有哪些区别？
14. 抽象类能使用 final 修饰吗？
15. 接口和抽象类有什么区别？
16. java 中 IO 流分为几种？
17. BIO、NIO、AIO 有什么区别？
18. Files的常用方法都有哪些？




# 解答：
##  1. JDK 和 JRE的区别
JDK：java开发工具包，提供了java的开发环境和运行环境
JRE：java运行环境

##  2. == 和 equals 区别
### == 
1. 基本类型的== ：比较值是否相同
2. 引用类型的== ：比较的引用是否相同
### equals
1. 默认情况是引用比较
2. 很多类，如String，Integer 重写了equals方法，变成值比较

  ```java
  equals本质是 == :
  public boolean equals(Object obj) {
     return (this == obj);
  }
  ```
两个String类型（引用）用equals比较，相同原因：
equals本质是==，对于引用类型，比较的是内存地址，但String重写equals方法，变成值的比较

String 类对equals() 方法的重写

```java
public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = count;
        if (n == anotherString.count) {
        char v1[] = value;
        char v2[] = anotherString.value;
        int i = offset;
        int j = anotherString.offset;
        while (n-- != 0) {
            if (v1[i++] != v2[j++])
            return false;
        }
        return true;
        }
    }
    return false;
    }

**底层代码原理：**

1. 判断是否是String类型
2. 是，取出两个对象的值，放到数组中，循环比较每一个字符，有不同的，返回false，全部相同，返回true
```



## 3. 两个对象的 hashCode()相同，则 equals()也一定为 true吗？



不一定

因为在散列表中，hashCode()相等即两个键值对的哈希值相等，然而哈希值相等，并不一定能得出键值对相等。



##  4. final 在 java 中有什么作用？
1. final 修饰的类叫最终类，该类不能被继承。

2. final 修饰的方法不能被重写。

3. final 修饰的变量叫常量，常量必须初始化，初始化之后值就不能被修改。

  

## 5. java 中的 Math.round(-1.5) 等于多少？
round(): 取整方法，加0.5，进行下取整



## 6. java的数据类型
1. 基础类型 8 种
  byte、boolean、char、short、int、float、long、double

2. 引用数据类型：

  

## 7. int和Integer
**区别**

​	Integer是int的包装类型，在拆箱和装箱中，二者自动转换。

​	int是基本类型，直接存数值，而integer是对象，用一个引用指向这个对象。

**占用内存**
	Integer 对象会占用更多的内存。Integer是一个对象，需要存储对象的元数据。但是 int 是一个原始类型的数据，所以占用的空间更少。



## 8. 可以将int强转为byte类型么?会产生什么问题?
可以强制转化，java中，byte是8位，范围 -128 ~ 128 。int是 32 位。 强转，会导致高24位被丢弃。




## 9. java中的操作字符串类，有何区别？
**1. String, StringBuffer, StringBuilder**

**2. 可变与不可变**	

**String: **

​	不可变类： 创建之后，字符串不可改变**(拼接字符串相当于重新创建一个)**

**StringBuffer,StringBuilder: **

​	可变类，继承自AbstractStringBuilder类，底层用字符数组保存字符串
<br>
**3. 初始化方式**

**String **

​	构造方法： String str = new String("hello");或 

​	直接赋值： String str = "hello";

**StringBuffer**

​	构造方法： StringBuffer sb = new StringBuffer("hello")

<br>

**4. 字符串修改方式**

**String：**

​	**底层**：new StringBuffer(str) -->  append(), --> toString()

​	**效率**：String修改有额外操作，效率低。StringBuffer，StringBuilder效率高

<br>

**5. 是否实现equals 和  hashCode方法**

**equals**

​       String实现了equals方法，StringBuffer，StringBuilder没有

**hashCode**

​      String实现hashCode，StringBuffer，StringBuilder没有
<br>
**6. 是否线程安全**

**效率**
       StringBuilder  >  StringBuffer  > String<br

**安全**

​	StringBuffer加锁，是线程安全的。

​	单线程选 StringBuilder，多线程，选 StringBuffer。



## 10. 实现字符串反转 reserve()

使用 StringBuffer 或 StringBuilder的reserve()  方法

```java
StringBuffer sb = new StringBuffer("hello");
sout(sb.reserver());
```



## 11. String 类的常用方法

**indexOf()**：返回指定字符的索引。

**charAt()**：返回指定索引处的字符。

**replace()**：字符串替换。

**trim()**：去除字符串两端空白。

**split()**：分割字符串，返回一个分割后的字符串数组。

**getBytes()**：返回字符串的 byte 类型数组。

**length()**：返回字符串长度。

**toLowerCase()**将字符串转成小写字母。

**toUpperCase()**：将字符串转成大写字符。

**substring()**：截取字符串。

**equals()**：字符串比较。



## 12. 抽象类必须要有抽象方法吗？

**不一定**

**示例**

```java
abstract class Cat {
   public static void sayHi() {
        System.out.println("hi~");
    }
}
```

结果：可以正常运行



## 13. 普通类和抽象类有哪些区别？

1. 普通类不能包含抽象方法，抽象类可以包含抽象方法。
2. 抽象类不能直接实例化，普通类可以直接实例化



## 14. 抽象类能使用 final 修饰吗？

​	不能，定义抽象类就是让其他类继承的，如果定义为 final 该类就不能被继承，这样彼此就会产生矛盾，所以 final 不能修饰抽象类。



## 15. 接口和抽象类有什么区别？



**实现**<br>

抽象类的子类用 extends 继承，接口必须用implements 实现

**构造函数 **<br>

抽象类可以有构造函数

接口没有

**main方法**<br>

抽象类可以有main方法，并且能运行；

接口不能有main方法

**实现数量**<br>

类可以实现多个接口；但只能继承一个抽象类

**访问修饰符**<br>

接口中的方法默认使用public修饰；

抽象类的方法可以是任意访问修饰符



## 16. java 中 IO 流分为几种？

**功能划分**<br>

输入流(input)

输出流(output）

**类型划分**<br>

字符流；

字节流
**字符流和字节流区别**<br>

字节流按 8 位传输以字节为单位输入输出数据，

字符流按 16 位传输以字符为单位输入输出数据。



## 17. BIO、NIO、AIO 有什么区别？



1. **BIO**<br>

  Block IO：同步阻塞式IO，就是传统IO

  特点：模式简单，使用方便，并发处理能力低

  

2. **NIO**<br>

  New IO ：同步非阻塞式IO，传统IO的升级；

  客户端和服务器端通过Channel（通道）通讯，实现了多路复用

3. **AIO**<br>

  Asynchronous IO：NIO的升级，也叫 NIO2；实现异步非阻塞IO；

  异步IO 的操作基于事件和回调机制

  

## 18. Files的常用方法都有哪些？

**Files.exists()**：检测文件路径是否存在。

**Files.createFile()**：创建文件。

**Files.createDirectory()**：创建文件夹。

**Files.delete()**：删除一个文件或目录。

**Files.copy()**：复制文件。

**Files.move()**：移动文件。

**Files.size()**：查看文件个数。

**Files.read()**：读取文件。

**Files.write()**：写入文件。

