package com.tuaev.task.service;

import com.tuaev.task.entity.User;
import com.tuaev.task.exception.NotFoundUserException;
import com.tuaev.task.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundUserException("Пользователь не найден"));
    }
}
