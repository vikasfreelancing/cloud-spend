//package com.cloudspend.commons.producers.impl;
//
//import com.cloudspend.commons.producers.MessageProducer;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.core.env.Environment;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import java.util.List;
//
//@Component("defaultKafkaProducer")
//@Slf4j
//public class DefaultKafkaProducer<K, V> implements MessageProducer<K, V> {
//
//    @Autowired
//    Environment env;
//
//    @Autowired
//    @Qualifier("defaultProducerTemplate")
//    private KafkaTemplate<K, V> kafkaTemplate;
//
//
//    public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, V message) {
//        log.info("sending message :{} to topic {} ", message, topicName);
//        ListenableFuture<SendResult<K, V>> future = kafkaTemplate.send(topicName, message);
//        return future;
//    }
//
//    @Override
//    public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, K key, V message) {
//        log.info("sending message :{} to topic {} to key {}", message, topicName, key);
//        ListenableFuture<SendResult<K, V>> future = kafkaTemplate.send(topicName, key, message);
//        return future;
//    }
//
//    @Override
//    public void sendMessage(String topicName, List<V> messageList) {
//        messageList.forEach(message -> this.sendMessage(topicName, message));
//    }
//
//}
