package com.ck.lmmanagement.JavaDemo;

import java.util.concurrent.*;

/**
 * @author 01378803
 * @date 2019/4/12 9:21
 * Description  : https://blog.csdn.net/qq_25806863/article/details/71126867 （这个地址有线程池运用的讲解）
 */
public class ThreadPoolExecutorDemo {

    static class MyThread implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
                System.out.println(Thread.currentThread().getName() + "run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        ThreadPoolExecutor executor = null;
        try {
            executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS,  new LinkedBlockingDeque<>());
//        ExecutorService executor = new ThreadPoolExecutor(1, 20, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
            executor.execute(myThread);
            executor.execute(myThread);
            executor.execute(myThread);
            System.out.println("---先开三个---");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
            executor.execute(myThread);
            executor.execute(myThread);
            executor.execute(myThread);
            System.out.println("---再开三个---");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
            Thread.sleep(8000);
            System.out.println("----8秒之后----");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
