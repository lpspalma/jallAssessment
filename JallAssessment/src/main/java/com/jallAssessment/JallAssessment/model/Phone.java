package com.jallAssessment.JallAssessment.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Entity
@Builder
@Table(name = "TB_PHONE")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String ddd;

    @NotBlank
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TB_CONTACT_id", nullable = false)
    private Contact contact;
}
