package javaDemo.concurrency.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在多线程环境下，i++操作是非原子的，经历以下步骤。获取i,v = i+1,赋值操作 i = v
 * 而AtomicInteger提供了对于Integer的一些原子的操作
 */
public class AtomicIntegerDemo {
    private static int a =0;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        //多线程环境下，很显然会出先并发错误 10个都为a进行增加值，最后结果居然只有20325
        for(int i = 0 ; i < 10; i++){
            new Thread(()->{
                for(int j = 0; j < 10000; j++){
                    a++;
                }
            }).start();
        }

        //使用AtomicInteger就可以保证自增的原子性
        for(int i = 0 ; i < 10; i++){
            new Thread(()->{
                for(int j = 0; j < 10000; j++){
                    atomicInteger.getAndIncrement();
                }
            }).start();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("非原子类的int最终结果： "+a);//输出结果为20325

        System.out.println("AtomicInteger的最终结果： "+atomicInteger.get());//输出结果为100000



    }

}
