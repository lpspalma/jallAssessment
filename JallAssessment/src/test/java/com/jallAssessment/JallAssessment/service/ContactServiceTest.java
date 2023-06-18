package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.dto.PhoneDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactServiceTest {
    private final ContactService contactService = new ContactService();
    @Test
    void updateContactTest(){
/*        List<PhoneDTO> phoneDTOS = Arrays.asList(PhoneDTO.builder().ddd("11").number("1111111").build(), PhoneDTO.builder().ddd("22").number("222222").build());
        List<Phone> phones = Arrays.asList(Phone.builder().ddd("33").number("333333").build(), Phone.builder().ddd("22").number("222222").build());

        ContactDTO dto = ContactDTO.builder()
                .name("name")
                .surname("surname")
                .birthday("birthday")
                .phones(phoneDTOS)
                .relative("relative")
                .build();

        Contact contact = Contact.builder()
                .name("name")
                .surname("surname")
                .birthday("birthday")
                .phones(phones)
                .build();

        Contact newContact = contactService.updateContact(dto, contact);

        assertEquals("name", newContact.getName());
        assertEquals("surname", newContact.getSurname());
        assertEquals("birthday", newContact.getBirthday());
        assertEquals("relative", newContact.getRelative());
        assertTrue( newContact.getPhones().contains(Phone.builder().ddd("11").number("1111111").build()));*/
    }
}
