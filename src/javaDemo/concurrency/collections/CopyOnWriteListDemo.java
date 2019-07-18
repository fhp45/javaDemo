package javaDemo.concurrency.collections;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程环境下，写时效率低，读时效率高，且读多写少的情景下推荐使用。
 * CopyOnWriteArrayList/CopyOnWriteArraySet的基本思想是一旦对容器有修改，那么就“复制”一份新的集合，
 * 在新的集合上修改，然后将新集合复制给旧的引用。当然了这部分少不了要加锁。显然对于CopyOnWriteArrayList/CopyOnWriteArraySet来说最大的好处就是“读”操作不需要锁了。
 * 查看源码就发现，CopyOnWriteArrayList/CopyOnWriteArraySet在get的时候没有加锁，而set时候，使用了ReentrantLock
 *
 * 一个小疑问？
 * 多核情况下, 一个线程在set的同时，有另外一个线程是否可以读取？这时能读取到正确的数据吗？
 * 答：可以读取，因为get操作不需要锁。
 * 答：这时不一定能取到正确的数据，也就是说这个东西不支持实时性但是会确保最终一致性。只有在写线程setArray()【复制完成，将指针指向新的list】完成之后，才能获取到正确的数据
 *
 *
 */
public class CopyOnWriteListDemo {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
    }
}
