package com.jallAssessment.JallAssessment.controllers;

import com.jallAssessment.JallAssessment.dto.JwtAuthenticationResponse;
import com.jallAssessment.JallAssessment.dto.SignInRequest;
import com.jallAssessment.JallAssessment.dto.SignUpRequest;
import com.jallAssessment.JallAssessment.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/signup")
    ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(loginService.signup(request));
    }

    @PostMapping("/signin")
    ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(loginService.signin(request));
    }
}
