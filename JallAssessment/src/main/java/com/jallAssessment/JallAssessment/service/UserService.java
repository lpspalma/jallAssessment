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

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
