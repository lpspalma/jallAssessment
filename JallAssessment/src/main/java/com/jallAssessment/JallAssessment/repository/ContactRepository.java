package com.jallAssessment.JallAssessment.repository;

import com.jallAssessment.JallAssessment.model.Contact;
import com.jallAssessment.JallAssessment.model.Phone;
import com.jallAssessment.JallAssessment.model.User;
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

    @Query(value = "select phones from TB_CONTACT c inner join contact_phones p on c.id = p.contact_id where id=:contactId", nativeQuery = true)
    List<Phone> getPhones(@Param("contactId") String contactId);

    @Query(value = "select * from TB_CONTACT where tb_user_id=:userId", nativeQuery = true)
    List<Contact> findByUser(@Param("userId") long userId);
}
