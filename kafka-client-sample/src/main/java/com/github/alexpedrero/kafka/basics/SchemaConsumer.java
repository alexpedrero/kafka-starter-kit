package com.github.alexpedrero.kafka.basics;

import com.example.Customer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SchemaConsumer {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SimpleConsumer.class.getName());

        String bootstrapServers = "192.168.0.39:19092";
        String groupId = "customer-consumer-group";
        String topic = "customer-topic";

        // consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        properties.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.0.39:8081");

        KafkaConsumer<String, Customer> kafkaConsumer = new KafkaConsumer<String, Customer>(properties);
        kafkaConsumer.subscribe(Collections.singleton(topic));

        // pool for data
        while(true) {
            ConsumerRecords<String, Customer> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Customer> record : records) {
                Customer customer = record.value();
                System.out.println(customer);
            }

            kafkaConsumer.commitSync();
        }
    }
}
