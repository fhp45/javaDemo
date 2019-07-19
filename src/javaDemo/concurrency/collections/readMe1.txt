介绍并发集合：
并发集合包括了Map,List，Set以及最总要的Queue
普通的线程安全的Map,List,Set包括:
Vector,HashTable,Collections.synchronizedList(new List)等
并发包下的包括【Set和Map的实现原理一致，只是map多了value】
ConcurrentHashMap：是基于分段锁机制实现的，默认分为了16个Segment，Segment继承了ReentrantLock, 所以默认支持16个线程并发。
ConcurrentSkipListMap: 跳表，有序的ConcurrentHashMap

CopyOnWriteList：写时复制集合中包含一个重入锁，在读时不加锁，在写时加锁。所以写的同时可以进行读取，但是不保证读取正确，保证的最终一致性。
                适用于读多写少的环境下。
    【注意：只有CopyOnWriteArrayList/Set.因为写时的复制是基于数组的】

加下来是重头戏队列:
队列分为有界/无界【array/linked】，阻塞/非阻塞队列【blocking/concurrent】
    有界/无界队列实现的区别:当基于链表的LinkedQueue初始化时不设置长度时，就是无界队列
                      基于数组的ArrayQueue以及初始化时设置长度的LinkedQueue都是有界队列。
                      有界队列可以避免内存溢出。
    阻塞/非阻塞队列实现的区别： 阻塞队列基于Lock/Condition实现的，通常来说包含两个Condition--notEmpty/notFull
                             当队列空的时候，阻塞读取的线程，当线程满的时候阻塞写线程。
                             非阻塞队列只能是无界的，实现方式则是用循环CAS的方式来实现,非阻塞队列只有ConcurrentLinkedQueue
                             队列空时，循环CAS让读线程空转，直到有其他线程写入数据；队列满时，循环CAS让写线程空转，直到队列有空间。
                             【实际上，非阻塞队列是无界的，写线程只管往队尾插入数据，读线程只管从队列头读取数据】

    有界阻塞队列：
       ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
       LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。
    无界阻塞队列：
           PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
           DelayQueue：一个无界阻塞的延时队列，队列中的元素只有在设定的时间到了才能被使用。ScheduleThreadPool就是基于DelayQueue实现。
           SynchronousQueue：一个不存储元素的阻塞队列。【只能立即被消费】
           LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。

    非阻塞队列：
       ConcurrentLinkedQueue ： 一个基于链接节点的无界线程安全队列，
          它采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部；当我们获取一个元素时，它会返回队列头部的元素。

为什么没有ConcurrentArrayQueue?
回答这个问题之前需要知道Queue分为 阻塞/非阻塞，有界/无界 队列的概念
阻塞队列：当队列满时，阻塞写线程；当队列空时，阻塞读线程。
非阻塞队列：写线程只管往队列尾部插入数据，读队列只管从队列头部读取数据。

有界队列: 队列的长度有限制，当队列满了的话，线程阻塞或者直接丢弃
无界队列：线程只管往队尾加数据即可。

Concurrent***Queue是非阻塞队列，如果队列是有界的，那么就会导致CAS一直进行空转，所以非阻塞队列就要求队列是无界的，因此就没有ConcurrentArrayQueue

【注：非阻塞队列只有ConcurrentLinkedQueue】
