package com.abiorh.smartEdConnect.student.controller;


import com.abiorh.smartEdConnect.student.response.StudentDto;
import com.abiorh.smartEdConnect.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/students")
public class StudentController
{
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }




    @PostMapping()
    public ResponseEntity<StudentDto> saveNewStudent(@Valid @RequestBody StudentDto studentDto){

        StudentDto newStudentDto = studentService.saveNewStudent(studentDto);
        return new ResponseEntity<>(newStudentDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable UUID id) {

        StudentDto studentDto = studentService.getStudentById(id);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtoList = studentService.getAllStudents();
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<StudentDto> updatePartOfStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        StudentDto studentDto = studentService.updatePartOfStudentById(id, updatedStudent);
        return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        StudentDto studentDto = studentService.updateStudentById(id, updatedStudent);
        return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
    }


}
