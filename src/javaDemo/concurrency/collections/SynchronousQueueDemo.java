package javaDemo.concurrency.collections;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 这是一种特殊的TransferQueue,它的容量为0 ，而且只能put元素进去，
 * 并且一个线程put的时候，必须有另一线程直接消费put的值，否则put操作所在的线程会被阻塞，直到put进去的元素被消费为止。
 *
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(15000);
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //不能add，因为容器是容量是0
        //queue.add("hello add ");


        queue.put("hello put");

        //线程在这里阻塞了
        queue.put("hello put1，在15秒后，put的元素被消费后，才会出现这一行");


    }

}
