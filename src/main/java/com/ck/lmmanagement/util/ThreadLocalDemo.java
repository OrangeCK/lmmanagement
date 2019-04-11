package com.ck.lmmanagement.util;

import java.math.BigDecimal;

/**
 * @author 01378803
 * @date 2019/3/19 15:45
 * Description  :
 */
public class ThreadLocalDemo {
    static class ThreadA implements Runnable{
        private ThreadLocal<String> threadLocal;
        public ThreadA(ThreadLocal<String> threadLocal){
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            threadLocal.set("A");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadA输出" + threadLocal.get());
        }
    }

    static class ThreadB implements Runnable{
        private ThreadLocal<String> threadLocal;
        public ThreadB(ThreadLocal<String> threadLocal){
            this.threadLocal = threadLocal;
        }

        @Override
        public void run() {
            threadLocal.set("B");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ThreadB输出" + threadLocal.get());
        }
    }

    public static void main(String[] args) {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        new Thread(new ThreadA(threadLocal)).start();
//        new Thread(new ThreadB(threadLocal)).start();
//        BigDecimal bigDecimal = new BigDecimal(0.00);
//        System.out.println("整数是：" + bigDecimal.setScale(0));
    }
}
