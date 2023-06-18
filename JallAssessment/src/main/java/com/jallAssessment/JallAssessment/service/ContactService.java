package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.dto.PhoneDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private ModelMapperService modelMapper;

    public ResponseEntity<String> newContact(ContactDTO contactDTO) {
        Optional<Contact> contactOptional = contactRepository.findContact(contactDTO.getName(), contactDTO.getSurname());
        if (contactOptional.isEmpty() || contactOptional.get().getUser().getId() != Long.parseLong(contactDTO.getUserId())) {
            modelMapper.mapper(contactRepository.save(buildContactFromDTO(contactDTO)), ContactDTO.class);
            return new ResponseEntity<>("Novo contato cadastrado.", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Contato já existe.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateContact(long id, ContactDTO contactDTO) {
        try {
            Contact contact = contactRepository.findById(id).orElseThrow();
            contact.setName(contactDTO.getName() != null ? contactDTO.getName() : contact.getName());
            contact.setSurname(contactDTO.getSurname() != null ? contactDTO.getSurname() : contact.getSurname());
            contact.setBirthday(contactDTO.getBirthday() != null ? contactDTO.getBirthday() : contact.getBirthday());
            contact.setRelative(contactDTO.getRelative() != null ? contactDTO.getRelative() : contact.getRelative());
            contact.setPhones(contactDTO.getPhones() != null ? updatePhones(contactDTO.getPhones(), contact.getPhones()) : contact.getPhones());

            return new ResponseEntity<>("Contato utualizado com Sucesso. " + contactRepository.save(contact), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Contato não existe. ", HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<String> deleteContact(long id) {
        try {
            contactRepository.delete(contactRepository.findById(id).orElseThrow());
            return new ResponseEntity<>("Contato deletado com Sucesso. ", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Contato não existe. ", HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<ContactDTO>> getAllByUser(String userId) {
        List<Contact> contacts = contactRepository.findByUser(userService.findUserById(userId));
        if (contacts.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modelMapper.collectionMapper(List.of(contacts), ContactDTO.class), HttpStatus.OK);
    }

    private Contact buildContactFromDTO(ContactDTO contactDTO) {
        Contact contact;
        contact = Contact.builder()
                .name(contactDTO.getName())
                .surname(contactDTO.getSurname())
                .birthday(contactDTO.getBirthday())
                .user(userService.findUserById(contactDTO.userId))
                .phones(contactDTO.getPhones().stream().map(phoneDTO -> phoneService.buildPhoneFromPhoneDTO(phoneDTO)).collect(Collectors.toList()))
                .relative(contactDTO.getRelative() != null && !contactDTO.getRelative().isEmpty() ? contactDTO.getRelative() : "")
                .build();
        return contact;
    }

    private List<Phone> updatePhones(List<PhoneDTO> dtos, List<Phone> phones) {
        List<Phone> phonesToUpdate = new ArrayList<>(phones);
        dtos.forEach(dto -> {
            if (comparePhoneDTOWithPhone(dto, phones) == null) {
                phonesToUpdate.add(Phone.builder().ddd(dto.getDdd()).number(dto.getNumber()).build());
            }
        });
        return phonesToUpdate;
    }

    private Phone comparePhoneDTOWithPhone(PhoneDTO dto, List<Phone> phones) {
        try {
            return phones.stream().filter(phone -> dto.getDdd().equals(phone.getDdd()) && dto.getNumber().equals(phone.getNumber())).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
