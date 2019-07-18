package javaDemo.concurrency.executors;

import java.util.concurrent.Executor;

/**
 * Executor和它的名字一样 执行器---执行你给定的任务。
 * Executor是一个接口，用来执行提交的对象Runnable任务，
 * 这个接口允许在将来的某个时间执行给定的命令，包括线程使用，调度等的Executor 。通常使用Executor而不是显式创建线程。
 */
public class ExecutorDemo implements Executor {

    public static void main(String[] args) {
        ExecutorDemo demo = new ExecutorDemo();

        demo.execute(()->{
            System.out.println("任务");
        });
    }

    @Override
    public void execute(Runnable command) {
        System.out.println("任务开始了");
        command.run();
        System.out.println("任务结束了");
    }
}
