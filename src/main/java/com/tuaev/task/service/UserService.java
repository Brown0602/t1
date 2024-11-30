package com.tuaev.task.service;

import com.tuaev.task.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);
}
