package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void when_successfullyFindUserByEmail(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(User.builder().build()));

        assertNotNull(userService.findUserByEmail("email"));
    }

    @Test
    void when_failedToFindUserByEmail(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertNull(userService.findUserByEmail("email"));
    }
}
