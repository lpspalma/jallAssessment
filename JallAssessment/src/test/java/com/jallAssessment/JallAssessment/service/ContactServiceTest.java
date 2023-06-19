package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.ContactDTO;
import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.model.User;
import com.jallAssessment.JallAssessment.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private UserService userService;

    @Mock
    private PhoneService phoneService;

    @InjectMocks
    private ContactService contactService;

    @Test
    void when_successfullyCreateNewContactWithOnePhone_then_SaveAndReturnDTO() {
        when(contactRepository.findContact(anyString(), anyString())).thenReturn(Optional.empty());
        when(contactRepository.save(any(Contact.class))).thenReturn(contact());

        ContactDTO dto = contactService.newContact(contactDTO());

        assertAll(
                () -> verify(contactRepository, times(1)).findContact("name", "surname"),
                () -> verify(contactRepository, times(1)).save(any(Contact.class)),
                () -> verify(phoneService, times(1)).buildPhoneFromPhoneDTO(any(Phone.class)),
                () -> verify(userService, times(1)).findUserById(anyString()),
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("1", dto.getUserId()),
                () -> assertEquals("name", dto.getName()),
                () -> assertEquals("surname", dto.getSurname()),
                () -> assertEquals("birthday", dto.getBirthday()),
                () -> assertEquals("relative", dto.getRelative()),
                () -> assertEquals(phone(), dto.getPhones())
        );
    }

    @Test
    void when_successfullyCreateNewContactWithTwoPhones_then_SaveAndReturnDTO() {
        ContactDTO requestDTO = contactDTO();
        requestDTO.setPhones(phones());
        Contact contact = contact();
        contact.setPhones(phones());

        when(contactRepository.findContact(anyString(), anyString())).thenReturn(Optional.empty());
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactDTO dto = contactService.newContact(requestDTO);

        assertAll(
                () -> verify(contactRepository, times(1)).findContact("name", "surname"),
                () -> verify(contactRepository, times(1)).save(any(Contact.class)),
                () -> verify(phoneService, times(2)).buildPhoneFromPhoneDTO(any(Phone.class)),
                () -> verify(userService, times(1)).findUserById(anyString()),
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("1", dto.getUserId()),
                () -> assertEquals("name", dto.getName()),
                () -> assertEquals("surname", dto.getSurname()),
                () -> assertEquals("birthday", dto.getBirthday()),
                () -> assertEquals("relative", dto.getRelative()),
                () -> assertEquals(phones(), dto.getPhones())
        );
    }

    @Test
    void when_successfullyCreateNewContactWithNoRelatives_then_SaveAndReturnDTO() {
        ContactDTO requestDTO = contactDTO();
        requestDTO.setRelative(null);
        Contact contact = contact();
        contact.setRelative("");

        when(contactRepository.findContact(anyString(), anyString())).thenReturn(Optional.empty());
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactDTO dto = contactService.newContact(requestDTO);

        assertAll(
                () -> verify(contactRepository, times(1)).findContact("name", "surname"),
                () -> verify(contactRepository, times(1)).save(any(Contact.class)),
                () -> verify(phoneService, times(1)).buildPhoneFromPhoneDTO(any(Phone.class)),
                () -> verify(userService, times(1)).findUserById(anyString()),
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("1", dto.getUserId()),
                () -> assertEquals("name", dto.getName()),
                () -> assertEquals("surname", dto.getSurname()),
                () -> assertEquals("birthday", dto.getBirthday()),
                () -> assertEquals("", dto.getRelative()),
                () -> assertEquals(phone(), dto.getPhones())
        );
    }


    @Test
    void when_successfullyCreateNewContactWhenContactAlreadyExistButIsNewUser_then_SaveAndReturnDTO() {
        ContactDTO requestDTO = contactDTO();
        requestDTO.setUserId("2");
        Contact contact = contact();
        contact.setUser(User.builder().id(2L).build());

        when(contactRepository.findContact(anyString(), anyString())).thenReturn(Optional.of(contact()));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactDTO dto = contactService.newContact(requestDTO);

        assertAll(
                () -> verify(contactRepository, times(1)).findContact("name", "surname"),
                () -> verify(contactRepository, times(1)).save(any(Contact.class)),
                () -> verify(phoneService, times(1)).buildPhoneFromPhoneDTO(any(Phone.class)),
                () -> verify(userService, times(1)).findUserById(anyString()),
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("2", dto.getUserId()),
                () -> assertEquals("name", dto.getName()),
                () -> assertEquals("surname", dto.getSurname()),
                () -> assertEquals("birthday", dto.getBirthday()),
                () -> assertEquals("relative", dto.getRelative()),
                () -> assertEquals(phone(), dto.getPhones())
        );
    }

    @Test
    void when_ContactAlreadyExist_then_onlyReturnNull() {
        when(contactRepository.findContact(anyString(), anyString())).thenReturn(Optional.of(contact()));

        ContactDTO dto = contactService.newContact(contactDTO());

        assertAll(
                () -> verify(contactRepository, times(1)).findContact("name", "surname"),
                () -> verify(contactRepository, times(0)).save(any(Contact.class)),
                () -> verify(phoneService, times(0)).buildPhoneFromPhoneDTO(any(Phone.class)),
                () -> verify(userService, times(0)).findUserById(anyString()),
                () -> assertNull(dto)
        );
    }

    @Test
    void when_successfullyUpdateContact_then_SaveAndReturnDTO() {
        ContactDTO request = contactDTO();
        request.setName("nameUpdated");
        request.setSurname("surnameUpdated");
        request.setBirthday("birthdayUpdated");
        request.setRelative("relativeUpdated");
        request.setPhones(phones());
        when(contactRepository.findById(anyLong())).thenReturn(Optional.of(contact()));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact());

        ContactDTO dto = contactService.updateContact(1L, request);

        assertAll(
                () -> verify(contactRepository, times(1)).findById(1L),
                () -> verify(contactRepository, times(1)).save(any(Contact.class)),
                () -> assertEquals(1L, dto.getId()),
                () -> assertEquals("1", dto.getUserId()),
                () -> assertEquals("nameUpdated", dto.getName()),
                () -> assertEquals("surnameUpdated", dto.getSurname()),
                () -> assertEquals("birthdayUpdated", dto.getBirthday()),
                () -> assertEquals("relativeUpdated", dto.getRelative()),
                () -> assertEquals(phones(), dto.getPhones())
        );
    }

    @Test
    void when_failedToFindContactById_then_ReturnNull() {
        ContactDTO request = contactDTO();
        request.setName("nameUpdated");
        request.setSurname("surnameUpdated");
        request.setBirthday("birthdayUpdated");
        request.setRelative("relativeUpdated");
        when(contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        ContactDTO dto = contactService.updateContact(1L, request);

        assertAll(
                () -> verify(contactRepository, times(1)).findById(1L),
                () -> assertNull(dto)
        );
    }

    @Test
    void when_successfullyDeleteContact_then_ReturnTrue() {
        when(contactRepository.existsById(anyLong())).thenReturn(true);

        boolean result = contactService.deleteContact(1L);

        assertAll(
                () -> verify(contactRepository, times(1)).deleteById(1L),
                () -> assertTrue(result)
        );
    }

    @Test
    void when_failedToDeleteContactById_then_ReturnFalse() {
        when(contactRepository.existsById(anyLong())).thenReturn(false);

        boolean result = contactService.deleteContact(1L);

        assertAll(
                () -> verify(contactRepository, times(0)).deleteById(1L),
                () -> assertFalse(result)
        );
    }

    @Test
    void when_successfullyGetAllContactsFromUser_then_ReturnListDTOS() {
        when(contactRepository.findByUser(anyString())).thenReturn(Collections.singletonList(contact()));

        List<ContactDTO> result = contactService.getAllByUser("email");

        assertAll(
                () -> assertTrue(result.contains(contactDTO()))
        );
    }

    @Test
    void when_successfullyGetAllContactsFromUserButUserHaveNoContact_then_ReturnEmptyListDTOS() {
        when(contactRepository.findByUser(anyString())).thenReturn(new ArrayList<>());

        List<ContactDTO> result = contactService.getAllByUser("email");

        assertAll(
                () -> assertEquals(0, result.size())
        );
    }

    private List<Phone> phone() {
        return Collections.singletonList(Phone.builder().ddd("11").number("11111111").build());
    }

    private List<Phone> phones() {
        return Arrays.asList(Phone.builder().ddd("11").number("11111111").build(), Phone.builder().ddd("22").number("22222222").build());
    }

    private ContactDTO contactDTO() {
        return ContactDTO.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .userId("1")
                .birthday("birthday")
                .phones(phone())
                .relative("relative")
                .build();
    }

    private Contact contact() {
        return Contact.builder()
                .id(1L)
                .user(User.builder().id(1L).build())
                .name("name")
                .surname("surname")
                .birthday("birthday")
                .phones(phone())
                .relative("relative")
                .build();
    }
}
