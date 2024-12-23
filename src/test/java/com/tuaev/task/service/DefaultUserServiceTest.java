package com.tuaev.task.service;

import com.tuaev.task.entity.User;
import com.tuaev.task.exception.NotFoundUserException;
import com.tuaev.task.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DefaultUserService defaultUserService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Владислав", "Туаев", 24);
    }

    @Test
    void findUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> optionalUser = Optional.ofNullable(defaultUserService.findById(1L));
        Assertions.assertThat(optionalUser).isPresent().contains(user);
    }

    @Test
    void findUserByIdUserNotFoundExceptionTest() {
        when(userRepository.findById(2L)).thenThrow(new NotFoundUserException());
        Assertions.assertThatThrownBy(() -> defaultUserService.findById(2L))
                .isInstanceOf(NotFoundUserException.class);
    }
}
