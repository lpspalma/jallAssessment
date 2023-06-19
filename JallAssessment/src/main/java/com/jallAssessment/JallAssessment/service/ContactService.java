package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    private final UserService userService;

    private final PhoneService phoneService;

    public ContactDTO newContact(ContactDTO contactDTO) {
        ContactDTO dto = null;
        Optional<Contact> contactOptional = contactRepository.findContact(contactDTO.getName(), contactDTO.getSurname());
        if (contactOptional.isEmpty() || !contactOptional.get().getUser().getEmail().equals(contactDTO.getUser())) {
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
            contact.setPhones(contactDTO.getPhones() != null ? phoneService.updatePhones(contactDTO.getPhones(), contact.getPhones()) : contact.getPhones());
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

    public List<ContactDTO> getAllByUser(String email) {
        List<Contact> contacts = contactRepository.findByUser(email);
        List<ContactDTO> contactsDTO;
        if (contacts.isEmpty()) {
            log.info("Usuario " + email + " não possui contatos cadastrados");
        }
        contactsDTO = contacts.stream().map(this::buildDTOFromContact).toList();
        return contactsDTO;
    }

    private Contact buildContactFromDTO(ContactDTO contactDTO) {
        Contact contact;
        contact = Contact.builder()
                .name(contactDTO.getName())
                .surname(contactDTO.getSurname())
                .birthday(contactDTO.getBirthday())
                .user(userService.findUserByEmail(contactDTO.getUser()))
                .phones(contactDTO.getPhones().stream().map(phoneService::buildPhoneFromPhoneDTO).toList())
                .relative(contactDTO.getRelative() != null && !contactDTO.getRelative().isEmpty() ? contactDTO.getRelative() : "")
                .build();
        return contact;
    }

    private ContactDTO buildDTOFromContact(Contact contact) {
        return ContactDTO.builder()
                .id(contact.getId())
                .user(contact.getUser().getEmail())
                .name(contact.getName())
                .surname(contact.getSurname())
                .birthday(contact.getBirthday())
                .phones(contact.getPhones())
                .relative(contact.getRelative())
                .build();
    }
}
