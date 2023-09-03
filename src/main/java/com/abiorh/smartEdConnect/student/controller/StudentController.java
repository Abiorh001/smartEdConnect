package com.abiorh.smartEdConnect.student.controller;


import com.abiorh.smartEdConnect.student.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.student.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.model.Student;
import com.abiorh.smartEdConnect.student.response.StudentDto;
import com.abiorh.smartEdConnect.student.response.StudentFirstAndLastNameDto;
import com.abiorh.smartEdConnect.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/students")
@CrossOrigin(origins = {"*"})
@Slf4j
public class StudentController
{
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }




    @PostMapping()
    public ResponseEntity<StudentDto> saveNewStudent(@Valid @RequestBody StudentDto studentDto){

        log.info("save new student to the database");
        try {
            StudentDto newStudentDto = studentService.saveNewStudent(studentDto);
            return new ResponseEntity<>(newStudentDto, HttpStatus.CREATED);
        } catch (UniqueEmailException e){
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable UUID id) {

        try {
            StudentDto studentDto = studentService.getStudentById(id);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtoList = studentService.getAllStudents();
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<StudentDto> updatePartOfStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        try {
            StudentDto studentDto = studentService.updatePartOfStudentById(id, updatedStudent);
            return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        try {
            StudentDto studentDto = studentService.updateStudentById(id, updatedStudent);
            return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudentById(@PathVariable("id") UUID id){

        try {
            studentService.deleteStudentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentDto> findStudentByEmail(@PathVariable("email") String email){

        try {
           StudentDto studentDto = studentService.findStudentByEmail(email);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/first_name/{firstName}")
    public ResponseEntity<List<StudentDto>> findStudentsByFirstName(@PathVariable("firstName") String firstName){

        List<StudentDto> studentDtoList = studentService.findStudentsByFirstName(firstName);
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }

    @GetMapping("/containing_first_name/{name}")
    public ResponseEntity<List<StudentDto>> findStudentsByFirstNameContaining(@PathVariable("name") String name){

        List<StudentDto> studentDtoList = studentService.findStudentsByFirstNameContaining(name);
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }

    @GetMapping("/email_address/{email}")
    public ResponseEntity<StudentFirstAndLastNameDto> getStudentByEmailAddress(@PathVariable("email") String email){

        log.info("get a student first and last name using their email address");
        try {
          StudentFirstAndLastNameDto  studentFirstAndLastNameDto = studentService.getStudentByEmailAddress(email);
          return new ResponseEntity<>(studentFirstAndLastNameDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by_page_and_size")
    public ResponseEntity<Page<StudentDto>> findALlStudentsByPageAndSize(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Page<StudentDto> studentDtoPage = studentService.findALlStudentsByPageAndSize(page, size);
        return new ResponseEntity<>(studentDtoPage, HttpStatus.OK);
    }


}
