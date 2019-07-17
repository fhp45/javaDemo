package javaDemo.concurrency.atomic;

import java.util.concurrent.TimeUnit;

/**
 * AtomicBoolean中最经常使用的一个方法是compareAndSet，通过这个原子操作可以避免boolean常规使用中先判断后赋值的非原子性的错误。
 * 例子：判断一个boolean值是否为true,如果是的话修改为false，如果是false则修改为true。
 * 这个例子还可以用来让两个线程循环输出0/1
 */
public class AtomicBooleanDemo {
    public boolean isTrue = true;

    //使用非原子boolean时，会由于判断和赋值的飞原子性，出现并发错误
    public void alwaysTrue() {
        //下面的代码看起来应该是永远输出true的，运行一下你就知道
        if (isTrue) {
            try {
                //让线程暂停20ms
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(isTrue == true);
            isTrue = false;
        } else {
            try {
                //让线程暂停20ms
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(isTrue == false);
            isTrue = true;
        }
    }

    public static void main(String[] args) {
        AtomicBooleanDemo demo = new AtomicBooleanDemo();

        for (int i = 0; i < 1000; i++) {
                new Thread(demo::alwaysTrue).start();
        }
    }


}
