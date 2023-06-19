package com.jallAssessment.JallAssessment.service;

import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public Phone buildPhoneFromPhoneDTO(Phone dto) {
        Phone phone = new Phone();
        BeanUtils.copyProperties(dto, phone);
        phone = phoneRepository.save(phone);

        log.info("Telefone salvo com sucesso. " + phone);
        return phone;
    }

    public List<Phone> updatePhones(List<Phone> dtos, List<Phone> phones) {
        List<Phone> phonesToUpdate = new ArrayList<>(phones);
        dtos.forEach(dto -> {
            if (comparePhoneDTOWithPhone(dto, phones) == null) {
                phonesToUpdate.add(Phone.builder().ddd(dto.getDdd()).number(dto.getNumber()).build());
            }
        });
        return phoneRepository.saveAll(phonesToUpdate);
    }

    private Phone comparePhoneDTOWithPhone(Phone dto, List<Phone> phones) {
        try {
            return phones.stream().filter(phone -> dto.getDdd().equals(phone.getDdd()) && dto.getNumber().equals(phone.getNumber())).findFirst().orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}