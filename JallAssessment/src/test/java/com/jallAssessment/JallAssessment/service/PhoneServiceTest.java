package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceTest {
    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    void when_buildPhoneSuccessfully_then_SaveAndReturnPhone(){
        when(phoneRepository.save(any(Phone.class))).thenReturn(Phone.builder().id(1L).ddd("11").number("11111111").build());

        Phone newPhone = phoneService.buildPhoneFromPhoneDTO(Phone.builder().ddd("11").number("11111111").build());

        assertAll(
                () -> verify(phoneRepository, times(1)).save(any(Phone.class)),
                () -> assertEquals(1L, newPhone.getId()),
                () -> assertEquals("11", newPhone.getDdd()),
                () -> assertEquals("11111111", newPhone.getNumber())
        );
    }
}
