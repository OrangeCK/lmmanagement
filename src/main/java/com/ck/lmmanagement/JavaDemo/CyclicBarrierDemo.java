package com.ck.lmmanagement.JavaDemo;

import com.ck.lmmanagement.util.TestRunner;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 01378803
 * @date 2019/4/11 17:16
 * Description  :
 */
public class CyclicBarrierDemo {

    private CyclicBarrier cyclicBarrier;
    private String name;

    static class Runner implements Runnable{
        private CyclicBarrier cyclicBarrier;
        private String name;

        public Runner(CyclicBarrier cyclicBarrier, String name){
            super();
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000 * (new Random()).nextInt(8));
                System.out.println(name + " 准备OK.");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(name + " Go!!");
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Runner runner1 = new Runner(barrier, "zhangsan");
        Runner runner2 = new Runner(barrier, "lisi");
        Runner runner3 = new Runner(barrier, "wangwu");
        executor.submit(new Thread(runner1));
        executor.submit(new Thread(runner2));
        executor.submit(new Thread(runner3));

        executor.shutdown();
    }
}
