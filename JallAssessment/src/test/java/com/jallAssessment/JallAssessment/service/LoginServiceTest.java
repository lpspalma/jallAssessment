package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.JwtAuthenticationResponse;
import com.jallAssessment.JallAssessment.dto.SignInRequest;
import com.jallAssessment.JallAssessment.dto.SignUpRequest;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private LoginService loginService;

    @Test
    void when_successfullySignUp_then_ReturnJWTResponse() {
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(User.builder().build());
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        JwtAuthenticationResponse response = loginService.signup(SignUpRequest.builder().name("name").email("email").password("password").build());

        assertAll(
                () -> verify(passwordEncoder, times(1)).encode(anyString()),
                () -> verify(userRepository, times(1)).save(any(User.class)),
                () -> verify(jwtService, times(1)).generateToken(any(User.class)),
                () -> assertEquals(JwtAuthenticationResponse.builder().token("token").build(), response)
        );

    }

    @Test
    void when_successfullySignIn_then_ReturnJWTResponse() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(User.builder().build()));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");

        JwtAuthenticationResponse response = loginService.signin(SignInRequest.builder().email("email").password("password").build());

        assertAll(
                () -> verify(userRepository, times(1)).findByEmail(anyString()),
                () -> verify(jwtService, times(1)).generateToken(any(User.class)),
                () -> assertEquals(JwtAuthenticationResponse.builder().token("token").build(), response)
        );
    }

    @Test
    void when_badCredentialsSignIn_then_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        IllegalArgumentException response =
                assertThrows(IllegalArgumentException.class, () -> loginService.signin(SignInRequest.builder().email("email").password("password").build()));

        assertAll(
                () -> verify(userRepository, times(1)).findByEmail(anyString()),
                () -> verify(jwtService, times(0)).generateToken(any(User.class)),
                () -> assertEquals("Invalid email or password.", response.getMessage())
        );
    }
}
