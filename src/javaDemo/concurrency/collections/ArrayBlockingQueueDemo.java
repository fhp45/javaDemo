package javaDemo.concurrency.collections;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * ArrayBlockingQueue和LinkedBlockingQueue的区别在于，它是基于Array实现的，并且初始化时必须指定容量
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) {
        Queue<String> queue = new ArrayBlockingQueue(5);

        queue.offer("0");
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        queue.offer("4");
        System.out.println(queue.offer("5"));


        while (!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
}
