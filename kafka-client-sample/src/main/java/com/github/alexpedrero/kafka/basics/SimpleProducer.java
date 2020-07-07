package com.github.alexpedrero.kafka.basics;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);

        String bootstrapServers = "192.168.0.39:19092";

        // producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i=0; i<2000000; i++) {
            // create a new record
            final ProducerRecord<String, String> record = new ProducerRecord<String, String>("sample-topic", "hello kafka " + Integer.toString(i));

            //send data
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null) {
                        // success!
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
        }

        producer.flush();
        producer.close();

    }
}
