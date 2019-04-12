package com.ck.lmmanagement.JavaDemo;

import java.util.concurrent.*;

/**
 * @author 01378803
 * @date 2019/4/12 17:49
 * Description  :
 */
public class CallableAndFutureDemo {

    static class Caller implements Callable{
        @Override
        public Object call() throws Exception {
            Thread.sleep(2000L);
            return "chenkang";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Caller caller = new Caller();
        Future result = executor.submit(caller);
        System.out.println("打印结果：" + result.get());
    }
}
