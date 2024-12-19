package com.tuaev.task.service;

import com.tuaev.task.config.MailConfiguration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultNotificationService implements NotificationService {

    private final JavaMailSender javaMailSender;
    private final MailConfiguration mailConfiguration;

    public DefaultNotificationService(JavaMailSender javaMailSender, MailConfiguration mailConfiguration) {
        this.javaMailSender = javaMailSender;
        this.mailConfiguration = mailConfiguration;
    }

    @KafkaListener(topics = "task_status", groupId = "status")
    @Override
    public void sendMessage(ConsumerRecord<String, String> consumerRecord) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailConfiguration.getEmail());
        message.setFrom(mailConfiguration.getEmail());
        message.setSubject("Уведомление об статусе задачи");
        message.setText("Ваш статус задачи: " + consumerRecord.value());
        javaMailSender.send(message);
    }
}
