package com.jallAssessment.JallAssessment.repository;

import com.jallAssessment.JallAssessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
