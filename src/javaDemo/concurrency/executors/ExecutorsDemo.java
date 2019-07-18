package javaDemo.concurrency.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这是一个很常用的类，作为ExecutorService工厂
 * 可以生产出来
 * 1.newFixedThreadPool : 固定线程数量的线程池【ThreadPoolExecutor实现了ExecutorService】
 * 2.newCachedThreadPool ：根据需要创建线程的线程池。【无线程数量限制】
 * 3.newScheduledThreadPool ：
 * 4.newSingleThreadExecutor ： 单线程的线程池
 */
public class ExecutorsDemo {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorService service1 = Executors.newCachedThreadPool();
        ExecutorService service2 = Executors.newScheduledThreadPool(10);
        ExecutorService service4 = Executors.newSingleThreadExecutor();



    }
}
