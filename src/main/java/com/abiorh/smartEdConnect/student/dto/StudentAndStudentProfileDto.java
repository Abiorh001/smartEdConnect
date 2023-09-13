package com.abiorh.smartEdConnect.student.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAndStudentProfileDto {


    private StudentDto studentDto;

    private StudentProfileDto studentProfileDto;
}
