# NIO

[TOC]

<br>

## 概述

**NIO (Non-blocking I/O):** NIO是一种 **同步非阻塞** 的I/O模型，在Java 1.4 中引入了NIO框架，对应 java.nio 包，提供了 **Channel , Selector，Buffer** 等抽象。

NIO中的N可以理解为Non-blocking，不单纯是New。它支持面向缓冲的，基于**通道的I/O操作方法**。

NIO提供了与传统BIO模型中的 `Socket` 和 `ServerSocket` 相对应的  **`SocketChannel`** 和 **`ServerSocketChannel`** 两种不同的套接字通道实现，**两种通道都支持阻塞和非阻塞两种模式**。

阻塞模式使用就像传统中的支持一样，比较简单，但是性能和可靠性都不好；非阻塞模式正好与之相反。

对于低负载、低并发的应用程序，可以使用同步阻塞I/O来提升开发速率和更好的维护性；

**对于高负载、高并发的（网络）应用，应使用 NIO 的非阻塞模式来开发**

<br>



## 几个概念

### Buffer（缓冲区）





### Channel（管道、通道）





### Selector（选择器、多路复用器）













## 参考

[Java 中 IO 流分为几种?BIO,NIO,AIO 有什么区别?](https://www.jianshu.com/p/91f43c73a760)<br>




  