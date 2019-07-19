关于锁的一些demo
Condition只能通过Lock.newCondition来创建，而Condition和Lock配合使用可以用来阻塞/唤醒特定的一类线程，能够很好的用于生产者-消费者模型中
Lock:
    ReentrantLock : 重入锁是一种独占锁，ConcurrentHashMap就是基于ReentrantLock实现的. Segment继承了ReentrantLock, 而每个Segment包含了一个HashMap.
    ReentrantReadWriteLock: 重入读写锁并不是独占锁，内部有两个锁readLock和writeLock,
                            当writeLock被占用时，其他线程不能获取readLock/writeLock来进行读/写；当readLock被占用时，其他线程还可以获取ReadLock从而进行读操作。
    【注：要和CopyOnWrite机制进行区分，CopyOnWrite内部只有一个ReentrantLock, 所以读时不加锁，写时加锁。因此写的时候，也可以进行读操作，
          但是可能出现脏读，也就是说不保证实时性，但是保证最终一致性。(只有写操作的复制(setArray)完成后，才能读取到正确的值)】