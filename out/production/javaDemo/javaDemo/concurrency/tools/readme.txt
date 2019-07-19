关于工具类的一些demo
CountDownLatch: 门闩的用法在与，任务B的执行依赖于任务A的结果。这时我们需要让执行任务B的线程阻塞，并且让任务A执行完成后通知任务B.
                CountDownLatch的await()方法让线程B阻塞，然后任务A中的线程调用countDown()方法，直到任务A完成。
CyclicBarrier: 循环栅栏，CyclicBarrier是一种可以循环使用的门闩。通过计算等待线程数量是否等于初始化时设置的等待数量来决定是否执行CyclicBarrier中的任务
               考虑一个例子：长途汽车可以乘坐20人，当乘客上满之后发车，并且另外一辆长途汽车等待乘客上车，如此往复循环。
               CyclicBarrier(int waitNumbers, Runnable task)这个构造器提供了一个表示线程阻塞数量和当阻塞到该数量后线程执行的任务。
               CyclicBarrier.await()让任务线程阻塞，当阻塞线程数量 = waitNumbers时，执行task，执行完毕task后CyclicBarrier重置。
Semaphore：信号量，用来设置最大并发量，数据库连接池就可以使用Semaphore来实现。
           固定线程数量的线程池应该也可以使用Semaphore来实现，Semaphore实现了AQS,内部维护了一个队列。
           当线程调用Semaphore.acquire()时，判断是否还有剩余并发量，有的话线程就继续往下执行，否则抛出异常。

