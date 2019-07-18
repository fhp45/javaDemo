package javaDemo.concurrency.executors;

import java.util.concurrent.*;

/**
 *
 * ExecutorService是一个接口，如它的名字一样--执行器服务，它是为了丰富Executor[执行器]的使用而创建出来的一个接口。
 * ExecutorService提供方法来终结任务，管理那些可以产生Future的方法。
 * ExecutorService一旦关闭，将导致它拒绝新的任务。
 * 提供了两种不同的方法来关闭ExecutorService
 * shutdown()方法将在已经提交任务执行完成之后关闭ExecutorService.
 * shutdownNow()则将尝试关闭正在执行的任务，然后关闭ExecutorService
 *
 * 方法submit()延伸的基方法Executor.execute(Runnable)，创建并返回代表任务待处理结果的Future。
 *
 * 方法invokeAny和invokeAll执行invokeAll执行最常用的形式，执行任务集合，然后等待至少一个或全部完成。
 *
 * Executors类为此包中提供的执行程序服务提供了工厂方法。
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) {
        ExecutorService service =  Executors.newFixedThreadPool(3);

        for (int i = 0; i < 10; i++) {
            service.execute(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }

        //重要的事情说三遍，记得关闭ExecutorService
        service.shutdown();

    }
}
