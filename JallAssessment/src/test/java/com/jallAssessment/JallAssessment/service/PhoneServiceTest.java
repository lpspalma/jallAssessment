package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {
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

    @Test
    void when_updatePhoneSuccessfully_then_SaveAndReturnPhoneListUpdated(){
        List<Phone> phonesUpdated = Arrays.asList(Phone.builder().ddd("11").number("11111111").build(), Phone.builder().ddd("22").number("22222222").build(), Phone.builder().ddd("33").number("33333333").build()) ;
        when(phoneRepository.saveAll(anyList())).thenReturn(phonesUpdated);

        List<Phone> result = phoneService.updatePhones(phones(), phone());

        assertAll(
                () -> verify(phoneRepository, times(1)).saveAll(anyList()),
                () -> assertEquals(phonesUpdated, result)
        );
    }

    private List<Phone> phone() {
        return Collections.singletonList(Phone.builder().ddd("11").number("11111111").build());
    }

    private List<Phone> phones() {
        return Arrays.asList(Phone.builder().ddd("22").number("22222222").build(), Phone.builder().ddd("33").number("33333333").build());
    }
}
