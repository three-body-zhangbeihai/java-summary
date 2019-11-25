# NIO中的Buffer

## 概述

Buffer本身是一个内存块，底层是数组，数据的读写都是通过Buffer类实现的。即同一个Buffer即可以写数据也可以读数据，通过filp() 方法进行Buffer位置状态的翻转。

缓冲区Buffer主要是和通道数据交互，即从通道中读入数据到缓冲区，和从缓冲区把数据写入到通道中，通过这样完成对数据的传输。

<br>

## 几个主要的变量

**容量（Capacity）**：

缓冲区能够容纳的数据元素的最大数量。在缓冲区被创建时确定，永远不会改变

**界限（Limit）：**

从缓冲区读时，指定缓冲区的有效数据

写到缓冲区时，指定还有多少空间可以放。

**位置（Position）：**

游标，初始化为0。每put一个数据，游标右移一位。

**get()方法**：

返回当前position位置的数据，position位置+1，一般在get之前会用filp()归位游标。

**filp()方法**：

将当前position的位置赋给 limit，position归0.

即： **limit = position;  position = 0;**

**remaining()**:

获取Buffer中 **limit - position** 的个数。

**clear()**:

清空Buffer， **limit = capacity;  position = 0;**

<br>

## 示例

```java
package nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

//NIO中的Buffer
public class TestBuffer {
    public static void main(String[] args) {
        //初始化容量为8.
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] bytes = {'a', 'b', 'c'};

        // java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
        // pos:0，游标位置默认为0
        // lim:8, 有效数据空间为8
        // cap:8, 容量始终都是8
        System.out.println("写入数据之前:" + buffer);

        buffer.put(bytes); // position -> 3
        // java.nio.HeapByteBuffer[pos=3 lim=8 cap=8]
        System.out.println("写入数据之后:" + buffer);

        //重置游标之后
        buffer.flip();
        // java.nio.HeapByteBuffer[pos=0 lim=3 cap=8]
        // lim = pos = 3
        // pos = 0
        System.out.println("重置游标之后:" + buffer);

        // 97 98 99 (limit = 3, position = 0, limit - pos = 3)
        System.out.println("---------------");
        for (int i =0;i <buffer.remaining();i++){
            System.out.print(buffer.get(i) + " ");
        }
        System.out.println();

        //清空Buffer
        buffer.clear();
        System.out.println("清空Buffer后:" +buffer);
        // limit = capacity, position = 0;
        for(int i = 0; i<buffer.remaining();i++){
            System.out.print(buffer.get(i) + " ");
        }


        System.out.println();
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        //清空Buffer
        buffer.clear();
        System.out.println(buffer.get());

        ///////////////////////////////////////////////
        System.out.println("\n");

        // 2 wrap方法使用
        //  wrap方法会包裹一个数组: 一般这种用法不会先初始化缓存对象的长度，因为没有意义，最后还会被wrap所包裹的数组覆盖掉。
        //  并且wrap方法修改缓冲区对象的时候，数组本身也会跟着发生变化。
        int[] arr = new int[]{1,2,5};
        IntBuffer buf1 = IntBuffer.wrap(arr);
        System.out.println(buf1);

        IntBuffer buf2 = IntBuffer.wrap(arr, 0 , 2);
        //这样使用表示容量为数组arr的长度，但是可操作的元素只有实际进入缓存区的元素长度
        System.out.println(buf2);

        ///////////////////////////////////////////////
        System.out.println("\n");


        // 3 其他方法
       /* IntBuffer buf1 = IntBuffer.allocate(10);
        int[] arr = new int[]{1,2,5};
        buf1.put(arr);
        System.out.println(buf1);
        //一种复制方法
        IntBuffer buf3 = buf1.duplicate();
        System.out.println(buf3);

        //设置buf1的位置属性
        //buf1.position(0);
        buf1.flip();
        System.out.println(buf1);

        System.out.println("可读数据为：" + buf1.remaining());

        int[] arr2 = new int[buf1.remaining()];
        //将缓冲区数据放入arr2数组中去
        buf1.get(arr2);
        for(int i : arr2){
            System.out.print(Integer.toString(i) + ",");
        }
        */
    }
}

```

<br>

**结果**：

```
写入数据之前:java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
写入数据之后:java.nio.HeapByteBuffer[pos=3 lim=8 cap=8]
重置游标之后:java.nio.HeapByteBuffer[pos=0 lim=3 cap=8]
---------------
97 98 99 
清空Buffer后:java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
97 98 99 0 0 0 0 0 
97
98
97


java.nio.HeapIntBuffer[pos=0 lim=3 cap=3]
java.nio.HeapIntBuffer[pos=0 lim=2 cap=3]


/*
java.nio.HeapIntBuffer[pos=3 lim=10 cap=10]
java.nio.HeapIntBuffer[pos=3 lim=10 cap=10]
java.nio.HeapIntBuffer[pos=0 lim=3 cap=10]
可读数据为：3
1,2,5,
*/
```

<br>

