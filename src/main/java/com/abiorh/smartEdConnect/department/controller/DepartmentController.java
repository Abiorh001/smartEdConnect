package com.abiorh.smartEdConnect.department.controller;


import com.abiorh.smartEdConnect.department.dto.DepartmentDto;
import com.abiorh.smartEdConnect.department.service.DepartmentService;
import com.abiorh.smartEdConnect.globalConfig.customException.ErrorResponse;
import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UnauthorizedAccessException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueDepartmentNameException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/smartedconnect.io")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @PostMapping("/schools/{schoolId}/departments")
    public ResponseEntity<?> saveNewDepartment(
            @Valid @RequestBody DepartmentDto departmentDto,
            @PathVariable("schoolId") UUID schoolId){
        log.info("add a new department name to the database");

        try {
            DepartmentDto newDepartment = departmentService.saveNewDepartment(departmentDto, schoolId);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDepartment);
        } catch (UniqueDepartmentNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server down!!! please try again later");
        }
    }


    @GetMapping("/schools/{schoolId}/departments/{departmentId}")
    public ResponseEntity<?> findDepartmentByDepartmentId(
            @PathVariable("schoolId") UUID schoolId,
            @PathVariable("departmentId") UUID departmentId){
        log.info("find a department by using departmentId");

        try {
            DepartmentDto departmentDto = departmentService.findDepartmentByDepartmentId(schoolId, departmentId);
            return ResponseEntity.status(HttpStatus.OK).body(departmentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (UnauthorizedAccessException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("FORBIDDEN", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server down!!! please try again later");
        }
    }


    @PutMapping("/schools/{schoolId}/departments/{departmentId}")
    public ResponseEntity<?> updateDepartmentByDepartmentId(
            @PathVariable("schoolId") UUID schoolId,
            @PathVariable("departmentId") UUID departmentId,
            @Valid @RequestBody DepartmentDto updatedDepartmentDto) {
        log.info("Updating a department name by using departmentId");

        try {
            DepartmentDto updateDepartmentDto = departmentService.updateDepartmentByDepartmentId(
                    schoolId,
                    departmentId,
                    updatedDepartmentDto
            );
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateDepartmentDto);
        } catch (UniqueDepartmentNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage()));
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("FORBIDDEN", e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }




    @GetMapping("/schools/{schoolId}/departments")
    public ResponseEntity<?> findAllDepartments(@PathVariable("schoolId") UUID schoolId){
        log.info("find all departments");

        try{List<DepartmentDto> departmentDtoList = departmentService.findAllDepartments(schoolId);
            return ResponseEntity.status(HttpStatus.OK).body(departmentDtoList);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server down!!! please try again later");
        }
    }

    @DeleteMapping("/schools/{schoolId}/departments/{departmentId}")
    public ResponseEntity<?> deleteDepartmentByDepartmentId(
            @PathVariable("schoolId") UUID schoolId,
            @PathVariable("departmentId") UUID departmentId) {
        log.info("delete department by using departmentId");

        try {
            departmentService.deleteDepartmentByDepartmentId(schoolId, departmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("FORBIDDEN", e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server down!!! please try again later");
        }
    }
}
