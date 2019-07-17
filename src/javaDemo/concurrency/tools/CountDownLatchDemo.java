package javaDemo.concurrency.tools;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch叫做门闩，当执行await方法时，表明门被关了，当前线程被阻塞。直到countDown到门闩为0时，线程才会被唤醒。
 * 在多线程下用在任务B依赖于任务A的结果情境下
 * 例子：求1-10000的和，用5个线程分别求值，然后使用主线程来计算和
 * 其他场景：
 * 场景1：一群学生在教室考试，学生们都完成了作答，老师才可以进行收卷操作。
 *
 * 场景2：110跨栏比赛中，所有运动员准备好起跑姿势，进入到预备状态，等待裁判一声枪响。裁判开了枪，所有运动员才可以开跑。
 */
public class CountDownLatchDemo {

    private static final CountDownLatch latch = new CountDownLatch(5);
    private static int a1=0;
    private static int a2=0;
    private static int a3=0;
    private static int a4=0;
    private static int a5=0;

    /**
     * 虽然例子不是很好，但是却可以描述出，任务B依赖于任务A结果的情景
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            for (int i = 0; i < 2000 ; i++) {
                a1+=i;
            }
            latch.countDown();
        }).start();

        new Thread(()->{
            for (int i = 2000; i < 4000 ; i++) {
                a2+=i;
            }
            latch.countDown();
        }).start();

        new Thread(()->{
            for (int i = 4000; i < 6000 ; i++) {
                a3+=i;
            }
            latch.countDown();
        }).start();

        new Thread(()->{
            for (int i = 6000; i < 8000 ; i++) {
                a4+=i;
            }
            latch.countDown();
        }).start();

        new Thread(()->{
            for (int i = 8000; i < 10000 ; i++) {
                a5+=i;
            }
            latch.countDown();
        }).start();

        latch.await();

        System.out.println(a1+a2+a3+a4+a5);
    }

}
