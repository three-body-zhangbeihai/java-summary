# java-summary
**对java后端技术栈的每个专题做个总结，一起学习，一起秃头，欢迎指出错误所在。欢迎star**

**持续更新中...**

<br>

## jvm

<br>

- [Java 运行时数据区域](jvm/1.Java运行时数据区域.md)
- [垃圾回收算法](jvm/2.垃圾回收算法.md)
- [垃圾收集器](jvm/3.垃圾收集器.md)
- [内存分配和回收策略](jvm/4.内存分配和回收策略.md)
- [虚拟机工具](jvm/6.虚拟机工具.md)
- [class文件结构](jvm/7.class文件结构.md)
- [虚拟机类加载机制](jvm/9.虚拟机类加载机制.md)
- [类加载器和双亲委派模型](jvm/10.类加载器和双亲委派模型.md)
- [GC参数总结](jvm/12.GC参数总结.md)
- [(转载)Java的强引用、弱引用、软引用、虚引用](jvm/20.(转载)Java的强引用、弱引用、软引用、虚引用.md)

<br>

## mysql

- [mysql各种连接查询](mysql/mysql各种连接查询.md)
- [MySQL事务](mysql/MySQL事务.md)
- [MySQL引擎](mysql/MySQL引擎.md)
- [MySQL索引](mysql/MySQL索引.md)
- [MySQL索引底层数据结构](mysql/MySQL索引底层数据结构.md)

<br>

## 算法和数据结构

- ### 排序算法

  - [时间复杂度和对数器](算法和数据结构/排序算法/1.时间复杂度和对数器.md)
  - [排序分类和递归](算法和数据结构/排序算法/2.排序分类和递归.md)
  - [冒泡排序](算法和数据结构/排序算法/3.冒泡排序.md)
  - [插入排序](算法和数据结构/排序算法/4.插入排序.md)
  - [选择排序](算法和数据结构/排序算法/5.选择排序.md)
  - [归并排序](算法和数据结构/排序算法/6.归并排序.md)
  - [归并排序应用：小和问题和逆序对问题](算法和数据结构/排序算法/7.归并排序应用：小和问题和逆序对问题.md)
  - [荷兰国旗问题](算法和数据结构/排序算法/8.荷兰国旗问题.md)
  - [快速排序](算法和数据结构/排序算法/9.快速排序.md)
  - [堆排序](算法和数据结构/排序算法/10.堆排序.md)
  - [算法稳定性和比较器](算法和数据结构/排序算法/11.算法稳定性和比较器.md)
  - [桶排序](算法和数据结构/排序算法/12.桶排序.md)

- ### 数据结构

  - [栈和队列](算法和数据结构/数据结构/1.栈和队列.md)
  - 

<br>

## http

- [TCP和IP](http/1.TCP和IP.md)
- [与HTTP关系密切的协议：IP、TCP和DNS](http/2.与HTTP关系密切的协议：IP、TCP和DNS.md)
- [URI 、URL、URN](http/3.URI、URL、URN.md)
- [http的连接](http/4.http的连接.md)
- [Http状态码](http/5.Http状态码.md)
- [Http首部](http/6.Http首部.md)
- [http的请求方法](http/7.http的请求方法.md)
- [确保安全的https](http/8.确保安全的https.md)
- [Cookie和Session](http/20.Cookie和Session.md)



<br>

## java基础

- [java反射](java基础/java反射.md)
- [java基础-面试题](java基础/java基础-面试题.md)
- [socket编程](java基础/socket编程.md)

<br>

## java容器

- [java容器概述](java容器/1.java容器概述.md)
- [ArrayList源码分析](java容器/2.ArrayList源码分析.md)
- [LinkedList源码分析](java容器/3.LinkedList源码分析.md)
- [Map-散列表-红黑树概述](java容器/4.Map-散列表-红黑树概述.md)
- [HashMap源码分析](java容器/5.HashMap源码分析.md)



<br>

## java并发

- ### JMM内存模型

  - [并发的一些概念](java并发/JMM内存模型/1.并发的一些概念.md)
  - [Java线程基础](java并发/JMM内存模型/2.Java线程基础.md)
  - [JMM内存模型和volatile缓存一致性](java并发/JMM内存模型/3.JMM内存模型和volatile缓存一致性.md)
  - [原子性、可见性、有序性](java并发/JMM内存模型/4.原子性、可见性、有序性.md)
  - [重排序和happens-before](java并发/JMM内存模型/5.重排序和happens-before.md)

- ### 并发之tools限制

  - [CountDownLatch](java并发/并发包之tools限制/CountDownLatch.md)
  - [Semaphore](java并发/并发包之tools限制/Semaphore.md)
  - [CyclicBarrier](java并发/并发包之tools限制/CyclicBarrier.md)

- ### 并发包之collections容器

  - #### 并发list、set

    - 

  - #### 并发map

    - 

  - #### 并发Queue

    - [并发队列概述](java并发/并发包之collections容器/并发Queue/1.并发队列概述.md)
    - [ArrayBlockingQueue源码分析](java并发/并发包之collections容器/并发Queue/2.ArrayBlockingQueue源码分析.md)
    - [PriorityBlockingQueue源码分析](java并发/并发包之collections容器/并发Queue/3.PriorityBlockingQueue源码分析.md)
    - [ConcurrentLinkedQueue源码分析](java并发/并发包之collections容器/并发Queue/ConcurrentLinkedQueue源码分析.md)
    - [DelayQueue源码分析](java并发/并发包之collections容器/并发Queue/DelayQueue源码分析.md)
    - [LinkedBlockingQueue源码分析](java并发/并发包之collections容器/并发Queue/LinkedBlockingQueue源码分析.md)

- ### 并发设计模式

  - [Future模式](java并发/并发设计模式/Future模式.md)
  - [Master-Worker模式](java并发/并发设计模式/Master-Worker模式.md)
  - [生产者-消费者模式](java并发/并发设计模式/生产者-消费者模式.md)

- ### 线程池

  - [线程池](java并发/线程池/线程池.md)
  - [自定义线程池](java并发/线程池/自定义线程池.md)

<br>



## design-pattern

- ### 面向对象设计原则

  - [单一职责原则](design-pattern/面向对象设计原则/1.单一职责原则.md)
  - [开闭原则](design-pattern/面向对象设计原则/2.开闭原则.md)
  - [里式替换原则](design-pattern/面向对象设计原则/3.里式替换原则.md)
  - [接口隔离原则](design-pattern/面向对象设计原则/4.接口隔离原则.md)
  - [依赖倒置原则](design-pattern/面向对象设计原则/5.依赖倒置原则.md)

- ### 创建型模式

  - [单例模式](design-pattern/创建型模式/1.单例模式.md)
  - [工厂模式](design-pattern/创建型模式/2.工厂模式.md)

- ### 行为型模式

  - [代理模式](design-pattern/行为型模式/代理模式.md)
  - [组合模式](design-pattern/行为型模式/组合模式.md)

- ### 结构型模式

  - [策略者模式](design-pattern/结构型模式/策略者模式.md)
  - [观察者模式](design-pattern/结构型模式/观察者模式.md)

  

<br>

## 网络编程(bio,nio,aio)

- [网络IO模型](network_programming/网络IO模型.md)
- [BIO](network_programming/BIO.md)
- [NIO中的Buffer](network_programming/NIO中的Buffer.md)
- [NIO](network_programming/NIO.md)
- [AIO](networ_programming/AIO.md)

<br>

## spring

<br>

## redis

- [Redis](redis/Redis.md)

<br>

## solr

- [solr](solr/solr入门.md)

## pyspider

- [pyspider](pyspider/pyspider.md)
