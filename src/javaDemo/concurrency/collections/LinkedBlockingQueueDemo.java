package javaDemo.concurrency.collections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueue和ConcurrentQueue支持并发的区别在于：
 * ConcurrentQueue是基于更底层的CAS实现的。
 * LinkedBlockingDeque无非是用一个独占锁来保持线程安全，然后用Condition来做阻塞操作
 */
public class LinkedBlockingQueueDemo {


    public static void main(String[] args) {
        Queue concurrentQueue = new ConcurrentLinkedQueue();
        Queue queue = new LinkedBlockingQueue(10);//初始化时给定参数，就可以成为有界队列，否则容量是int的最大值
        queue.offer("0");
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        queue.offer("4");
        System.out.println(queue.offer("5"));
        queue.offer("6");
        queue.offer("7");
        queue.offer("8");
        queue.offer("9");
        System.out.println(queue.offer("10"));

        while(!queue.isEmpty()){
            System.out.println(queue.poll());
        }


    }
}
