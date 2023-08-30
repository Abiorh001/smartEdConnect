package com.abiorh.smartEdConnect.student.response;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class StudentDto {
    @Id
    private UUID id;

    @NotBlank(message = "please enter your first name")
    private String firstName;

    @NotBlank(message = "please enter your last name")
    private String lastName;

    private String email;
}
