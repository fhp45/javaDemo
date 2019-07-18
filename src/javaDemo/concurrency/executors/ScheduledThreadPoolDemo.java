package javaDemo.concurrency.executors;

import java.util.SortedMap;
import java.util.concurrent.*;

/**
 * 用来设置定时任务，和quartz是一样的，设置任务多久执行一次
 */
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newScheduledThreadPool(5);

        System.out.println("任务开始" + System.currentTimeMillis());

        ScheduledFuture<Integer> future = ((ScheduledExecutorService) threadPool).schedule(() -> {
            System.out.println("定时任务，1秒后输出" + System.currentTimeMillis());
            return 1;
        }, 1000, TimeUnit.MILLISECONDS);

        ((ScheduledExecutorService) threadPool).schedule(() -> {
            System.out.println("定时任务，2秒后输出" + System.currentTimeMillis());
        }, 2000, TimeUnit.MILLISECONDS);

        ((ScheduledExecutorService) threadPool).schedule(() -> {
            System.out.println("定时任务，3秒后输出" + System.currentTimeMillis());
        }, 2000, TimeUnit.MILLISECONDS);

        ((ScheduledExecutorService) threadPool).scheduleAtFixedRate(() -> {
            System.out.println("按照固定频率执行的任务，10秒执行一次，超时后不重新计时" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 4, 10, TimeUnit.SECONDS);

        ((ScheduledExecutorService) threadPool).scheduleWithFixedDelay(() -> {
            System.out.println("按照固定时间间隔执行的任务,10秒一次，超时后重新开始计时" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(14);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 6, 10, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(100);

        threadPool.shutdown();

    }
}
