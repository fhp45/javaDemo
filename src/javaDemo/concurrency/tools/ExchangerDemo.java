package javaDemo.concurrency.tools;

import java.util.concurrent.Exchanger;

/**
 * Exchanger它可以在两个线程之间传输数据，Exchanger中的public V exchange(V x)方法被调用后等待另一个线程到达交换点（如果当前线程没有被中断），然后将已知的对象传给它，返回接收的对象。
 *
 * 例子：线程AB交换数据，线程A输出线程B的名字，线程B输出线程A的名字
 */
public class ExchangerDemo {


    private static final Exchanger<String> exchanger = new Exchanger<>();


    public static void main(String[] args) {
        new Thread(()->{
            String data = Thread.currentThread().getName();
            try {
                System.out.println(data+"得到了通过Exchanger得到了另一个线程的名字" + exchanger.exchange(data));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程A").start();

        new Thread(()->{
            String data = Thread.currentThread().getName();
            try {
                System.out.println(data+"得到了通过Exchanger得到了另一个线程的名字" + exchanger.exchange(data));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程B").start();


        new ExchangerThread("线程A",exchanger).start();
        new ExchangerThread("线程B",exchanger).start();

    }

    //另一种写法

    private static class ExchangerThread extends Thread{
        private Exchanger<String> exchanger;
        private String name;

        public ExchangerThread(String name,Exchanger exchanger){
            this.name = name;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"交换前，我的名字是：" + name);
                name = exchanger.exchange(name);
                System.out.println(Thread.currentThread().getName()+"交换后，我的名字是：" + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
