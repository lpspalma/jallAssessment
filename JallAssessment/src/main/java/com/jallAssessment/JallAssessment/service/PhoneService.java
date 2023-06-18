package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone buildPhoneFromPhoneDTO(Phone dto) {
        Phone phone = new Phone();
        BeanUtils.copyProperties(dto, phone);
        phoneRepository.save(phone);

        log.info("Telefone salvo com sucesso. " + phone);
        return phone;
    }
}