package com.ck.lmmanagement.JavaDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author
 * @date 2019/4/11 10:14
 * Description  :
 */
public class ReentrantLockDemo {

    private Lock lock = new ReentrantLock();

    private void method(Thread thread){
        if(lock.tryLock()){
            try {
                System.out.println("线程：" + thread.getName() + "获得了锁");
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void method2(Thread thread){
        try {
            lock.lock();
            System.out.println("线程：" + thread.getName() + "获得了锁");
            Thread.sleep(3000L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static class MyThread implements Runnable{
        ReentrantLockDemo demo = new ReentrantLockDemo();
        @Override
        public void run() {
            demo.method(Thread.currentThread());
        }
    }

    static class MyThread2 implements Runnable{
        ReentrantLockDemo demo = new ReentrantLockDemo();
        @Override
        public void run() {
            demo.method2(Thread.currentThread());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        MyThread2 myThread2 = new MyThread2();
        new Thread(myThread2, "A").start();
//        Thread.sleep(3100L);
        new Thread(myThread2, "B").start();
    }
}
