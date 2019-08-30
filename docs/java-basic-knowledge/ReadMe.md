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
	JRE：java运行环境

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


##  3. 两个对象的 hashCode()相同，则 equals()也一定为 true吗？


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


