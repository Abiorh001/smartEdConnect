package com.abiorh.smartEdConnect.student.controller;


import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UnauthorizedAccessException;
import com.abiorh.smartEdConnect.student.dto.StudentAndStudentProfileDto;
import com.abiorh.smartEdConnect.student.dto.StudentProfileDto;
import com.abiorh.smartEdConnect.student.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/smartedconnect.io")
@CrossOrigin(origins = {"*"})
@Slf4j

public class StudentProfileController {


    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping("/students/{studentId}/profile")
    public ResponseEntity<StudentProfileDto> saveNewStudentProfile(
            @PathVariable("studentId") UUID studentId,
            @Valid @RequestBody StudentProfileDto studentProfileDto)
    {
        try {
            StudentProfileDto savedStudentProfile = studentProfileService.saveNewStudentProfile(
                                                    studentId,studentProfileDto);
            return new ResponseEntity<>(savedStudentProfile, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/students/profile/{id}")
    public ResponseEntity<StudentProfileDto> getStudentProfile(@PathVariable("id") UUID id){

        try {
           StudentProfileDto studentProfileDto = studentProfileService.getStudentProfile(id);
            return new ResponseEntity<>(studentProfileDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{studentId}/profile/{id}")
    public ResponseEntity<StudentAndStudentProfileDto> getStudentWithStudentProfile
            (@PathVariable("studentId") UUID studentId,
             @PathVariable("id") UUID id){

        try {
            StudentAndStudentProfileDto studentAndStudentProfileDto =
                    studentProfileService.getStudentWithStudentProfile(studentId, id);
            return new ResponseEntity<>(studentAndStudentProfileDto, HttpStatus.OK);
        } catch (ResourceNotFoundException | UnauthorizedAccessException e){
            throw new RuntimeException(e);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
