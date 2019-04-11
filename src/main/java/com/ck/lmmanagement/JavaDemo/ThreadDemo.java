package com.ck.lmmanagement.JavaDemo;

/**
 * @author 01378803
 * @date 2019/4/11 10:45
 * Description  :
 */
public class ThreadDemo {

    public static class Mythread extends Thread{
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
        Mythread mythread1 = new Mythread();
        Mythread mythread2 = new Mythread();
        Mythread mythread3 = new Mythread();
        mythread1.start();
        mythread2.start();
        mythread3.start();
    }
}
