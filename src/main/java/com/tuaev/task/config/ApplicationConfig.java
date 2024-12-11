package com.tuaev.task.config;

import com.tuaev.task.annotation.LogBefore;
import com.tuaev.task.entity.User;
import com.tuaev.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@PropertySource("data_mail.properties")
@Configuration
public class ApplicationConfig {

    @Value("${mail.transport.protocol}")
    private String protocol;
    @Value("${mail.smtps.host}")
    private String host;
    @Value("${mail.smtps.user}")
    private String username;
    @Value("${mail.password}")
    private String password;

    @LogBefore
    @Bean
    public CommandLineRunner addEntityUser(UserRepository userRepository) {
        return o -> userRepository.saveAll(List.of(new User("Владислав", "Туаев", 24)));
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(protocol);
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }
}
