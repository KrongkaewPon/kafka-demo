package com.github.simplesteph.kafka.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoAssignSeel {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerDemoAssignSeel.class);

        String bootstrapServers = "127.0.0.1:9092";
        String topic = "first_topic";

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // assign and seek are mostly used to replay data or fetch a specific message

        // assign
        TopicPartition partitionToReadForm = new TopicPartition(topic, 0);
        long offsetToReadForm = 15L;
        consumer.assign(Arrays.asList(partitionToReadForm));

        // seek
        consumer.seek(partitionToReadForm, offsetToReadForm);

        int numberOfMsgToRead = 5;
        boolean keepOnReading = true;
        int numberOfMsgReadSoFar = 0;

        // poll for new data
        while (keepOnReading) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); // new in kafka 2.0.0
            for (ConsumerRecord<String, String> record : records) {
                numberOfMsgReadSoFar += 1;
                logger.info("Key: " + record.key() + ", Value: " + record.value() + "\n" +
                        "Partition: " + record.partition() + ", Offset: " + record.offset());
                if (numberOfMsgReadSoFar >= numberOfMsgToRead) {
                    keepOnReading = false; // to exit the while loop
                    break;
                }
            }
        }

        logger.info("Exit the app");
    }
}
