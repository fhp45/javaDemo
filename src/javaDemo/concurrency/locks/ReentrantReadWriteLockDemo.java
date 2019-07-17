package javaDemo.concurrency.locks;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁，内部包含了ReadLock和WriteLock
 */
public class ReentrantReadWriteLockDemo {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static Lock readLock = lock.readLock();
    private static Lock writeLock = lock.writeLock();


    public static void main(String[] args) {
        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
        for (int i = 0; i < 5 ; i++) {
            new Thread(demo::read).start();
        }

        for (int i = 0; i < 5 ; i++) {
            new Thread(demo::write).start();
        }

    }


    //读：在读的时候，会把当前线程睡眠20秒，但是还可以看到其他的线程在读，表明其他线程获得了锁
    public void read(){
        readLock.lock();
        System.out.println("我在读，会持续20秒，但是还会看见其他线程读哦:"+Thread.currentThread().getName()+new Date());
        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我读完了"+Thread.currentThread().getName());
        readLock.unlock();
    }

    //写：在写的时候，持续20秒，这是看不到任何输出，其他线程都不能获得锁。
    public void write(){
        writeLock.lock();
        System.out.println("我在写，会睡眠20秒。其他线程不能读，也不能写:"+Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我写完了"+Thread.currentThread().getName());
        writeLock.unlock();
    }
}
