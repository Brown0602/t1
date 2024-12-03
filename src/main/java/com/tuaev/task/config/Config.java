package com.tuaev.task.config;

import com.tuaev.task.annotation.LogBefore;
import com.tuaev.task.entity.User;
import com.tuaev.task.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class Config {


    @LogBefore
    @Bean
    public CommandLineRunner addEntityUser(UserRepository userRepository) {
        return o -> userRepository.saveAll(List.of(new User("Владислав", "Туаев", 24)));
    }
}
