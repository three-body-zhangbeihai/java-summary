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
        /*IntBuffer buf1 = IntBuffer.allocate(10);
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
