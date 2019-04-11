package com.ck.lmmanagement.JavaDemo;

/**
 * @author 01378803
 * @date 2019/4/11 11:18
 * Description  :
 */
public class WaitAndNotifyDemo {

    private static Object lock = new Object();

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread A " + i);
                    if(i == 50){
                        try {
                            lock.notify();
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread A(2) " + i);
                    }
                }
            }
        }
    }

    static class ThreadC implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread C " + i);
                    if(i == 50){
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thread C(2) " + i);
                    }
                }
            }
        }
    }

    static class ThreadD implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("Thread D " + i);
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("Thread B " + i);
                }
                lock.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        new Thread(new ThreadA()).start();
//        new Thread(new ThreadC()).start();
//        Thread.sleep(10);
//        new Thread(new ThreadB()).start();
        Thread d = new Thread(new ThreadD());
        d.start();
        d.join();
        System.out.println("线程main现在执行完了:TERMIATED=" + Thread.currentThread().getState());
    }
}
