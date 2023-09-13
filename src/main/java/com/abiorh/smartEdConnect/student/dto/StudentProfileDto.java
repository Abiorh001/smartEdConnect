package com.abiorh.smartEdConnect.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProfileDto {


    private UUID id;
    private String phoneNumber;
    private String dateOfBirth;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;


}
