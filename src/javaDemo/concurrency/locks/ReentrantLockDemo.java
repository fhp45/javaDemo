package javaDemo.concurrency.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是重入锁，执行下面的代码会发现，当调用write时，持有锁。这时还可以进入到read里面
 */
public class ReentrantLockDemo {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        for(int i = 1; i < 3; i++){
            new Thread(()->{
                demo.write();
            }).start();
        }
    }

    private void read() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "我正在读");
            TimeUnit.MILLISECONDS.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    private void write() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "我在写");
        read();//这里表明是可以重入的
        lock.unlock();
    }
}
