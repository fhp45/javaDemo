package javaDemo.concurrency.collections;

import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * 这个demo是为了测试TransferQueue的容量问题，目前看来容量只能是1个，只要有未被take的元素，那么transfer所在的线程就会被阻塞
 * 不不不，其实容量可以是多个，但是如果队列的第一个元素是transfer进去的，未被消费，那么就不能再往里面Transfer了，但是还可以add和put
 *
 * 使用最多的方法应该是tryTransfer
 */
public class TransferQueueDemo1 {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>(3);
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>(list);


        queue.add("3");
        queue.put("4");
        queue.put("5");
        queue.put("6");
        queue.put("7");
        new Thread(()->{
            for (int i = 0; i < 2 ; i++) {
                try {
                    System.out.println(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        queue.tryTransfer("1",1000, TimeUnit.MILLISECONDS);
        queue.tryTransfer("2", 1000, TimeUnit.MILLISECONDS);//所以这两个在一秒内，如果不被消费的话，就从队列中移除了

        while(!queue.isEmpty()){
            System.out.println(queue.take());
        }

        System.out.println(queue.size());
    }
}
