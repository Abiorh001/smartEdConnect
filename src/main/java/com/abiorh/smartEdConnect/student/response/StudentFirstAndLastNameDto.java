package com.abiorh.smartEdConnect.student.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFirstAndLastNameDto {

    private String firstName;
    private String lastName;
}