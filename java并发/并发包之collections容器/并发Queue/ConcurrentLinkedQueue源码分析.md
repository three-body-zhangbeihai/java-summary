# ConcurrentLinkedQueue源码分析



ConcurrentLinkedQueue：是一个适用于高并发场景下的队列，通过**无锁**的方式，实现了高并发状态下的高性能，通常ConcurrentLinkedQueue性能好于BlockingQueue。

- 它是一个基于**链表**的**无界线程安全队列**。
- 该队列遵循**先进先出(FIFO)**原则。头是最先加入，尾是最近加入的。
- 该队列**不允许null**元素。

<br>

