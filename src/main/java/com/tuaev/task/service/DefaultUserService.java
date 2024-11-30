package com.tuaev.task.service;

import com.tuaev.task.entity.User;
import com.tuaev.task.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(1L);
    }
}
