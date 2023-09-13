package com.abiorh.smartEdConnect.student.dto;

import com.abiorh.smartEdConnect.teacher.model.Teacher;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentTeacherDto {

    @Id
    private UUID id;

    @NotBlank(message = "please enter your first name")
    private String firstName;

    @NotBlank(message = "please enter your last name")
    private String lastName;

    private String email;

    private Teacher teacher;
}
