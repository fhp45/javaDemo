package javaDemo.concurrency.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConcurrentHashMap的使用和普通Map的使用是一样的，但是在多线程环境下是安全的。
 * ConcurrentHashMap使用的锁是ReentrantLock,所以不支持多个线程同时读。
 * 还有一个并发的Map,ConcurrentSkipListMap 在插入时会进行排序
 * 例子：12个线程同时往ConcurrentHashMap里面写
 * 结果：看不出来什么快的地方。。。
 */
public class ConcurrentHashMapDemo {
    private final static Map<Integer, String> map =
    //new HashMap<>();
    new ConcurrentHashMap();
    //new ConcurrentSkipListMap<>();
    private static CountDownLatch latch = new CountDownLatch(12);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        ExecutorService threadPool = Executors.newFixedThreadPool(12);

        long start = new Date().getTime();
        for (int i = 0; i < 12; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10000; j++) {
                        map.put(UUID.randomUUID().hashCode(), "value");
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("创建个" + map.size() + "元素用时" + (new Date().getTime() - start));
    }


}
