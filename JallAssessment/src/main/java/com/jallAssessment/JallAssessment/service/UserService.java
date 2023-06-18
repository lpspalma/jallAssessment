package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserById(String userId) {
        try {
            return userRepository.findById(Long.parseLong(userId)).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Usuario não encontrado. id = " + userId);
            return null;
        }
    }

    public ResponseEntity<String> addNewUser(UserDTO dto) {
        User user = new User();
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) {
            BeanUtils.copyProperties(dto, user);
            userRepository.save(user);
            log.info("Novo usuário cadastrado com sucesso. " + user);
        } else user = optionalUser.get();

        userRepository.save(user);

        return new ResponseEntity<>("Novo usuário cadastrado.", CREATED);
    }
}
