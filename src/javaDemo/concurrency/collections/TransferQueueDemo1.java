package javaDemo.concurrency.collections;

import java.util.ArrayList;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * 这个demo是为了测试TransferQueue的容量问题，目前看来容量只能是1个，只要有未被take的元素，那么transfer所在的线程就会被阻塞
 */
public class TransferQueueDemo1 {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>(3);
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>(list);

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
        queue.tryTransfer("2", 1000, TimeUnit.MILLISECONDS);
        queue.transfer("3");
        queue.transfer("4");

        System.out.println(queue.size());



    }
}
