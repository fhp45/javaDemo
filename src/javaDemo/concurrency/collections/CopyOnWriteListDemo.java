package javaDemo.concurrency.collections;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程环境下，写时效率低，读时效率高，且读多写少的情景下推荐使用。
 * CopyOnWriteArrayList/CopyOnWriteArraySet的基本思想是一旦对容器有修改，那么就“复制”一份新的集合，
 * 在新的集合上修改，然后将新集合复制给旧的引用。当然了这部分少不了要加锁。显然对于CopyOnWriteArrayList/CopyOnWriteArraySet来说最大的好处就是“读”操作不需要锁了。
 * 查看源码就发现，CopyOnWriteArrayList/CopyOnWriteArraySet在get的时候没有加锁，而set时候，使用了ReentrantLock
 *
 * 一个小疑问？
 * 多核情况下, 一个线程在set的同时，有另外一个线程是否可以读取？这时能读取到正确的数据吗？
 * 答：可以读取，因为get操作不需要锁。
 * 答：这时不一定能取到正确的数据，也就是说这个东西不支持实时性但是会确保最终一致性。只有在写线程setArray()【复制完成，将指针指向新的list】完成之后，才能获取到正确的数据
 *
 * 结果：写的时候是可以读的，并且并不一定读取到正确的值
 * 写线程我开始写啦
 * pool-1-thread-1我开始读取啦
 * 第一个元素的值是第一个元素
 * 我读取完成啦
 * pool-1-thread-1我开始读取啦
 * 第一个元素的值是第一个元素
 * 我读取完成啦
 * pool-1-thread-1我开始读取啦
 * 第一个元素的值是第一个元素
 * 我读取完成啦
 * 写线程我写完啦
 *
 */
public class CopyOnWriteListDemo {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        //测试一下咯
        list.add("第一个元素");

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        threadPool.execute(()->{
            while(true){
                System.out.println(Thread.currentThread().getName()+"我开始读取啦");
                System.out.println("第一个元素的值是"+list.get(0));
                System.out.println("我读取完成啦");
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"我开始写啦");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.set(0, UUID.randomUUID().toString());
            System.out.println(Thread.currentThread().getName()+"我写完啦");

        },"写线程").start();

    }
}
