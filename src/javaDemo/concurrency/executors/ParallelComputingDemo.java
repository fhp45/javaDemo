package javaDemo.concurrency.executors;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 并行计算的例子
 * 计算出 1000000以内的质数个数
 *
 * 单线程用时：37501
 * 78498
 * 12线程用时：9736
 * 78498

 */
public class ParallelComputingDemo {

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> result = getPrime(2, 1000000);
        System.out.println("单线程用时：" + (System.currentTimeMillis() - start));
        System.out.println(result.size());

        long start1 = System.currentTimeMillis();

        ExecutorService threadPool = Executors.newFixedThreadPool(12);
        List<FutureTask<List<Integer>>> taskList =getTaskList();

        for (int i = 0; i < taskList.size(); i++) {
            threadPool.submit(taskList.get(i));
        }
        int count = taskList.get(0).get().size() + taskList.get(1).get().size() +
                taskList.get(2).get().size() +
                taskList.get(3).get().size() +
                taskList.get(4).get().size() +
                taskList.get(5).get().size() +
                taskList.get(6).get().size() +
                taskList.get(7).get().size() +
                taskList.get(8).get().size() +
                taskList.get(9).get().size() +
                taskList.get(10).get().size() +
                taskList.get(11).get().size();
        System.out.println("12线程用时：" + (System.currentTimeMillis() - start1));
        System.out.println(count);
    }

    static List<FutureTask<List<Integer>>> getTaskList(){
        FutureTask<List<Integer>> task0 = new FutureTask<List<Integer>>(() -> {
            return getPrime(2, 400000);
        });

        FutureTask<List<Integer>> task1 = new FutureTask<List<Integer>>(() -> {
            return getPrime(400001, 600000);
        });

        FutureTask<List<Integer>> task2 = new FutureTask<List<Integer>>(() -> {
            return getPrime(600001, 670000);
        });

        FutureTask<List<Integer>> task3 = new FutureTask<List<Integer>>(() -> {
            return getPrime(670001, 720000);
        });

        FutureTask<List<Integer>> task4 = new FutureTask<List<Integer>>(() -> {
            return getPrime(720001, 760000);
        });

        FutureTask<List<Integer>> task5 = new FutureTask<List<Integer>>(() -> {
            return getPrime(760001, 800000);
        });

        FutureTask<List<Integer>> task6 = new FutureTask<List<Integer>>(() -> {
            return getPrime(800001, 835000);
        });

        FutureTask<List<Integer>> task7 = new FutureTask<List<Integer>>(() -> {
            return getPrime(835001, 870000);
        });

        FutureTask<List<Integer>> task8 = new FutureTask<List<Integer>>(() -> {
            return getPrime(870001, 910000);
        });

        FutureTask<List<Integer>> task9 = new FutureTask<List<Integer>>(() -> {
            return getPrime(910001, 950000);
        });

        FutureTask<List<Integer>> task10 = new FutureTask<List<Integer>>(() -> {
            return getPrime(950001, 980000);
        });

        FutureTask<List<Integer>> task11 = new FutureTask<List<Integer>>(() -> {
            return getPrime(980001, 1000000);
        });

        return Arrays.asList(task0,task1,task2,task3,task4,task5,task6,task7,task8,task9,task10,task11);
    }


    static List<Integer> getPrime(int start, int end) {
        List<Integer> result = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                result.add(i);
            }
        }
        return result;
    }

    static boolean isPrime(int number) {
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
