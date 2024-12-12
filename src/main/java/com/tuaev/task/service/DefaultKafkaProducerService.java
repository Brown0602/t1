package com.tuaev.task.service;

import com.tuaev.task.annotation.LogKafkaMethod;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DefaultKafkaProducerService implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public DefaultKafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @LogKafkaMethod
    @Override
    public void sendMessage(ProducerRecord<String, String> producerRecord) {
        kafkaTemplate.send(producerRecord);
    }
}
