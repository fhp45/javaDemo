关于执行器的使用，包括线程池
Executor：执行器接口，定义了一个execute方法，用来执行交给它的任务。
ExecutorService ： 执行器服务，继承了Executor接口，定义了一系列执行器常用的方法。包括execute，shutdown，submit(返回Future)
Callable : 和Runnable的唯一区别是有返回值
Executors ： 一个工具类，ExecutorService工厂
        可以用来生成固定数量的线程池fixedThreadPool, 按需产生线程的线程池cachedThreadPool,单线程的线程池singleThreadPool,按设定时间执行任务的线程池ScheduleThreadPool
Future : 用于接收任务执行后的结果，ExecutorService.submit()后，返回Future，提供方法来检查计算是否完成，等待其完成，并检索计算结果。在Future.get()时，线程会阻塞，直到计算完成。
CachePool：按需创建线程的线程池，队列是无界阻塞队列LinkedBlockingQueue
SingleThreadPool ： 单线程的线程池，可以用来让任务按顺序执行。
ScheduleThreadPool ： 定时执行任务的线程池，必须设置执行任务的时间 ScheduleThreadPool.schedule(Callable task,long delay，TimeUnit unit)
ForkJoinPool :ForkJoinPool思想和其他的接口类不一样，ForkJoinPool的优势在于，可以充分利用多cpu，多核cpu的优势，把一个任务拆分成多个“小任务”，
                把多个“小任务”放到多个处理器核心上并行执行；当多个“小任务”执行完成之后，再将这些执行结果合并起来；
WorkStealingPool ： 和其他线程池不一样的是，WorkStealingPool是一种无锁化线程池，在任务到达时就分配给给个线程，每个线程维护了自己的任务队列，
                    当队列中的任务执行完毕时，就去执行同一线程池下其他线程的任务。
                    【普通线程池的任务队列是多个线程公用的，新任务到来时，就把任务放入到公共的队列中；当一个线程执行完毕一个任务时，需要从公共队列中获取新的任务
                       ,而从公共队列中获取新任务时，就需要锁来避免多个线程拿到同一个任务了】
ParallelStreamApi：java8新支持的ParallelStream来进行并行计算。




