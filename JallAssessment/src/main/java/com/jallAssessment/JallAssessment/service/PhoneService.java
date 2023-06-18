package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone buildPhoneFromPhoneDTO(Phone dto) {
        Phone phone = new Phone();
        BeanUtils.copyProperties(dto, phone);
        phoneRepository.save(phone);

        return phone;
    }
}