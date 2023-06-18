package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserById(String userId) {
        try {
            return userRepository.findById(Long.parseLong(userId)).orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public ResponseEntity<String> addNewUser(UserDTO dto) {
        User user = new User();
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if(optionalUser.isEmpty()){
            BeanUtils.copyProperties(dto, user);
            userRepository.save(user);
        }else user = optionalUser.get();

        userRepository.save(user);

        return new ResponseEntity<>("Novo usuário cadastrado.", CREATED);
    }
}
