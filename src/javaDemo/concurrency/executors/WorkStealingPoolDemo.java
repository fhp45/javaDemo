package javaDemo.concurrency.executors;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 每个线程维护自己的任务队列，当其中一个线程任务队列中的任务执行完之后，他会去窃取同一线程池下其他线程的任务来执行。
 * 哇，这个线程池应该很高效率
 * 而且是WorkStealingPool的线程是守护线程，看不到输出
 *
 * WorkStealingPool是基于ForkJoinPool实现的
 *
 * https://www.cnblogs.com/ok-wolf/p/7761755.html
 * 这里很好的解释了原理
 */
public class WorkStealingPoolDemo {

    public static void main(String[] args) {
        //我的电脑的核有点多
        ExecutorService threadPool = Executors.newWorkStealingPool(3);

        threadPool.execute(new MyTask(1000));//执行这个任务的线程和第四个任务的线程是一样的
        threadPool.execute(new MyTask(2000));
        threadPool.execute(new MyTask(2000));
        threadPool.execute(new MyTask(2000));//这个任务一定有第一个线程执行

        threadPool.shutdown();
        try {
            //守护线程看不到输出，下面的代码会使main线程阻塞，然后能看到输出
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class MyTask implements Runnable{
        private long time;

        public MyTask(long time){
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+",  " + System.currentTimeMillis());
        }
    }
}
