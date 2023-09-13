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
public class SchoolDto {

    private UUID schoolId;

    @NotBlank
    @Size(max = 255)
    private String schoolName;

    @NotBlank
    @Size(max = 150)
    private String schoolPhoneNumber;

    @NotBlank
    @Size(max = 150)
    private String schoolEmailAddress;

    @NotNull
    private Integer schoolYearOfEstablishment;

    @NotBlank
    @Size(max = 255)
    private String schoolLicenceNumber;

    @NotBlank
    private String schoolLicenceCertificateUrl;

    @NotBlank
    private String schoolLogoUrl;

    @NotBlank
    @Size(max = 255)
    private String schoolStreetAddress;

    @NotBlank
    @Size(max = 150)
    private String schoolCity;

    @NotBlank
    @Size(max = 150)
    private String schoolState;

    @NotBlank
    @Size(max = 100)
    private String schoolPostalCode;

    @NotBlank
    @Size(max = 150)
    private String schoolCountry;
}
