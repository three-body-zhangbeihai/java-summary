# 三、LinkedList 源码分析

<br>

**版本 : JDK 1.8**

<br>



## 一、 概述

> LinkedList是一种可以在任何位置进行**高效**地插入和移除操作的有序序列，它是基于**双向链表**实现的，是**线程**
>
> **不安全**的，**允许元素为null**的双向链表。



- 底层数据结构：**双向链表**
- 插入删除比较快：**O(1)**,查询相对较慢 **O(n)**
- 链表结构，所以分配的空间不要是连续的

- 线程不安全

<br>

**双向链表结构：**

![image](images/双向链表.png)

<br>



## 二、LinkedList 的继承关系

<br>

**LinkedList的继承关系图：**

![image](images/LinkedList继承关系.png)

<br>

可以看到不仅继承了 **List** ，还继承了 **Queue** 接口。

**源码中的定义**：

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

- AbstractSequentialList这个类提供了List的一个骨架实现接口，以尽量减少实现此接口所需的工作量由“顺序

  访问”数据存储（如链接列表）支持。对于随机访问数据（如数组），应使用AbstractList优先于此类。

- 实现了List接口,意味着LinkedList元素是 **有序** 的,可以 **重复** 的,可以有null元素的集合.

- Deque是Queue的子接口,Queue是一种队列形式,而 **Deque是双向队列** ,它支持 **从两个端点方向检索和插入元素.**

- 实现了Cloneable接口,标识着可以它可以被复制.注意,ArrayList里面的clone()复制其实是浅复制(不知道此概念

  的赶快去查资料,这知识点非常重要).

- 实现了Serializable 标识着集合可被 **序列化。**

<br>



## 三、源码解析



### 3.1、成员变量

```java
// 集合元素数量
transient int size = 0;
 /**
  * Pointer to first node.
  * Invariant: (first == null && last == null) ||
  *            (first.prev == null && first.item != null)
 */
//指向第一个节点的指针
transient Node<E> first;

/**
 * Pointer to last node.
 * Invariant: (first == null && last == null) ||
 *            (last.next == null && last.item != null)
 */
//指向最后一个节点的指针
transient Node<E> last;
```

<br>



### 3.2 节点的定义



```java
private static class Node<E> {
    E item;			//节点数据
    Node<E> next;	//指向下一节点的指针
    Node<E> prev;	//指向上一节点的指针

    //节点
    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

LinkedList 节点定义是 **静态内部类**。**可能的原因** 如下：

> 普通内部类会有外部类的强引用,而静态内部类就没有.有外部类的强引用的话,很容易造成内存泄漏,写成静态内部类可以避免这种情况的发生。

<br>



### 3.3 构造方法

<br>

**无参构造方法**

```java
public LinkedList() {}
```

<br>

**集合为参数的构造方法**

将集合 c 所有元素插入到LinkedList中

```java
public LinkedList(Collection<? extends E> c) {
    this();
    addAll(c);
}
```

<br>





### 3.4 添加元素

<br>

#### 3.4.1 直接添加元素  add(E e)  (在链表末尾添加)

<br>

**大致思路：**

- 构建一个新节点
- 将该节点作为新的尾节点，如果空链表，则头结点 = 尾节点 = 新节点。非空，在尾节点后面插入新节点
- 增加链表长度

**add(E e)**:

```java
public boolean add(E e) {
    linkLast(e);	//在链表末尾添加数据
    return true;
}
```

<br>

**linkLast(e)**:

```java
/**
  * Links e as last element.
  */
void linkLast(E e) {
    //1. 暂记尾节点
    final Node<E> l = last;
    //2.构建节点。把咱记尾节点初始化并赋值给新节点
    final Node<E> newNode = new Node<>(l, e, null);
    //新节点变成尾节点
    last = newNode;
    
    //判断链表是否为空
    if (l == null)//为空，新节点赋给头结点（空链表插入第一个元素，头结点=尾节点）
        first = newNode;
    else		  //不为空，尾节点指向新节点
        l.next = newNode;
    size++;	//链表长度增加
    modCount++;
}
```

<br>

**思考：为何在add(E e) 方法中，直接返回true。而不是，添加成功返回true，失败返回false呢？**

**原因**: 因为分配的内存空间不是连续的，只要还能分配空间，就不添加失败。当空间不够分配，会抛出 **OutofMemory(内存溢出)**。

<br>



#### 3.4.2 在链表尾部添加元素  addLast(E e)、 offerLast(E e)

<br>

**原理：实现和add(E e) 一样，都是在尾部添加结点**

```java
public void addLast(E e) {
    linkLast(e);
}
```

```java
public boolean offerLast(E e) {
    addLast(e);
    return true;
}
```

<br>



#### 3.4.3 在头部添加元素  addFirst(E e)

<br>

**大致思路：**

- 新建结点f为原链表头结点
- 新建newNode结点。判断原头结点f是否为空，空，则 新结点=头结点=尾节点。非空，则newNode为新的头结点，原头结点f为第二节点
- 链表长度增加

<br>

```java
public void addFirst(E e) {
    linkFirst(e);
}
```



```java
/**
 * Links e as first element.
 */
private void linkFirst(E e) {
    final Node<E> f = first;	//f结点 = 原链表的头结点
    final Node<E> newNode = new Node<>(null, e, f); //新节点用f去初始化
    first = newNode;	//新节点成为头结点
    if (f == null)		//原来链表头结点f为空(即链表为空)，则尾节点=新节点=头结点
        last = newNode;
    else
        f.prev = newNode;//链表非空，则原链表头结点成为第二结点（前向指针指向新节点）
    size++;	//长度增加
    modCount++;
}
```

<br>



#### 3.4.4 push(E e)、 offer()、 offerFirst(E e)

**原理: 内部原理和 add(E e) 方法几乎一模一样**：

```java
public void push(E e) {
    addFirst(e);
}
```

```java
public boolean offer(E e) {
    return add(e);
}
```

```java
public boolean offerFirst(E e) {
    addFirst(e);
    return true;
}
```

<br>



#### 3.4.5 在指定位置添加元素 add(int index, E element)

<br>

**大致思路:**

- 首先判断一下插入的位置是在链表的最后还是在链表中间.

- 如果是插入到链表末尾,那么将之前的尾节点指向新节点

- 如果是插入到链表中间 

- - 需要先找到链表中index索引处的节点.
  - 将新节点赋值为index处节点的前一个节点
  - 将index处节点的前一个节点的next指针赋值为新节点

<br>

```java
public void add(int index, E element) {
    checkPositionIndex(index);	//检查下标是否越界

    if (index == size)	//如果指定位置是末尾，则直接在末尾插入（linkLast方法前面已讲过，不赘述)
        linkLast(element);
    else //否则，在指定位置插入
        linkBefore(element, node(index));
}
```

<br>



**检查是否越界方法：checkPositionIndex(int index)**

```java
private void checkPositionIndex(int index) {
    if (!isPositionIndex(index))
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
```

<br>



**node(index) 方法：**

```java
Node<E> node(int index) {
    // assert isElementIndex(index);

    if (index < (size >> 1)) {//如果链表在前半部分，则从前往后找，找到index位置的结点元素，返回该结点
        Node<E> x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return x;
    } else { //index在链表后半部分，从后往前找，找到index位置的结点元素，返回该结点
        Node<E> x = last;
        for (int i = size - 1; i > index; i--)
            x = x.prev;
        return x;
    }
}
```

<br>



**linkBefore 方法**：

```java
/**
 * Inserts element e before non-null Node succ.
 */
void linkBefore(E e, Node<E> succ) {
    // assert succ != null;
    //记录index位置的前一个结点
    final Node<E> pred = succ.prev;	
    //构建新节点，数据是e，前一个结点是pred，后一个结点是index位置的结点
    final Node<E> newNode = new Node<>(pred, e, succ);
    //将新节点作为index结点的前一个结点
    succ.prev = newNode;
    //判断pred是否为空，如果为空,那么说明succ是之前的头节点,现在新节点在succ的前面,所以新节点是头节点
    if (pred == null)
        first = newNode;
    else//5. succ的前一个节点不是空的话,那么直接将succ的前一个节点指向新节点就可以了
        pred.next = newNode;
    size++;//链表长度加1
    modCount++;
}
```

**pred.next=newNode;中** ，因为新创建的pred结点指向的是原来index位置前一个节点的**内存地址**，所以，用pred.next来指向是行得通的

<br>

**图示**：

![image](images/LinkedList在指定位置插入结点.png)

<br>



#### 3.4.6 指定集合所有元素插入末尾  addAll(collection<? extends E> c)

<br>

**大致思路：**

- 将需要添加的集合转成数组a

- 判断需要插入的位置index是否等于链表长度size,如果相等则插入到链表最后;如果不相等,则插入到链表中间,

  还需要找到index处节点succ,方便拿到该节点的前后节点信息.

- 记录index索引处节点的前一个节点pred,循环将集合中所有元素连接到pred的后面

- 将集合最后一个元素的next指针指向succ,将succ的prev指针指向集合的最后一个元素

<br>

```java
public boolean addAll(Collection<? extends E> c) {
    return addAll(size, c);
}
```

<br>

```java
public boolean addAll(int index, Collection<? extends E> c) {
    //1. 检查参数合法性
    checkPositionIndex(index); 
	//2. 将集合转为数组
    Object[] a = c.toArray(); 
    //3. 得到要插入元素的个数
    int numNew = a.length;	  
    if (numNew == 0)//插入个数为0，返回false，不执行
        return false;

    Node<E> pred, succ;	
    //4. 判断插入位置index是否与size相等
    //相等，则在末尾插入
    //不等，则在链表中间index处插入
    if (index == size) {
        succ = null;
        pred = last;
    } else {
        succ = node(index);	//找到index索引的结点
        pred = succ.prev;	//找到index索引的前一个结点
    }
    
	//5. 循环将集合中所有元素连接到pred后面
    for (Object o : a) {
        @SuppressWarnings("unchecked") E e = (E) o;
        Node<E> newNode = new Node<>(pred, e, null);
        if (pred == null) //如果前一个是空,那么将新节点作为头结点
            first = newNode;
        else//指向新结点
            pred.next = newNode;
        //再将辅助结点从新插入结点开始，重复
        pred = newNode;
    }
	//6. 判断succ是否为空
    //为空的话,那么集合的最后一个元素就是尾节点
    //非空的话,那么将succ连接到集合的最后一个元素后面
    if (succ == null) {
        last = pred;
    } else {
        pred.next = succ;
        succ.prev = pred;
    }

    size += numNew; //长度直接 + numNew
    modCount++;
    return true;
}
```

<br>

### 3.5删除元素

#### 3.5.1 移除链表的第一个元素：remove()、 removeFirst()

<br>

**原理:都是用removeFirst() 方法**

```java
public E remove() {
    return removeFirst();
}
```

<br>

```java
public E removeFirst() {
    //新增结点，引用头结点地址
    final Node<E> f = first;
    //如果头结点为空（即原链表为空），报错
    if (f == null)
        throw new NoSuchElementException();
    //不为空，调用移除方法
    return unlinkFirst(f);
}
```

<br>

**具体的移除方法**

```java
/**
 * Unlinks non-null first node f.
 */
private E unlinkFirst(Node<E> f) {
    // assert f == first && f != null;
    //先得到头结点的数据
    final E element = f.item;
    //得到头结点的下一个结点
    final Node<E> next = f.next;
    //头结点的数据置为空
    f.item = null;
    //头结点的下一个指针地址为空，GC回收该结点
    f.next = null; // help GC
    //头结点first 引用变为下一个结点(原链表第二个结点)地址
    first = next;
    //如果下一个结点为空，链表已无结点，末尾结点置为空
    if (next == null)
        last = null;
    else  //否则，下一个结点前向指针为空
        next.prev = null;
    size--;	//长度减1
    modCount++;
    return element;//返回已移除的头结点的数据
}
```

<br>



#### 3.5.2 移除指定位置的结点 remove(int index)



```java
public E remove(int index) {
    //1. 检查下标合法性
    checkElementIndex(index);
    //2. 得到index所在位置的结点（前面已讲解 node(index) 方法。），并作为参数调用具体的移除方法
    return unlink(node(index));
}
```

<br>

**具体移除方法**：

```java
/**
 * Unlinks non-null node x.
 */
E unlink(Node<E> x) {
    // assert x != null;
    //1. 先得到 移除结点的数据
    final E element = x.item;	
    //2. 得到移除结点的后一个结点
    final Node<E> next = x.next;
    //3. 得到移除结点的前一个结点
    final Node<E> prev = x.prev;

    //4. 如果前一个结点为空，说明移除的是头结点，让第二个结点作为头结点
    if (prev == null) {
        first = next;
    } else { // 不为空，
        prev.next = next;//让 前一个结点的下一个指针 指向 后一个结点的地址
        x.prev = null;   //移除结点的前指针置为空
    }
    //5. 如果后一个结点为空，说明是最后一个结点，让移除结点的前一个结点作为末尾结点
    if (next == null) {
        last = prev;
    } else { //非空
        next.prev = prev;//让 后一个结点的前指针 指向前一个结点 的地址
        x.next = null;	 //移除结点的后指针 置为空
    }

    x.item = null;	//移除节点的数据置为空
    size--;			//链表长度 -1
    modCount++;
    return element;	//返回已被移除节点的数据
}
```

<br>

#### 3.5.3 移除第一次出现指定元素的结点  remove(Object o)

```java
public boolean remove(Object o) {
    //1. 指定元素为空空
    if (o == null) {
        //从头循环，找到 值为null 的第一个结点，移除
        for (Node<E> x = first; x != null; x = x.next) {
            if (x.item == null) {
                unlink(x);
                return true;
            }
        }
    } else {  //2. 指定元素非空
        //从头循环，用equals比较值，找到，则移除。
        for (Node<E> x = first; x != null; x = x.next) {
            if (o.equals(x.item)) {
                unlink(x);
                return true;
            }
        }
    }
    return false;
}
```

<br>





### 3.6 修改元素

#### 3.6.1 在指定位置修改元素 set(int index, Element e)

```java
public E set(int index, E element) {
    //1. 检查下标合法性
    checkElementIndex(index);
    //2. 得到index所在结点
    Node<E> x = node(index);
    //3. 得到旧结点的值
    E oldVal = x.item;
    //4. 将旧结点赋予新值 element
    x.item = element;
    //5. 返回旧值
    return oldVal;
}
```

<br>



### 3.7 查询元素

#### 3.7.1 获取链表的第一个元素  element()、 getFirst()

```java
public E element() {
    return getFirst();
}
```

<br>

```java
public E getFirst() {
    //得到头结点
    final Node<E> f = first;
    //头结点为空，抛出错误
    if (f == null)
        throw new NoSuchElementException();
    //返回头结点的值
    return f.item;
}
```

<br>



#### 3.7.2 获取指定位置的元素

```java
public E get(int index) {
	//检查下标是否越界
    checkElementIndex(index);
    //得到index位置的结点，在得到值。 node(index) 方法在前面已经讲过。
    return node(index).item;
}
```

<br>

#### 3.7.3 获取链表最后一个元素 getLast()

```java
public E getLast() {
    //得到最后一个结点对象
    final Node<E> l = last;
    //最后一个结点为空，报错
    if (l == null)
        throw new NoSuchElementException();
    //返回值
    return l.item;
}
```

<br>



#### 3.7.4 遍历链表   listIterator(int index)

**功能**：

- ListIterator只能用于List及其子类型。
- 有add()方法,可以往链表中添加对象
- 可以通过**hasNext()和next()往后顺序遍历**,也可以通过**hasPrevious()和previous()实现往前遍历**
- 可以通过nextIndex()和previousIndex()返回当前索引处的位置
- 可以通过set()实现当前 **遍历对象的修改**

```java
public ListIterator<E> listIterator(int index) {
    checkPositionIndex(index);
    return new ListItr(index);
}
```

<br>

```java
private class ListItr implements ListIterator<E> {
    //上一次返回的节点
    private Node<E> lastReturned;
    //下一个节点
    private Node<E> next;
    //下一个节点索引
    private int nextIndex;
    private int expectedModCount = modCount;

    ListItr(int index) {
        // assert isPositionIndex(index);
        //如果是最后一个节点,那么返回next是null    
        //如果不是最后一个节点,那么找到该index索引处节点
        next = (index == size) ? null : node(index);
        nextIndex = index;
    }

    public boolean hasNext() {
        //判断是否还有下一个元素
        return nextIndex < size;
    }

    //获取下一个元素
    public E next() {
        checkForComodification();
        //1. 如果没有下一个元素   抛异常
        if (!hasNext())
            throw new NoSuchElementException();

        //2. 记录上一次遍历到的节点
        lastReturned = next;
        //3. 往后移
        next = next.next;
        //4. 索引+1
        nextIndex++;
        //5. 将遍历到的节点数据值返回
        return lastReturned.item;
    }

    public boolean hasPrevious() {
        //判断是否还有前一个元素
        return nextIndex > 0;
    }

    //获取前一个元素
    public E previous() {
        checkForComodification();
        //1. 如果没有前一个元素,则抛异常
        if (!hasPrevious())
            throw new NoSuchElementException();

        //2. 当next是null的时候,赋值为last     
        //不是null的时候,往前移动
        lastReturned = next = (next == null) ? last : next.prev;
        //3. index-1  因为是往前
        nextIndex--;
        //4. 将遍历到的节点数据值返回
        return lastReturned.item;
    }

    public int nextIndex() {
        return nextIndex;
    }

    public int previousIndex() {
        return nextIndex - 1;
    }

    //移除当前遍历到的元素
    public void remove() {
        checkForComodification();
        //1. 移除当前遍历到的元素为null,直接抛错误
        if (lastReturned == null)
            throw new IllegalStateException();

        //2. 记录当前节点的下一个节点
        Node<E> lastNext = lastReturned.next;
        //3. 删除当前节点
        unlink(lastReturned);
        //4. 如果next == lastReturned,说明当前是从前往后遍历的,那么将next赋值为下一个节点
        //如果不相等,那么说明是从后往前遍历的,这时只需要将index-1就行了
        if (next == lastReturned)
            next = lastNext;
        else
            nextIndex--;
        //5. 将移除的节点置空
        lastReturned = null;
        expectedModCount++;
    }

    //设置当前正在遍历的节点的值,用ListIterator可以在遍历的时候修改值
    public void set(E e) {
        if (lastReturned == null)
            throw new IllegalStateException();
        checkForComodification();
        //设置当前遍历的节点的值
        lastReturned.item = e;
    }

    //添加一个值
    public void add(E e) {
        checkForComodification();
        lastReturned = null;
        //如果next为null,那么添加到最后
        //否则,将e元素添加到next的前面
        if (next == null)
            linkLast(e);
        else
            linkBefore(e, next);
        nextIndex++;
        expectedModCount++;
    }

    public void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        //循环 往后遍历   没遍历一个节点就回调当前节点的数据值
        while (modCount == expectedModCount && nextIndex < size) {
            action.accept(next.item);
            lastReturned = next;
            next = next.next;
            nextIndex++;
        }
        checkForComodification();
    }

    //判断一下该列表是否被其他线程改过(在迭代过程中)   修改过则抛异常
    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

<br>

**总结：**

可以用 **`listIterator(int index)`** 在指定位置循环遍历过程中配合其他方法做 **增、删、改、查。**





