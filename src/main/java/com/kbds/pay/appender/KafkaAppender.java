package com.kbds.pay.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaAppender extends AppenderBase<ILoggingEvent>  {

    private String topic;
    private String bootstrapServers;
    private Producer<String, String> producer;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void append(ILoggingEvent eventObject) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("ts", DateFormatUtils.format(eventObject.getTimeStamp() , "yyyy-MM-dd HH:mm:ss"));
        messageMap.put("message", eventObject.getFormattedMessage());
        try {
            producer.send(new ProducerRecord<>(topic, objectMapper.writeValueAsString(messageMap)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public void start() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers );
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
        producer = new KafkaProducer<String, String>(props);
        super.start();
    }

    public static void main(String[] args){
        Properties props = new Properties();
        props.put("bootstrap.servers", "3.35.217.180:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        producer.send(new ProducerRecord<>("pay-logger", "key1", "Hello Kafka!"));
        producer.close();


    }

    @Override
    public void stop() {
        super.stop();
    }
}
