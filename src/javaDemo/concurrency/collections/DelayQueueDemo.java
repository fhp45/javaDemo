package javaDemo.concurrency.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue:是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的对象只能在其到期时才能从队列中取走。
 * 这种队列是有序的，即队头对象的延迟到期时间最长。注意：不能将null元素放置到这种队列中。
 *
 * 可以用作定时任务, 只能使用take方法来获取
 */
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<MyDelayed> queue = new DelayQueue();

        long nowTime = System.currentTimeMillis();

        queue.put(new MyDelayed(nowTime+1000));
        queue.put(new MyDelayed(nowTime+2000));
        queue.put(new MyDelayed(nowTime+3000));
        queue.put(new MyDelayed(nowTime+500));
        queue.put(new MyDelayed(nowTime+1500));
        queue.put(new MyDelayed(nowTime+2500));



        while(!queue.isEmpty()){
            //DelayedQueue这里不能使用poll,因为第一个元素要在500ms后才生效，所以此时poll的是null。只能使用take
            //System.out.println(queue.poll().toString());
            System.out.println(queue.take().toString());
        }
    }



    static class MyDelayed implements Delayed {

        //可以使用本对象的时间
        private long expireTime;

        public MyDelayed(long expireTime) {
            this.expireTime = expireTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {

            if (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS) > 0) {
                return 1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS) == 0) {
                return 0;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return expireTime+"";
        }
    }
}

