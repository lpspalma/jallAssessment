package com.jallAssessment.JallAssessment.repository;

import com.jallAssessment.JallAssessment.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
