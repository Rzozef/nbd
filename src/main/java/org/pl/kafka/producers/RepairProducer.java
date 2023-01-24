package org.pl.kafka.producers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.pl.model.Repair;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RepairProducer {
    private final Producer<UUID, String> producer;
    private final String topic;
    private final Jsonb jsonb = JsonbBuilder.create();

    public RepairProducer(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka1:9192, kafka2:9292, kafka3:9392");
        props.put("acks", "all");
        props.put("key.serializer", UUIDSerializer.class.getName());
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void send(Repair repair) {
        try {
            Future<RecordMetadata> sent = producer.send(new ProducerRecord<>(topic, repair.getID(), jsonb.toJson(repair)), (recordMetadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                }
            });
            RecordMetadata metadata = sent.get();
            System.out.println("Object sent successfully with key: " + repair.getID() + " and partition: " + metadata.partition() + " offset: " + metadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
