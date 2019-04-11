package com.ck.lmmanagement.util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import java.util.List;

/**
 * @author 01378803
 * @date 2019/3/27 13:40
 * Description  :
 */
//@Component
public class QueueConsumer {
//    @JmsListener(destination = "stringQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveStringQueue(String msg) {
//        System.out.println("接收到消息...." + msg);
//    }
//
//
//    @JmsListener(destination = "stringListQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveStringListQueue(List<String> list) {
//        System.out.println("接收到集合队列消息...." + list);
//    }
//
//
//    @JmsListener(destination = "objQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveObjQueue(ObjectMessage objectMessage) throws Exception {
//        System.out.println("接收到对象队列消息...." + objectMessage.getObject());
//    }
//
//
//    @JmsListener(destination = "objListQueue", containerFactory = "jmsListenerContainerQueue")
//    public void receiveObjListQueue(ObjectMessage objectMessage) throws Exception {
//        System.out.println("接收到的对象队列消息..." + objectMessage.getObject());
//    }

}
