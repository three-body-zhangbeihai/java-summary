# Semaphore

Semaphore翻译成字面意思为 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。

<br>

```java
package tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UseSemaphore {

    //指定同时执行的线程的数量，当指定的线程中有哪一个执行完毕，其他线程才能执行，否则会阻塞着。

    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        System.out.println("Accessing: " + NO);
                        //模拟实际业务逻辑
                        Thread.sleep((long) (Math.random() * 10000));
                        // 访问完后，释放
                        System.out.println();
                        semp.release();
                    } catch (InterruptedException e) {
                    }
                }
            };
            exec.execute(run);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //System.out.println(semp.getQueueLength());



        // 退出线程池
        exec.shutdown();
    }

}

```

<br>
**结果**：

```
Accessing: 1
Accessing: 0
Accessing: 2
Accessing: 3
Accessing: 4

Accessing: 5

Accessing: 6

Accessing: 7

Accessing: 8

Accessing: 9

Accessing: 10

Accessing: 11

Accessing: 12

Accessing: 13

Accessing: 14

Accessing: 15

Accessing: 16

Accessing: 17

Accessing: 18

Accessing: 19

```

<br>

