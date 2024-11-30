package com.tuaev.task.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface NotificationService {

    void sendMessage(ConsumerRecord<String, String> consumerRecord);
}
