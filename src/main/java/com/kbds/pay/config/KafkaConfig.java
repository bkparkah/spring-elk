package com.kbds.pay.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    @Bean
    public Producer<String, String> producer(
            @Value("${kafka.bootstrap.servers}") String server
            , @Value("${kafka.key.serializer}") String keySearializer
            , @Value("${kafka.value.serializer}") String valueSearializer)  {
        Properties props = new Properties();
        props.put("bootstrap.servers", server );
        props.put("key.serializer", keySearializer );
        props.put("value.serializer", valueSearializer );

        return new KafkaProducer<>(props);
    }
}
