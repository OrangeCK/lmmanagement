package com.ck.lmmanagement.util;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 01378803
 * @date 2019/3/22 11:30
 * Description  :
 */
public class Test2Util {
    private static final int N = 10;
    static int count = 0;

    private Lock lock = new ReentrantLock(); // ReentrantLock是Lock的子类
    private static Object locObj = new Object();

    private void method(Thread thread) throws InterruptedException {
        // 如果2秒内获取不到锁对象，那就不再等待
        if (lock.tryLock(5,TimeUnit.SECONDS)) {
            try {
                System.out.println("线程名："+thread.getName() + "获得了锁");

                // 这里睡眠3秒
                Thread.sleep(3000);
            }catch(Exception e){
                e.printStackTrace();
            } finally {
                System.out.println("线程名："+thread.getName() + "释放了锁");
                lock.unlock(); // 释放锁对象
            }
        }
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThread");
            threadMethod(0);
        }
    }

    public static void main(String[] args) {
        Test2Util lockTest = new Test2Util();
        // 线程1
        Thread t1 = new Thread(() -> {
//            synchronized(locObj){
//                try {
//                    System.out.println("线程名："+Thread.currentThread().getName() + "获得了锁");
//                    Thread.sleep(2000);
//                    System.out.println("线程名："+Thread.currentThread().getName() + "结束了睡眠");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                lockTest.method(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        // 线程2
        Thread t2 = new Thread(() -> {
            try {
                lockTest.method(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }, "t2");

        t1.start();
        t2.start();

    }


//
    public static synchronized void threadMethod(int j){
        count ++;
        int i = count;
        j = j + i;
        System.out.println("打印的j=" + j);
    }
}
