package com.cloudspend.commons.producers.impl;
import com.cloudspend.commons.producers.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Component(value = "highThroughtputKafkaProducer")
public class HighThroughputKafkaProducer<K,V> implements MessageProducer<K, V> {

	private final static Logger LOGGER = LoggerFactory.getLogger(HighThroughputKafkaProducer.class);
	
	@Qualifier("highThroughtputProducerTemplate")
	@Autowired
	private KafkaTemplate<K, V> kafkaTemplate;
	@Autowired
	Environment env;

	public ListenableFuture<SendResult<K, V>>  sendMessage(String topicName, V message) {
		LOGGER.info("sending message :{} to topic {} ",message, topicName);
		ListenableFuture<SendResult<K, V>>  future=  kafkaTemplate.send(topicName, message);
		return future;
	}
	@Override
	public ListenableFuture<SendResult<K, V>> sendMessage(String topicName, K key, V message) {
		LOGGER.info("sending message :{} to topic {} to key {}",message, topicName, key);
		ListenableFuture<SendResult<K, V>>  future=  kafkaTemplate.send(topicName, key, message);
		return future;
	}

	@Override
	public void sendMessage(String topicName, List<V> messageList){
		messageList.forEach(message->this.sendMessage(topicName,message));
	}

}
