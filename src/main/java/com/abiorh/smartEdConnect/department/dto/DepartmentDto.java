package com.abiorh.smartEdConnect.department.dto;

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
public class DepartmentDto {

    private UUID departmentId;
    @NotBlank(message = "please enter a valid department name")
    private String departmentName;

}
