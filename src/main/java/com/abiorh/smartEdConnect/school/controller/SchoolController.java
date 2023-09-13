package com.abiorh.smartEdConnect.school.controller;

import com.abiorh.smartEdConnect.globalConfig.customException.*;
import com.abiorh.smartEdConnect.school.dto.SchoolDto;
import com.abiorh.smartEdConnect.school.dto.SchoolUpdateDto;
import com.abiorh.smartEdConnect.school.service.SchoolService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("smartedconnect.io/api/v1/")
@Slf4j
public class SchoolController {

    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }


    @PostMapping("/schools")
    public ResponseEntity<?> saveNewSchool(@Valid @RequestBody SchoolDto schoolDto){
        log.info("adding a new school to the database");

        try {
            SchoolDto newSchoolDto = schoolService.saveNewSchool(schoolDto);
            return  ResponseEntity.status(HttpStatus.CREATED).body(newSchoolDto);
        } catch (UniqueEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage()));
        } catch (UniqueSchoolLicenceNumberException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (UniqueSchoolNameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }
    @GetMapping("/schools/{schoolId}")
    public ResponseEntity<?> getSchoolBySchoolId(@PathVariable("schoolId") UUID schoolId){
        log.info("retrieve school information from database using it's schoolId");

        try {
            SchoolDto existingSchoolDto = schoolService.getSchoolBySchoolId(schoolId);
            return ResponseEntity.status(HttpStatus.OK).body(existingSchoolDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @GetMapping("/{schoolName}")
    public ResponseEntity<?> getSchoolBySchoolName(@PathVariable("schoolName") String schoolName){
        log.info("retrieve school information from database using it's schoolName");

        try {
            SchoolDto schoolDto = schoolService.getSchoolBySchoolName(schoolName);
            return ResponseEntity.status(HttpStatus.OK).body(schoolDto);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }


    @PutMapping("/schools/{schoolId}")
    public ResponseEntity<?> updatePartOfSchoolBySchoolId
            (@PathVariable("schoolId") UUID schoolId,
             @Valid @RequestBody SchoolUpdateDto schoolUpdateDto){
        log.info("updating some fields in school using the schoolId");

        try {
            SchoolUpdateDto existingSchoolDto = schoolService.updatePartOfSchoolBySchoolId(schoolId, schoolUpdateDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(existingSchoolDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }


    @DeleteMapping("/schools/{schoolId}")
    public ResponseEntity<?> deleteSchoolBySchoolId(@PathVariable("schoolId") UUID schoolId){
        log.info("Delete school information from the database using schoolId");

        try {
            schoolService.deleteSchoolBySchoolId(schoolId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @GetMapping("/schools")
    public ResponseEntity<List<?>> findAllSchools(){
        log.info("List all schools in the database");

        List<SchoolDto> schoolDtoList = schoolService.findAllSchools();
        return ResponseEntity.status(HttpStatus.OK).body(schoolDtoList);
    }
}
