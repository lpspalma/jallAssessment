package com.jallAssessment.JallAssessment.dto;

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
    public String userId;
    private String name;
    private String surname;
    private String birthday;
    private List<PhoneDTO> phones;
    private String relative;
}
