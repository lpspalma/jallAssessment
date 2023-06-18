package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneService phoneService;

    public ContactDTO newContact(ContactDTO contactDTO) {
        ContactDTO dto = null;
        Optional<Contact> contactOptional = contactRepository.findContact(contactDTO.getName(), contactDTO.getSurname());
        if (contactOptional.isEmpty() || contactOptional.get().getUser().getId() != Long.parseLong(contactDTO.getUserId())) {
            Contact contact = contactRepository.save(buildContactFromDTO(contactDTO));
            log.info("Contato salvo com sucesso.");
            dto = buildDTOFromContact(contact);
        }

        return dto;
    }

    public ContactDTO updateContact(long id, ContactDTO contactDTO) {
        try {
            Contact contact = contactRepository.findById(id).orElseThrow();
            contact.setName(contactDTO.getName() != null ? contactDTO.getName() : contact.getName());
            contact.setSurname(contactDTO.getSurname() != null ? contactDTO.getSurname() : contact.getSurname());
            contact.setBirthday(contactDTO.getBirthday() != null ? contactDTO.getBirthday() : contact.getBirthday());
            contact.setRelative(contactDTO.getRelative() != null ? contactDTO.getRelative() : contact.getRelative());
            contact.setPhones(contactDTO.getPhones() != null ? updatePhones(contactDTO.getPhones(), contact.getPhones()) : contact.getPhones());
            contactRepository.save(contact);
            log.info("Contato salvo com sucesso. " + contact);
            return buildDTOFromContact(contact);

        } catch (NoSuchElementException e) {
            log.error("Falha em atualizar o contato. " + contactDTO);
            return null;
        }
    }

    public boolean deleteContact(long id) {
        if (contactRepository.existsById(id)){
            contactRepository.deleteById(id);
            log.info("Contato deletado com Sucesso.  id = " + id);
            return true;
        }
        log.error("Contato não existe. id = " + id);
        return false;
    }

    public List<ContactDTO> getAllByUser(long userId) {
        List<Contact> contacts = contactRepository.findByUser(userId);
        List<ContactDTO> contactsDTO;
        if (contacts.isEmpty()) {
            log.info("Usuario " + userId + " não possui contatos cadastrados");
        }
        contactsDTO = contacts.stream().map(this::buildDTOFromContact).collect(Collectors.toList());
        return contactsDTO;
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

    private ContactDTO buildDTOFromContact(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .userId(String.valueOf(contact.getUser().getId()))
                .name(contact.getName())
                .surname(contact.getSurname())
                .birthday(contact.getBirthday())
                .phones(contact.getPhones())
                .relative(contact.getRelative())
                .build();
    }

    private List<Phone> updatePhones(List<Phone> dtos, List<Phone> phones) {
        List<Phone> phonesToUpdate = new ArrayList<>(phones);
        dtos.forEach(dto -> {
            if (comparePhoneDTOWithPhone(dto, phones) == null) {
                phonesToUpdate.add(Phone.builder().ddd(dto.getDdd()).number(dto.getNumber()).build());
            }
        });
        return phonesToUpdate;
    }

    private Phone comparePhoneDTOWithPhone(Phone dto, List<Phone> phones) {
        try {
            return phones.stream().filter(phone -> dto.getDdd().equals(phone.getDdd()) && dto.getNumber().equals(phone.getNumber())).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
