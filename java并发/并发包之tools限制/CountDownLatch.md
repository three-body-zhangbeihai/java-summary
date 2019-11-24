# CountDownLatch

CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用。

<br>

```java
package tools;

import java.util.concurrent.CountDownLatch;

//只有当t2,t3线程执行完，才能唤醒t1
public class UseCountDownLatch {

    public static void main(String[] args) {

        final CountDownLatch countDown = new CountDownLatch(2);    //等待其他2个线程执行完毕才会执行

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("进入线程t1" + "等待其他线程处理完成...");
                    countDown.await(); //等待其他线程的唤醒
                    System.out.println("t1线程继续执行...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t2线程进行初始化操作...");
                    Thread.sleep(3000);
                    System.out.println("t2线程初始化完毕，通知t1线程继续...");
                    countDown.countDown(); //唤醒正在等待的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t3线程进行初始化操作...");
                    Thread.sleep(4000);
                    System.out.println("t3线程初始化完毕，通知t1线程继续...");
                    countDown.countDown(); //唤醒正在等待的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

```

<br>

**结果**：

```
t3线程进行初始化操作...
进入线程t1等待其他线程处理完成...
t2线程进行初始化操作...
t2线程初始化完毕，通知t1线程继续...
t3线程初始化完毕，通知t1线程继续...
t1线程继续执行...
```

<br>

