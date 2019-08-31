# 四、Map 、散列表、红黑树



## 一、Map

<br>

**Map是这样的一种数据存储结构：一个key，对应一个value，就是映射。**

<br>

### 1.1Map关系图

![image](https://github.com/wenhuohuo/java-summary/blob/master/images/map关系图.png)

<br>

### 1.2 Map 的特点

- 包含键值对
- 键和值一一对应（键、值都是唯一的）

<br>

### 1.3 Map 和 Collection的区别

1. Map集合存储元素是成对出现的，Map的键是唯一的，值是可以重复的
2. Collection集合存储的元素是单独出现的，Collection中**Set类**是惟一的，**List** 是可重复的

<br>

### 1.4 常见Map类

1. HashMap

   底层数据结构是：**数组+链表+红黑树 **

2. TreeMap

   底层数据结构是：**红黑树**

其中ConcurrentHashMap在并发容器模块讲。

<br>



## 二、 散列表

### 2.1 简介

**散列表** （Hash table，也叫 **哈希表**），是根据**关键码值(Key value)**而直接进行访问的 **数据结构** 。也就是

说，它**通过把关键码值映射到表中一个位置来访问记录**，**以加快查找的速度**。这个映射函数叫做**散列函数**，存放记

录的**数组**叫做**散列表**。

给定表**M**，存在函数 **f(key)**，对任意给定的关键字值**key**，代入函数后若能得到包含该关键字的记录在表中的地

址，则称表M为**哈希(Hash）表**，函数f(key)为**哈希(Hash) 函数。**

(来源：百度百科)



<br>

### 2.2 散列表工作原理

散列表为每个对象计算出一个整数，称为 **散列码**，根据这些 **散列码** 保存到对应的位置上。

在Java中，散列表用的是 **链表数组** 实现的，每个列表称为 **桶**。

<br>

由 **hashCode 函数** 得到的散列码

| 串    | 散列码 |
| ----- | ------ |
| “Lee” | 76268  |
| “lee" | 107020 |
| "eel" | 100300 |

<br>

**散列表**：

![image](https://github.com/wenhuohuo/java-summary/blob/master/images/散列表.png)

<br>



一个 **桶（散列表）** 可能会遇到被占用的情况（hashCode散列码相同，就存储在同一个位置。其实，hashCode一

样，equals() 不一定为true），这种情况无法避免，称为： **散列冲突**。

- 此时需要 **用该对象与桶上的对象进行比较，看看该对象是否存在桶子上了** ~如果存在，就不添加了，如果不存

  在则添加到桶子上

- 当然了，如果hashcode函数设计得足够好，桶的数目也足够，这种比较是很少的~

- 在 **JDK1.8**中，**桶满时** 会从 **链表变成平衡二叉树**

如果散列表太满，**是需要对散列表再散列，创建一个桶数更多的散列表，并将原有的元素插入到新表中，丢弃**

**原来的表**~

- 装填因子(load factor) **决定了何时** 对散列表再散列~
- 装填因子默认为0.75，如果表中 **超过了75%的位置** 已经填入了元素，那么这个表就会用 **双倍的桶数** 自动进行再散列

<br>



## 三、红黑树

### 3.1 二叉查找树

**简介**:

二叉查找树（Binary Search Tree），又称二叉排序树（Binary Sort Tree），亦称二叉搜索树。

<br>

**特点：**

- **左**子树上所有结点的值均**小于或等于**它的根结点的值。
- **右**子树上所有结点的值均**大于或等于**它的根结点的值。
- 左、右子树也分别为二叉排序树。

<br>

**图示**:

![image](https://github.com/wenhuohuo/java-summary/blob/master/images/二叉查找树.jpg)

<br>

**查找10**：

![image](https://github.com/wenhuohuo/java-summary/blob/master/images/二叉查找树-查找10.jpg)

<br>

**普通二叉查找树存在的问题。**
<br>

![image](https://github.com/wenhuohuo/java-summary/blob/master/images/二叉查找树存在的问题.png)



<br>

### 3.2 红黑树

**红黑树的约束**：

0. 红黑树是**二叉搜索树。**

1. **根**节点是**黑色**。

2. **节点**是红色或黑色。

3. 每个**叶子节点**都是**黑色的空节点**（NIL节点）。

4. 每个红色节点的两个**子节点都是黑色**。(从每个叶子到根的所有路径上**不能有两个连续的红色节点**)

5. 从任一节点到其每个叶子的所有路径都包含**相同数目的黑色节点**(每一条树链上的黑色节点数量 **（称之为“黑高”）必须相等)**。

<br>
**图示**：
<br>
![image](https://github.com/wenhuohuo/java-summary/blob/master/images/红黑树图.jpg)
<br>

**红黑树可以解决上述的问题，主要通过下面两个方法。**

- **旋转**：顺时针旋转和逆时针旋转
- **反色**：交换红黑的颜色

<br>

**通过旋转和反色，同时做到不违反红黑树的约束，或者说通过两种方法，把红黑树达到约束的范围。**

<br>

<br>



## 参考

[java集合详解--什么是Map](https://blog.csdn.net/wz249863091/article/details/77483948)

[漫画：什么是红黑树？](https://zhuanlan.zhihu.com/p/31805309)

[二叉查找树-百度百科](https://baike.baidu.com/item/%E4%BA%8C%E5%8F%89%E6%8E%92%E5%BA%8F%E6%A0%91?fromtitle=%E4%BA%8C%E5%8F%89%E6%9F%A5%E6%89%BE%E6%A0%91&fromid=7077965)

[红黑树-百度百科](https://baike.baidu.com/item/%E7%BA%A2%E9%BB%91%E6%A0%91/2413209?fr=aladdin)

[Map集合、散列表、红黑树介绍](<https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484135&idx=1&sn=be2221572ffc82f5792dd4ef1ea8e309&chksm=ebd743e6dca0caf00f188cabafc73665b875bf1cbe92cf3626cedb4f80313bb20a7429b8ec3f#rd>)
