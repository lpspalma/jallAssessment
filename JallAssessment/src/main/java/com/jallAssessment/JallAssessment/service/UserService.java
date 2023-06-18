package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.NewUserDTO;
import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

    public UserDTO addNewUser(NewUserDTO dto) {
        User user = new User();
        UserDTO userDTO = new UserDTO();
        Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
        if (optionalUser.isEmpty()) {
            BeanUtils.copyProperties(dto, user);
            user = userRepository.save(user);
            log.info("Novo usuário cadastrado com sucesso. " + user);
        } else user = optionalUser.get();

        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
