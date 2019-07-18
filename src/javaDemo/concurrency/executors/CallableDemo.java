package javaDemo.concurrency.executors;

import java.util.concurrent.Callable;

/**
 * Callable和Runnable接口的区别：
 * Callable有返回值
 *
 */
public class CallableDemo {

    public static void main(String[] args) throws Exception {
        Callable<String> callable = new MyCallable();

        System.out.println(callable.call());

    }

    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            return Thread.currentThread().getName();
        }
    }
}
