package org.pl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pl.model.Repair;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class test {
    private static KafkaProducer producer;
    private static KafkaConsumer consumer;

    @BeforeAll
    public static void init() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192,kafka2:9292,kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer(producerConfig);

        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "clientconsumer");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192, kafka2:9292,kafka3:9392");
        consumer = new KafkaConsumer(consumerConfig);
        consumer.subscribe(List.of("naprawy"));
    }

    @Test
    void testunio() throws JsonMappingException {
        AvroMapper avroMapper = new AvroMapper();
        AvroSchema schema = avroMapper.schemaFor(Repair.class);

        int messagesReceived = 0;
        Map<Integer, Long> offsets = new HashMap<>();

        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
        while (messagesReceived < 100) {
            ConsumerRecords<UUID,String> records = consumer.poll(timeout);
            for (ConsumerRecord<UUID, String> record : records) {
                String result = formatter.format(new Object[]{record.topic(), record.partition(), record.offset(), record.key(), record.value()});
                System.out.println(result);
                offsets.put(record.partition(), record.offset());
                messagesReceived++;
            }
        }
        System.out.println(offsets);
        consumer.commitAsync();
    }
}
