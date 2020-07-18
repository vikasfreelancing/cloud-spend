//package com.cloudspend.commons.kafka;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaProducerConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    protected String bootstrapAddress;
//
//    @Bean
//    public ProducerFactory<String, String> defaultProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        //create a safe idempotent producer
//        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
//        configProps.put(ProducerConfig.RETRIES_CONFIG, 5);
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
//        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);//keep this 1 if your producer is using key based setup which means when order is important
//        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean("defaultProducerTemplate")
//    public KafkaTemplate<String, String> defaultKafkaTemplate() {
//        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(defaultProducerFactory());
//        return kafkaTemplate;
//    }
//
//    @Bean
//    public ProducerFactory<String, String> highThroughputProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        //create a safe idempotent producer
//        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
//        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 2000);
//        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);//keep this 1 if your producer is using key based setup which means when order is important
//        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//
//        //high throughput producer(at the expense of a bit of latency and CPU usage)
//        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
//        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 20);
//        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));    //32kb batch size
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean("highThroughtputProducerTemplate")
//    public KafkaTemplate<String, String> highThroughputKafkaTemplate() {
//        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<String, String>(highThroughputProducerFactory());
//        return kafkaTemplate;
//    }
//
//}
