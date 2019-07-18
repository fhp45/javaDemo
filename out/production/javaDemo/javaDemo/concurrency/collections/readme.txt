关于并发集合的使用

注意：没有ConcurrentArrayQueue
Queue的分类：
实现方式分类：
    通过内部加锁实现的queue：
    ConcurrentLinkedQueue
    通过阻塞实现的queue：
    BlockingQueue-> LinkedBlockingQueue,ArrayBlockingQueue,DelayedQueue,TransferQueue
有界无界分类：
    有界的：
    ArrayBlockingQueue,LinkedBlockingQueue在初始化时指定容量，也可以作为有界队列
    无界的
    LinkedBlockingQueue, ConcurrentLinkedQueue


LinkedBlockingDeque和LinkedBlockingDeque的区别？
LinkedBlockingDeque无非是用一个独占锁来保持线程安全，然后用Condition来做阻塞操作

ConcurrentLinkedDeque不是阻塞队列所以没有用到条件原语

在日常开发中，如何选择各种队列呢？

以LinkedBlockingQueue、ArrayBlockingQueue、SynchronousQueue为例，根据需求可以从很多方面考量：

1、考虑应用场景中对队列边界的要求。ArrayBlockingQueue是有明确的容量限制的，而LinkedBlockingQueue则取决于我们是否在创建时指定，

SynchronousQueue则干脆不能缓存任何元素。

2、从空间利用角度，数组结构的ArrayBlockingQueue要比LinkedBlockingQueue紧凑，因为其不需要创建所谓节点，但是其初始分配阶段就需要一段连续的空间，所以初始内存需求更大。

3、通用场景中，LinkedBlockingQueue的吞吐量一般优于ArrayBlockingQueue，因为它实现了更加细粒度的锁操作

4、ArrayBlockingQueue实现比较简单，性能更加预测，属于表现稳定的“选手”

5、如果我们需要实现的是两个线程之间接力性（handoff）的场景，你可能会选择CountDownLatch，但是SynchronousQueue也是完美符合这种场景的，而且线程间协调和数据传输统一起来，代码更加规范

6、可能令人意外的是，很多时候SynchronousQueue的性能表现，往往大大超过其他表现，尤其是在队列元素较小的场景。