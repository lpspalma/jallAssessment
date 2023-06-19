package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.SignUpRequestDTO;
import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void when_successfullyFindUserById(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));

        assertNotNull(userService.findUserByEmail("1"));
    }

    @Test
    void when_failedToFindUserById(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertNull(userService.findUserByEmail("1"));
    }

    /*@Test
    void when_successfullyAddNewUser_then_returnUserDTO(){
        User user = User.builder().id(1L).name("name").email("email").password("password").build();
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = userService.addNewUser(SignUpRequestDTO.builder().name("name").email("email").password("password").build());

        assertAll(
                () -> verify(userRepository, times(1)).save(any()),
                () -> assertEquals(1L, userDTO.getId()),
                () -> assertEquals("name", userDTO.getName()),
                () -> assertEquals("email", userDTO.getEmail())
        );
    }

    @Test
    void when_userAlreadyExist_then_returnUserDTO(){
        User user = User.builder().id(1L).name("name").email("email").password("password").build();
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));

        UserDTO userDTO = userService.addNewUser(SignUpRequestDTO.builder().name("name").email("email").password("password").build());

        assertAll(
                () -> verify(userRepository, times(0)).save(any()),
                () -> assertEquals(1L, userDTO.getId()),
                () -> assertEquals("name", userDTO.getName()),
                () -> assertEquals("email", userDTO.getEmail())
        );
    }*/
}
