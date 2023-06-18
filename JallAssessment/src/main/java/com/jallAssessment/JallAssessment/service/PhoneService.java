package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.dto.PhoneDTO;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone buildPhoneFromPhoneDTO(PhoneDTO dto) {
        return
                Phone.builder()
                        .ddd(dto.getDdd())
                        .number(dto.getNumber())
                        .build();
    }
}
