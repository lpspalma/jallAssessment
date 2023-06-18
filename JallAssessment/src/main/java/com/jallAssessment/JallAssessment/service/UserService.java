package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.CREATED;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapperService modelMapper;

    public User findUserById(String userId) {
        try {
            return userRepository.findById(Long.parseLong(userId)).orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public ResponseEntity<String> addNewUser(UserDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElse(
                User.builder()
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(Base64.getEncoder().encodeToString(dto.getPassword().getBytes()))
                        .build()
        );
        userRepository.save(user);
        return new ResponseEntity<>("Novo usuário cadastrado.", CREATED);
    }
}
