package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.dto.PhoneDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapperService modelMapper;

    public Contact newContact(ContactDTO contactDTO) {
        Optional<Contact> contactOptional = contactRepository.findContact(contactDTO.getName(), contactDTO.getSurname());
        if (contactOptional.isEmpty()) {
            return contactRepository.save(
                    Contact.builder()
                            .name(contactDTO.getName())
                            .surname(contactDTO.getSurname())
                            .birthday(contactDTO.getBirthday())
                            .user(userService.finUserById(contactDTO.userId))
                            .phones(modelMapper.collectionMapper(Collections.singletonList(contactDTO.getPhones()), Phone.class))
                            .relative(contactDTO.getRelative() != null && !contactDTO.getRelative().isEmpty() ? contactDTO.getRelative() : "")
                            .build());
        } else return updateContact(contactDTO, contactOptional.get());
    }

    public Contact updateContact(ContactDTO contactDTO, Contact contact) {
       if(contactDTO.getBirthday()!=null && contactDTO.getBirthday().isEmpty() && !contactDTO.getBirthday().equals(contact.getBirthday())) {
            contact.setBirthday(contact.getBirthday());
        }
        if(contactDTO.getRelative()!=null && !contactDTO.getRelative().isEmpty() && !contactDTO.getRelative().equals(contact.getRelative())){
            contact.setRelative(contactDTO.getRelative());
        }
        if(contactDTO.getPhones()!=null && !contactDTO.getPhones().isEmpty()){
            contact.setPhones(updatePhones(contactDTO.getPhones(), contact.getPhones()));
        }
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
