package com.tuaev.task.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tuaev.task.entity.User;
import com.tuaev.task.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@Configuration
public class ApplicationConfiguration {

    private final MailConfigurationProperties mailConfiguration;

    public ApplicationConfiguration(MailConfigurationProperties mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    @Bean
    public CommandLineRunner addEntityUser(UserRepository userRepository) {
        return o -> userRepository.saveAll(List.of(new User("Владислав", "Туаев", 24)));
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(mailConfiguration.getProtocol());
        javaMailSender.setHost(mailConfiguration.getHost());
        javaMailSender.setUsername(mailConfiguration.getUser());
        javaMailSender.setPassword(mailConfiguration.getPassword());
        return javaMailSender;
    }
}
