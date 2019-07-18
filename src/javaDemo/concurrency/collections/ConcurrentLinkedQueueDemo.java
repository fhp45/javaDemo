package javaDemo.concurrency.collections;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Queue是并发中使用最多的集合
 * ArrayQueue是有界的，LinkedQueue是无界的
 *
 * 对于queue的操作：
 1. add和offer的区别在于，add方法在队列满的情况下将选择抛异常的方法来表示队列已经满了，而offer方法通过返回false表示队列已经满了；在有限队列的情况，使用offer方法优于add方法；
 2. remove方法和poll方法都是删除队列的头元素，remove方法在队列为空的情况下将抛异常，而poll方法将返回null；
 3.
 *
 */
public class ConcurrentLinkedQueueDemo {

    //常见的操作
    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedDeque<>();

        queue.add("第一个元素");
        queue.add("第二个元素");

        //queue的大小
        System.out.println(queue.size());

        //获取queue的第一个元素，但是不移除。如果队列为空，则返回null
        System.out.println(queue.peek());

        //获取queue的第一个元素，并且删除。如果队列为空，则返回null
        System.out.println(queue.poll());

        //和poll一样，但是如果队列为空，则抛出异常
        System.out.println(((ConcurrentLinkedDeque<String>) queue).pop());
    }
}
