package com.ck.lmmanagement.JavaDemo;

import java.util.concurrent.CountDownLatch;

/**
 * @author 01378803
 * @date 2019/4/11 15:11
 * Description  :
 */
public class CountDownLauchDemo {
    private static final int N = 10;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(N);
        CountDownLatch startSignal = new CountDownLatch(1);
        for(int i = 1; i <= N; i++){
            new Thread(new Worker(i, doneSignal, startSignal)).start();
        }
        System.out.println("begin---------------");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("end-----------------");
    }

    static class Worker implements Runnable{
        private final CountDownLatch doneSignal;
        private final CountDownLatch startSignal;
        private int beginIndex;

        Worker(int beginIndex, CountDownLatch doneSignal, CountDownLatch startSignal){
            this.beginIndex = beginIndex;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = 0; i <= beginIndex + 10; i++) {
                    System.out.println("打印数字:" + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }
    }
}
