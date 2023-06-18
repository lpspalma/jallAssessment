package com.jallAssessment.JallAssessment.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_CONTACT")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String birthday;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Phone> phones;

    private String relative;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TB_USER_id", nullable = false)
    private User user;

    public void addPhones(List<Phone> phones){
        this.phones = phones;
        phones.forEach(phone -> phone.setContact(this));
    }
}
