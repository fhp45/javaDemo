package javaDemo.concurrency.locks;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock+Condition可以组合使用，用来实现生产者-消费者模式
 * <p>
 * 例子：4个线程生产，3个线程消费。产品最多可以同时存在20个。
 */
public class LockAndCondition {
    private ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(20);
    //可以选择公平还是非公平锁
    private Lock lock = new ReentrantLock();

    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public static void main(String[] args) {
        LockAndCondition lac = new LockAndCondition();

        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                while(true){
                    lac.produce();
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(()->{while(true) {
                lac.consume();
            }
            }).start();
        }
    }

    //await类似的方法，一般都是使用while判断
    private void produce() {
            try {
                lock.lock();
                while (queue.size() == 20) {
                    System.out.println("队列已满");
                    try {
                        producer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(new Date().toString());
                consumer.signalAll();
            } finally {
                lock.unlock();
            }
    }

    private void consume() {
            try {
                lock.lock();
                while (queue.isEmpty()) {
                    System.out.println("队列已空");
                    try {
                        consumer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(queue.poll());
                producer.signalAll();
            } finally {
                lock.unlock();
            }
    }


}
