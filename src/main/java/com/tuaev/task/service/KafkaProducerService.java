package com.tuaev.task.service;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface KafkaProducerService {

    void sendMessage(ProducerRecord<String, String> producerRecord);
}
