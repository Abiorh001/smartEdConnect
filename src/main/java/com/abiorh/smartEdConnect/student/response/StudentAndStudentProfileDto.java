package com.abiorh.smartEdConnect.student.response;

import com.abiorh.smartEdConnect.student.model.StudentProfile;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAndStudentProfileDto {


    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private StudentProfileDto studentProfileDto;
}
