package com.ck.lmmanagement.util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import java.util.List;

/**
 * @author ck
 * @date 2019/3/27 11:19
 * Description  :
 */
//@Component
public class BTopicConsumer {
//    @JmsListener(destination = "stringTopic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveStringTopic(String msg) {
//        System.out.println("BTopicConsumer接收到消息...." + msg);
//    }
//
//
//    @JmsListener(destination = "stringListTopic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveStringListTopic(List<String> list) {
//        System.out.println("BTopicConsumer接收到集合主题消息...." + list);
//    }
//
//
//    @JmsListener(destination = "objTopic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveObjTopic(ObjectMessage objectMessage) throws Exception {
//        System.out.println("BTopicConsumer接收到对象主题消息...." + objectMessage.getObject());
//    }
//
//
//    @JmsListener(destination = "objListTopic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveObjListTopic(ObjectMessage objectMessage) throws Exception {
//        System.out.println("BTopicConsumer接收到的对象集合主题消息..." + objectMessage.getObject());
//    }

}
