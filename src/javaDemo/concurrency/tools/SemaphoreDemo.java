package javaDemo.concurrency.tools;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * semaphore作用是控制线程的并发数量
 * 那么什么是信号量呢？我用一种比较通俗的方式来跟大家解释一下，就是在该类初始化的时候，给定一个数字A，每个线程调用acquire()方法后，
 * 首先判断A是否大于0，如果大于0，就将A减去1，然后执行对应的线程，如果不大于0，那么就会阻塞，直到其他线程调用了release()方法，将A加上1，该线程可能有执行的机会。
 *
 * 场景介绍：有一个停车场只有5个车位，现在有100辆车要去抢这个5个车位，每辆车最多停3s，理想情况下最多只有五辆车同时可以抢到车位，那么没有抢到车位的车只能等到，其他的车让出车位，才有机会去使用该车位
 *
 * semaphore可以用作连接池
 * semaphore可以使任何一种容器变成有界阻塞队列
 */
public class SemaphoreDemo {
    //信号量，表明停车场的做多可以停的车辆【车辆停在停车场不就是线程在使用CPU吗？】
    private static final Semaphore semaphore = new Semaphore(5);
    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 100; i++){
            threadPool.execute(()->{
                try {
                    semaphore.acquire();
                    count.getAndIncrement();
                    System.out.println(Thread.currentThread().getName() + "我停进来啦");
                    TimeUnit.MILLISECONDS.sleep(300);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "我已经停够3秒啦");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }


}
