package com.abiorh.smartEdConnect.school.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolUpdateDto {

    private UUID schoolId;

    @NotBlank(message = "please enter a valid school name")
    @Size(max = 255)
    private String schoolName;

    @NotBlank(message = "please enter a valid phone Number")
    @Size(max = 150)
    private String schoolPhoneNumber;

    @NotBlank(message = "please enter a valid email address")
    @Size(max = 150)
    private String schoolEmailAddress;

}
