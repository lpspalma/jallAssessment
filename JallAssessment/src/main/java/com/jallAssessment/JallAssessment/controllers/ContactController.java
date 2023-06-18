package com.jallAssessment.JallAssessment.controllers;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        ContactDTO dto = contactService.newContact(contactDTO);
        if (dto == null)
            return new ResponseEntity<>("Contato já existe.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Novo contato cadastrado. " + dto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<String> updateContact(@PathVariable("id") long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO dto = contactService.updateContact(id, contactDTO);
        if (dto == null)
            return new ResponseEntity<>("Contato não existe. ", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>("Contato utualizado com Sucesso. " + dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteContact(@PathVariable("id") long id) {
        if (contactService.deleteContact(id))
            return new ResponseEntity<>("Contato deletado com Sucesso. ", HttpStatus.OK);
        return new ResponseEntity<>("Contato não existe.", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/{id}")
    ResponseEntity<List<ContactDTO>> getAllByUser(@PathVariable("id") long userId) {
        List<ContactDTO> dto = contactService.getAllByUser(userId);
        if (dto.isEmpty())
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
