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
13.  普通类和抽象类有哪些区别？
14. 抽象类能使用 final 修饰吗？
15. 接口和抽象类有什么区别？
16. java 中 IO 流分为几种？
17. BIO、NIO、AIO 有什么区别？
18. Files的常用方法都有哪些？




# 解答：
##  1. JDK 和 JRE的区别
JDK：java开发工具包，提供了java的开发环境和运行环境
<br>JRE：java运行环境
<br>
##  2. == 和 equals 区别
### == 
1. 基本类型的== ：比较值是否相同
2. 引用类型的== ：比较的引用是否相同
### equals
1. 默认情况是引用比较
2. 很多类，如String，Integer 重写了equals方法，变成值比较
    
  ```
  equals本质是 == :
  public boolean equals(Object obj) {
     return (this == obj);
  }
  ```
两个String类型（引用）用equals比较，相同原因：
equals本质是==，对于引用类型，比较的是内存地址，但String重写equals方法，变成值的比较
**底层代码原理：**
1. 判断是否是String类型
2. 是，取出两个对象的值，放到数组中，循环比较每一个字符，有不同的，返回false，全部相同，返回true

<br>

## 3. 两个对象的 hashCode()相同，则 equals()也一定为 true吗？
**答案：**不一定<br>
**原因：**因为在散列表中，hashCode()相等即两个键值对的哈希值相等，然而哈希值相等，并不一定能得出键值对相等。
<br>
##  4. final 在 java 中有什么作用？
1. final 修饰的类叫最终类，该类不能被继承。
2. final 修饰的方法不能被重写。
3. final 修饰的变量叫常量，常量必须初始化，初始化之后值就不能被修改。
<br>

## 5. java 中的 Math.round(-1.5) 等于多少？
round(): 取整方法，加0.5，进行下取整
<br>

## 6. java的数据类型
1. 基础类型 8 种
	byte、boolean、char、short、int、float、long、double
2. 引用数据类型：
<br>

## 7. int和Integer
1. 区别
	1. Integer是int的包装类型，在拆箱和装箱中，二者自动转换。
	2. int是基本类型，直接存数值，而integer是对象，用一个引用指向这个对象。
2. 占用内存
	Integer 对象会占用更多的内存。Integer是一个对象，需要存储对象的元数据。但是 int 是一个原始类型的数据，所以占用的空间更少。
<br>

## 8. 可以将int强转为byte类型么?会产生什么问题?
可以强制转化，java中，byte是8位，范围 -128 ~ 128 。
int是 32 位。 强转，会导致高24位被丢弃。
<br>


## 9. java中的操作字符串类，有何区别？
**1. String, StringBuffer, StringBuilder**

**2. 可变与不可变**
	String: 不可变类： 创建之后，字符串不可改变(拼接相当于重新创建一个)
	StringBuffer,StringBuilder: 可变类，继承自AbstractStringBuilder类，底层用字符数组保存字符串
	
**3. 初始化方式**
	String 
		构造方法： String str = new String("hello");或 
		直接赋值： String str = "hello";
	StringBuffer
		构造方法： StringBuffer sb = new StringBuffer("hello")
		
**4. 字符串修改方式**
	String：底层：new StringBuffer(str) -->  append(), --> toString()
	效率：String修改有额外操作，效率低。StringBuffer，StringBuilder效率高
	
**5. 是否实现equals 和  hashCode方法**
	equals
		String实现了equals方法，StringBuffer，StringBuilder没有
	hashCode
		String实现hashCode，StringBuffer，StringBuilder没有
		
**6. 是否线程安全**
	效率
		StringBuilder  >  StringBuffer  > String
	安全
		StringBuffer加锁，是线程安全的。
		单线程选 StringBuilder，多线程，选 StringBuffer。

## 10. 实现字符串反转 reserve()

## 11. String 类的常用方法

## 12. 抽象类必须要有抽象方法吗？

## 13. 普通类和抽象类有哪些区别？

## 14. 抽象类能使用 final 修饰吗？

## 15. 接口和抽象类有什么区别？

## 16. java 中 IO 流分为几种？

## 17. BIO、NIO、AIO 有什么区别？

## 18. Files的常用方法都有哪些？

