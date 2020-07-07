package com.github.alexpedrero.kafka.basics;

import com.example.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SchemaProducer {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

        String bootstrapServers = "192.168.0.39:19092";

        // producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.setProperty(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://192.168.0.39:8081");

        KafkaProducer<String, Customer> producer = new KafkaProducer<String, Customer>(properties);

        String topic = "customer-topic";
        Customer customer = Customer.newBuilder()
                .setFirstName("Lemmy").setLastName("Kilmister").setAge(60).build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<>(topic, customer);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e == null) {
                    logger.info("Metadata received: \n" +
                            "Topic:" + recordMetadata.topic() + "\n" +
                            "Partition: " + recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp());
                } else {
                    e.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();
    }
}
