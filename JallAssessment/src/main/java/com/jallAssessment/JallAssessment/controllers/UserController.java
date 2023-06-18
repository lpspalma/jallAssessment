package com.jallAssessment.JallAssessment.controllers;

import com.jallAssessment.JallAssessment.dto.NewUserDTO;
import com.jallAssessment.JallAssessment.dto.UserDTO;
import com.jallAssessment.JallAssessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/new")
    ResponseEntity<String> creatingNewUser(@RequestBody NewUserDTO dto) {
        UserDTO userDTO = userService.addNewUser(dto);

        return new ResponseEntity<>("Novo usuário cadastrado. " + userDTO, CREATED);
    }
}
