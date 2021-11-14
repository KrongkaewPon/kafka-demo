package com.github.simplesteph.kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.swing.*;
import java.util.Properties;

public class ProducerDemo1 {
    public static void main(String[] args) {
        System.out.println("Hello");
        String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        // create producer record
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "Hello World");

        // send data - asynchronous
        producer.send(record);

        // flush data
        producer.flush();
        // flush and close producer
        producer.close();
    }
}
