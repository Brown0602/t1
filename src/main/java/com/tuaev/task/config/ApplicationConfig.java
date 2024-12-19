package com.tuaev.task.config;

import com.tuaev.task.entity.User;
import com.tuaev.task.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@Configuration
public class ApplicationConfig {

    private final MailConfiguration mailConfiguration;

    public ApplicationConfig(MailConfiguration mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    @Bean
    public CommandLineRunner addEntityUser(UserRepository userRepository) {
        return o -> userRepository.saveAll(List.of(new User("Владислав", "Туаев", 24)));
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
