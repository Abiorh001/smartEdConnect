package com.abiorh.smartEdConnect.student.response;

import com.abiorh.smartEdConnect.student.model.Student;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat; // Import this

import java.time.ZonedDateTime;
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
