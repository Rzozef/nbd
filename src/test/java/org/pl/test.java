package org.pl;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.Test;
import org.pl.model.Repair;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class test {

    private KafkaProducer producer;
    private KafkaConsumer consumer;

    @Test
    void init() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192, kafka2:9292, kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer(producerConfig);


        //sendRepairAsync
        Callback callback = this::onCompletion;

        for (int i = 0; i < 100; i++) {
            Repair repair = new Repair(UUID.randomUUID(), false, null, null);
            Jsonb jsonb = JsonbBuilder.create();
            String jsonClient = jsonb.toJson(repair);
            UUID entityId = repair.getEntityId();
            ProducerRecord<UUID, String> record = new ProducerRecord<>(
                    "naprawy", entityId, jsonClient);
            producer.send(record, callback);
        }

        System.out.println("koniec producenta");

        //consumerInit
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "repairconsumer");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka1:9192, kafka2:9292, kafka3:9392");
        consumer = new KafkaConsumer(consumerConfig);
        consumer.subscribe(List.of("naprawy"));

        //receive
        int messagesReceived = 0;
        Map<Integer, Long> offsets = new HashMap<>();

        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
        while (messagesReceived < 100) {
            ConsumerRecords<UUID, String> records = consumer.poll(timeout);
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

    @Test
    void sendClientAsync() {
        Callback callback = this::onCompletion;

        for (int i = 0; i < 100; i++) {
            Repair repair = new Repair(UUID.randomUUID(), false, null, null);
            Jsonb jsonb = JsonbBuilder.create();
            String jsonClient = jsonb.toJson(repair);
            UUID entityId = repair.getEntityId();
            ProducerRecord<UUID, String> record = new ProducerRecord<>(
                    "naprawy", entityId, jsonClient);
            producer.send(record, callback);
        }
    }

    private void onCompletion(RecordMetadata data, Exception exception) {
        if (exception == null) {
            System.out.println(data.offset());
        } else {
            System.out.println(exception);
        }
    }
    @Test
    void messageAfterCreatingNewRepairTest() {
        Repair repair = Repair.builder()
                .entityId(UUID.randomUUID())
                .archive(false)
                .client(null)
                .hardware(null)
                .build();
    }
}
