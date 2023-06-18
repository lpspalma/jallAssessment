package com.jallAssessment.JallAssessment.controllers;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/new")
    ResponseEntity<String> createNewContact(@RequestBody ContactDTO contactDTO) {
        return contactService.newContact(contactDTO);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<String> updateContact(@PathVariable("id") long id, @RequestBody ContactDTO contactDTO) {
        return contactService.updateContact(id, contactDTO);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteContact(@PathVariable("id") long id) {
        return contactService.deleteContact(id);
    }

    @GetMapping("/all/{id}")
    ResponseEntity<List<ContactDTO>> getAllByUser(@PathVariable("id") long userId) {
        return contactService.getAllByUser(userId);
    }
}
