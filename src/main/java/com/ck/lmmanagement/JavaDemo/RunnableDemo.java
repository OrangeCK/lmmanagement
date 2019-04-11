package com.ck.lmmanagement.JavaDemo;

/**
 * @author 01378803
 * @date 2019/4/11 10:46
 * Description  :
 */
public class RunnableDemo {

    public static class Mythread implements Runnable{
        private int ticket = 5;
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if(ticket > 0){
                    System.out.println(Thread.currentThread().getName() + "卖票：ticket=" + ticket--);
                }
            }
        }
    }

    public static void main(String[] args) {
        Mythread mythread = new Mythread();
        Thread mythread1 = new Thread(mythread, "t1");
        Thread mythread2 = new Thread(mythread, "t2");
        Thread mythread3 = new Thread(mythread, "t3");
        mythread1.start();
        mythread2.start();
        mythread3.start();
    }
}
