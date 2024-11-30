package com.tuaev.task.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultNotificationService implements NotificationService{

    private final JavaMailSender javaMailSender;
    @Value("${mail.myEmail}")
    private String email;

    public DefaultNotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @KafkaListener(topics = "task_status", groupId = "status")
    @Override
    public void sendMessage(ConsumerRecord<String, String> consumerRecord){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(email);
        message.setSubject("Уведомление о изменения статуса задачи");
        message.setText(consumerRecord.value());
        javaMailSender.send(message);
    }
}