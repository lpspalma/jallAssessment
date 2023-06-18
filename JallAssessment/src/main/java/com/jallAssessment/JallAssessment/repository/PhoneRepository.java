package com.jallAssessment.JallAssessment.repository;

import com.jallAssessment.JallAssessment.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query(value = "select * from tb_phone p where ddd= :#{#ddd} and number = :#{#number}", nativeQuery = true)
    Optional<Phone> findPhone(@Param("ddd") String ddd, @Param("number") String number);

}
