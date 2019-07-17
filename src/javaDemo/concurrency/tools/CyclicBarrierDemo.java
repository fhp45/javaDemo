package javaDemo.concurrency.tools;

import java.util.concurrent.*;

/**
 * CyclicBarrier(循环屏障)可以理解为可以重复使用的CountDownLatch
 * 更为高级的是，barrier有一个Runnable的参数，成为屏障线程。屏障线程的运行时机：等待的线程数量=parties之后，CyclicBarrier打开屏障之前。
 * 例子：
 * 长途汽车发车：
 * 长途汽车站提供长途客运服务。
 * 当等待坐车的乘客到达20人时，汽车站就会发出一辆长途汽车，让这20个乘客上车走人。
 * 等到下次等待的乘客又到达20人是，汽车站就会又发出一辆长途汽车。
 */
public class CyclicBarrierDemo {

    private static final CyclicBarrier barrier = new CyclicBarrier(20, () -> {
        System.out.println("乘客已经上满，即将发车");
    });

    public static void main(String[] args) {
        while (true) {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.execute(() -> {
                try {
                    System.out.println("当前乘客数量：" + barrier.getNumberWaiting());
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
