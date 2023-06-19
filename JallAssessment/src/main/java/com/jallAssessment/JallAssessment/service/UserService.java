package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email).orElseThrow();
        } catch (NoSuchElementException e) {
            log.error("Usuario não encontrado. id = " + email);
            return null;
        }
    }

    /*public UserDTO addNewUser(SignUpRequestDTO dto) {
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
    }*/

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
