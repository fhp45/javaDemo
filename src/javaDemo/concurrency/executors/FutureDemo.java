package javaDemo.concurrency.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Future是一个接口，在ExecutorService中submit之后，会返回一个Future,提供方法来检查计算是否完成，等待其完成，并检索计算结果
 * Executor -> ExecutorService 执行submit -> Future
 * 哈哈，为什么需要Future呢？
 * ExecutorService的execute方法并不会有返回结果，当我们需要返回结果时，用什么来接受这个结果呢？--- Future。【当然让多个线程操作共享变量也能得到结果】
 *
 * 所以Future是Executor架构中的一员。
 *
 * 例子：使用线程池提交5个任务，然后submit之后等待结果。
 *
 * 当然还有一个FutureTask类，它实现了Runnable和Future接口。
 *
 */
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        AtomicInteger integer = new AtomicInteger(0);

        Future<Integer> f = threadPool.submit(()->{
            TimeUnit.MILLISECONDS.sleep(200);
           return integer.addAndGet(5);
        });

        threadPool.shutdown();

        //任务是否完成了呢？
        System.out.println(f.isDone());

        //获取结果
        System.out.println(f.get());


        FutureTask<String> fTask = new FutureTask<String>(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return Thread.currentThread().getName();
        });



        //FutureTask是否完成了呢？
        System.out.println(fTask.isDone());

        //由于FutureTask实现了Runnable接口，所以也新启动一个线程执行
        new Thread(fTask).start();
       //fTask.run();


        //获取结果
        System.out.println(fTask.get());
    }
}
