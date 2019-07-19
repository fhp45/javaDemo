关于锁的一些demo
Condition只能通过Lock.newCondition来创建，而Condition和Lock配合使用可以用来阻塞/唤醒特定的一类线程，能够很好的用于生产者-消费者模型中
Lock:
    ReentrantLock : 重入锁是一种独占锁，ConcurrentHashMap就是基于ReentrantLock实现的. Segment继承了ReentrantLock, 而每个Segment包含了一个HashMap.
    ReentrantReadWriteLock: 重入读写锁并不是独占锁，支持一个线程写的时候--多个线程读。不能保证读的实时性，但是能够保证最终一致性。