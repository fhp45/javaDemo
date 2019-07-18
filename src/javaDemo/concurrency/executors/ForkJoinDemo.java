package javaDemo.concurrency.executors;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 假若我有一个很大的任务，很难以完成。我们就可以把任务fork成许多子任务，如果子任务还是太大，那么就继续切分成更小的子任务
 * 然后子任务的结果再进行累加，最终得到结果。相当于map-reduce
 * <p>
 * 例子：计算100万个随机数字求和
 */
public class ForkJoinDemo {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;//每个任务最多计算50000个数字
    static Random random = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(500);
        }
        System.out.println(Arrays.stream(nums).sum()+"静态代码块");
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinPool pool1 = new ForkJoinPool();
        ForkJoinTask task = new AddTask(0, 1000000);
        pool.execute(task);

        System.out.println("由于RecursiveAction没有返回值，下面使用RecursiveTask来获取返回值");

        ForkJoinTask<Long> task1 = new AddTask1(0,1000000);

        pool.execute(task1);
        System.out.println(task1.join());

        //守护线程，需要下面阻塞才能显示输出
        System.in.read();
    }

    //RecursiveAction没有返回值，所以看不到结果
    static class AddTask extends RecursiveAction {
        int start, end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println("from : " + start + ",to : " + end + "; sum = " + sum);
            } else {
                int middle = (start + end) / 2;
                AddTask subTask1 = new AddTask(start, middle);
                AddTask subTask2 = new AddTask(middle, end);

                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    static class AddTask1 extends RecursiveTask<Long>{
        int start, end;

        public AddTask1(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            if (end - start <= MAX_NUM) {
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }
                int middle = (start + end) / 2;
                AddTask1 subTask1 = new AddTask1(start, middle);
                AddTask1 subTask2 = new AddTask1(middle, end);
                subTask1.fork();
                subTask2.fork();


            return subTask1.join()+subTask2.join();
        }
    }

}
