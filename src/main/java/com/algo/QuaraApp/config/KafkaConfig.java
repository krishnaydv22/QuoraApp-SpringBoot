package com.algo.QuaraApp.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id:view-count-consumer}")
    private String groupId;

    @Value("${spring.kafka.consumer.group-id-follow:update-follow-consumer}")
    private String followGroupId;

    @Value("${spring.kafka.consumer.group-id:generate-feed-consumer}")
    private String generateFeedId;
    public static final String TOPIC_NAME = "view-count-topic";

    public static final String FOLLOW_UPDATE_TOPIC = "follow-update-topic";

    public static final String GENERATE_FEED_TOPIC = "generate-feed-topic";




    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        Map<String,Object>  configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);

    }
    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        Map<String,Object>  configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.algo.QuaraApp.events");


        return new DefaultKafkaConsumerFactory<>(configProps);


    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        return factory;
    }


//-------------------------------------------------------Update Count Consumer-----------------------------------------------------
    @Bean
    public ConsumerFactory<String, Object> followConsumerFactory(){
        Map<String,Object>  configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG,followGroupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.algo.QuaraApp.events");


        return new DefaultKafkaConsumerFactory<>(configProps);


    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> followKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(followConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }



    //-------------------------------------------------------feed generation -----------------------------------------------------

    @Bean
    public ConsumerFactory<String,Object> generateFeedFactory(){

        HashMap<String,Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG,generateFeedId);

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.algo.QuaraApp.events");

        return new DefaultKafkaConsumerFactory<>(configProps);


    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,Object> feedServiceKafkaListenerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(3);
        factory.setConsumerFactory(generateFeedFactory());

        return factory;


    }








}
