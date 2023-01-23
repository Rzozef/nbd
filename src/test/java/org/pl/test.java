package org.pl;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.Test;
import org.pl.model.Repair;

import java.util.Properties;
import java.util.UUID;

public class test {

    private KafkaProducer producer;

    @Test
    void initProducer() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "0.0.0.0:9192, 0.0.0.0:9292, 0.0.0.0:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producer = new KafkaProducer(producerConfig);


        //sendClientAsync
        Callback callback = this::onCompletion;

        for (int i = 0; i < 100; i++) {
            Repair repair = new Repair(UUID.randomUUID(), false, null, null);
            Jsonb jsonb = JsonbBuilder.create();
            String jsonClient = jsonb.toJson(repair);
            UUID entityId = repair.getEntityId();
            ProducerRecord<UUID, String> record = new ProducerRecord<>(
                    "naprawy", entityId, jsonClient);
            producer.send(record, callback);
            System.out.println(record);
        }
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
}
