package com.ck.lmmanagement.JavaDemo;

/**
 * @author ck
 * @date 2019/4/15 18:06
 * Description  :
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {
    @Bean(initMethod = "initUser", destroyMethod = "destoryUser")
    public User1 getUser1(){
        return new User1();
    }
    @Bean
    public MyBeanPostProcessor getMyBeanPostProcessor(){
        return new MyBeanPostProcessor();
    }
}
