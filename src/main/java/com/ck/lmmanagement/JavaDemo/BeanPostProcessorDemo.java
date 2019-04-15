package com.ck.lmmanagement.JavaDemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ck
 * @date 2019/4/15 17:31
 * Description  :
 */
public class BeanPostProcessorDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(MainConfig.class);
        applicationContext2.close();
    }
}
