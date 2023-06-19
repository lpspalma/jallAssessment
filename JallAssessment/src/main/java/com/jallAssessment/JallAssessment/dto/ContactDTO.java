package com.jallAssessment.JallAssessment.dto;

import com.jallAssessment.JallAssessment.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private long id;
    private String user;
    private String name;
    private String surname;
    private String birthday;
    private List<Phone> phones;
    private String relative;
}
