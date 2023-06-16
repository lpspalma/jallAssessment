package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User finUserById(String userId) {
        try {
            return userRepository.findById(Long.parseLong(userId)).orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
