package com.jallAssessment.JallAssessment.repository;

import com.jallAssessment.JallAssessment.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository  extends JpaRepository<Contact, Long> {
    @Query("select c from Contact c where c.name= :#{#name} and c.surname = :#{#surname}")
    Optional<Contact> findContact(String name, String surname);

    //@Query(value = "select * from TB_CONTACT where tb_user_email=:email#{#name}", nativeQuery = true)
    @Query("select c from Contact c where c.user.email= :#{#email}")
    List<Contact> findByUser(@Param("email") String email);
}
