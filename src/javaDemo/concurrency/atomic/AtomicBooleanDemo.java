package javaDemo.concurrency.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicBoolean中最经常使用的一个方法是compareAndSet，通过这个原子操作可以避免boolean常规使用中先判断后赋值的非原子性的错误。
 * 例子：判断一个boolean值是否为true,如果是的话修改为false，如果是false则修改为true。
 * 这个例子还可以用来让两个线程循环输出0/1
 */
public class AtomicBooleanDemo {
    private boolean isTrue = true;

    private AtomicBoolean atomicTrue = new AtomicBoolean(true);

    //使用非原子boolean时，会由于判断和赋值的非原子性，出现并发错误
    public void alwaysTrue() {
        //下面的代码看起来应该是永远输出true的，运行一下你就知道
        if (isTrue) {
            System.out.println(isTrue == true);
            isTrue = false;
        } else {
            System.out.println(isTrue == false);
            isTrue = true;
        }
    }

    //使用AtomicBoolean时，避免了在判断和修改之间时发生并发错误。但是下面的代码也并总是输出true，因为程序在System.out.println和compareAndSet的两个操作不是原子的
    public void alwaysTrueAtomic(){
        if(atomicTrue.compareAndSet(true,false)){
            System.out.println(atomicTrue.get() == false);
        }else{
            atomicTrue.compareAndSet(false,true);
            System.out.println(atomicTrue.get() == true);
        }
    }

    public static void main(String[] args) {
        AtomicBooleanDemo demo = new AtomicBooleanDemo();
        //启动一百个线程，我们会发现输出并不全是true
        for (int i = 0; i < 100; i++) {
                new Thread(demo::alwaysTrue).start();
        }
    }


}
