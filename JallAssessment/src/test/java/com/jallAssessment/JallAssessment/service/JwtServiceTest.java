package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private final JwtService jwtService = new JwtService("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");

    @Test
    void when_successfullyExtractUserNameFromToken_then_ReturnUserName() {
        String username = jwtService.extractUserName(generateToken());
        assertEquals("email", username);
    }

    @Test
    void when_successfullyGenerateToken_then_ReturnToken() {
        String jwt = jwtService.generateToken(User.builder()
                .name("name")
                .email("email")
                .password("Password")
                .build());
        //nao eh possivel gerar um token e comparar valores ja que capta os segundos no exato momento fazendo assim ter q mudar o parametro de comparacao todas as vezes
        assertNotNull(jwt);
    }


    private String generateToken() {
        var user = User.builder()
                .name("name")
                .email("email")
                .password("Password")
                .build();
        return jwtService.generateToken(user);
    }

}
