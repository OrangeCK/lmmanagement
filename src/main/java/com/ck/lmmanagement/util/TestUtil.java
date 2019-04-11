package com.ck.lmmanagement.util;

import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author 01378803
 * @date 2019/3/15 9:21
 * Description  :
 */
public class TestUtil {
//    @FunctionalInterface
//    public interface Runnable{ void run();}
//
//    public interface Callable<V>{ V call() throws Exception;}
//
//    public interface ActionListener{void actionPerformed(ActionEvent e);}
//
//    public interface Comparator<T>{
//        int compare(T o1, T o2);
//        @Override
//        boolean equals(Object obj);
//    }
//
//    public interface Function<T, R>{
//        R apply(T t);
//    }
//
//    public interface Consumer<T>{
//        void accept(T t);
//    }
//
//    public interface Predicate<T>{
//        boolean test(T t);
//    }

    public static class T1 extends Thread {
        @Override
        public void run(){
            super.run();
            System.out.println(String.format("当前执行的线程是：%s，优先级：%d", Thread.currentThread().getName(), Thread.currentThread().getPriority()));
        }
    }


    private static synchronized void testMethod(){
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    private static volatile int signal = 0;

//    public static class ThreadA implements Runnable {
//        @Override
//        public void run(){
//            synchronized (lock1){
//                for (int i = 0; i < 5; i++){
//                    try {
//                        System.out.println("Thread A :" + Thread.currentThread().getName() + " " + i);
//                        lock1.notify();
//                        lock1.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                lock1.notify();
//            }
//        }
//    }
//
//    public static class ThreadB implements Runnable {
//        @Override
//        public void run(){
//            synchronized (lock2){
//                for (int i = 0; i < 5; i++){
//                    try {
//                        System.out.println("Thread B :" + Thread.currentThread().getName() + " " + i);
//                        lock2.notify();
//                        lock2.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                lock2.notify();
//            }
//        }
//    }

//    public static class ThreadA implements Runnable {
//        @Override
//        public void run(){
//            synchronized (lock1){
//                for (int i = 0; i < 5; i++){
//                    System.out.println("Thread A :" + Thread.currentThread().getName() + " " + i);
//                }
//            }
//        }
//    }
//
//    public static class ThreadB implements Runnable {
//        @Override
//        public void run(){
//            synchronized (lock2){
//                for (int i = 0; i < 5; i++){
//                    System.out.println("Thread B :" + Thread.currentThread().getName() + " " + i);
//                }
//            }
//        }
//    }

//    public static class ThreadA implements Runnable {
//        @Override
//        public void run(){
//            while (signal < 5){
//                if(signal % 2 == 0){
//                    System.out.println("Thread A :" + Thread.currentThread().getName() + " " + signal);
//                    synchronized (this){
//                        signal++;
//                    }
//                }
//            }
//        }
//    }
//
//    public static class ThreadB implements Runnable {
//        @Override
//        public void run(){
//            while (signal < 5){
//                if(signal % 2 == 1){
//                    System.out.println("Thread B :" + Thread.currentThread().getName() + " " + signal);
//                    synchronized (this){
//                        signal++;
//                    }
//                }
//            }
//        }
//    }

    public static class ThreadA implements Runnable {
        @Override
        public void run(){
            try {
                System.out.println("我是子线程，我先睡一秒");
                Thread.sleep(1000);
                signal++;
                System.out.println("我是子线程，我睡醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int i = 0;
//        retry:
        while (true) {
            i++;
            System.out.println("i=" + i);
            int j = 0;
            retry:
            for (; ; ) {
                j++;
                System.out.println("j=" + j);
                if (j == 2) {
                    break retry;
                }
            }
        }

//        String name = "John";
//        Optional<String> opt = Optional.ofNullable(name);
//        Map<String, Object> obj = new HashMap<>();
//        obj.put("deng", "liang");
////        opt.ifPresent(u -> System.out.println("打印：" + name));
//        System.out.println("打印：" + Optional.ofNullable(null).orElse("chenkang"));
//        System.out.println("打印：" + Optional.ofNullable(null).orElseGet(() -> "limin"));
//        System.out.println("打印：" + Optional.ofNullable(obj).map(u -> u.get("deng")).orElse("ceshi"));
////        String print = Optional.ofNullable(obj).flatMap(u -> u.get("deng")).orElse("shi");
////        System.out.println("打印：" + Optional.ofNullable(obj).flatMap(u -> u.get("deng")).orElse("shi"));
//        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6);
//        long result = numList.stream().reduce(0L,(a,b) ->  a + b, (a,b)-> 0L );
//        System.out.println(result);
//        List<Integer> numList1 = Arrays.asList(1, 2, 3, 4, 5, 6);
//        long result1 = numList1.stream().reduce(0L, (a, b) -> {return a + b; }, (a, b) -> 0L);
//        System.out.println(result1);
//        List<Integer> numList2 = Arrays.asList(1, 2, 3, 4, 5, 6);
//        long result2 = numList2.stream().reduce(10,(a, b) -> {return a + b; });
//        System.out.println(result2);

//        Thread thread = new Thread(new ThreadA(), "chenkang");
//        thread.start();
//        thread.join();
//        System.out.println("如果不加join方法，我会先被打出来，加了就不一样了" + signal);
//        Thread.sleep(10);
//        new Thread(new ThreadB(), "limin").start();


//        Thread a = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testMethod();
//            }
//        });
//        Thread b = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                testMethod();
//            }
//        });
//        a.start();
//        a.join(1000L);
//        b.start();
//        System.out.println(a.getName() + ":" + a.getState());
//        System.out.println(b.getName() + ":" + b.getState());


//        ThreadGroup threadGroup1 = new ThreadGroup("group1"){
//            @Override
//            public void uncaughtException(Thread t, Throwable e){
//                System.out.println(t.getName() + ": " + e.getMessage());
//            }
//        };
//        Thread thread1 = new Thread(threadGroup1, new Runnable() {
//            @Override
//            public void run() {
//                throw new RuntimeException("测试异常");
//            }
//        });
//        Thread thread2 = new Thread(threadGroup1, new Runnable() {
//            @Override
//            public void run() {
//                throw new RuntimeException("测试异常2");
//            }
//        });
//        thread1.start();
//        thread2.start();


//        IntStream.range(1,10).forEach(i -> {
//            Thread thread = new Thread(new T1());
//            thread.setPriority(i);
//            thread.start();
//        });


//        Thread a = new Thread();
//        System.out.println("我是默认的线程优先级：" + a.getPriority());
//        Thread b = new Thread();
//        b.setPriority(10);
//        System.out.println("我是设置过的线程优先级：" + b.getPriority());


//        Thread testTread = new Thread(() -> {
//            System.out.println("testThread当前线程组的名字：" + Thread.currentThread().getThreadGroup().getName());
//            System.out.println("testThread当前线程的名字：" + Thread.currentThread().getName());
//        });
//        testTread.start();
//        System.out.println("执行main方法线程名字：" + Thread.currentThread().getName());


//        Callable<Runnable> c1 = () -> () -> {
//            System.out.println("Nested lambda");
//        };
//        try {
//            c1.call().run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Callable<Integer> c2 = true ? (() -> 42) : (() -> 24);
//        try {
//            System.out.println("正在打印" + c2.call());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        UnaryOperator<Integer> factorial = i -> {return i == 0 ? 1 : i * factorial.apply(i-1);};
//        System.out.println("递归调用" + factorial.apply(3));
//        List<String> list = new ArrayList<>();
//        list.add("2");
//        list.add("23");
//        list.add("15");
//        list.add("65");
//        list.add("2");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//        list.add("8");
//        list.add("9");
//        int r = list.stream()
//                .map(e -> new Integer(e))
//                .filter(e -> e != 65)
//                .distinct()
//                .reduce(0, (x,y) -> x+y);
//        System.out.println("result is :" + r);
//        List<String> list = new ArrayList<>();
//        list.add("2");
//        list.add("23");
//        list.add("15");
//        list.add("65");
//        list.add("2");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//        list.add("8");
//        list.add("9");
//        Map<Integer, Integer> r = list.stream()
//                .map(e -> new Integer(e))
//                .filter(e -> e != 65)
//                .collect(Collectors.groupingBy(e -> e, Collectors.summingInt(p -> 3)));
//        System.out.println("result is :" + r);
//        list.forEach(o -> {
//            System.out.println(o);
//        });
    }
}
