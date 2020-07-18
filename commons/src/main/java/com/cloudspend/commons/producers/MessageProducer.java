//package com.cloudspend.commons.producers;
//
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import java.util.List;
//
//@Component
//public interface MessageProducer<K,V> {
//
//	public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, V message);
//
//	public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, K key, V message);
//
//    void sendMessage(String topicName, List<V> messageList);
//}
