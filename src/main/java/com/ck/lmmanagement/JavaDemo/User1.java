package com.ck.lmmanagement.JavaDemo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author ck
 * @date 2019/4/15 18:06
 * Description  :
 */
public class User1 implements InitializingBean, DisposableBean {
    public User1(){
        System.out.println("实例化User1的构造方法......");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("调用实现DisposableBean的destroy方法....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用实现InitializingBean的afterPropertiesSet方法......");
    }

    public void initUser(){
        System.out.println("执行initMethod方法......");
    }

    public void destoryUser(){
        System.out.println("执行destoryMethod方法......");
    }
}
