package com.abiorh.smartEdConnect.student.controller;


import com.abiorh.smartEdConnect.globalConfig.customException.ErrorResponse;
import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.entity.Student;
import com.abiorh.smartEdConnect.student.dto.StudentDto;
import com.abiorh.smartEdConnect.student.dto.StudentFirstAndLastNameDto;
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
@RequestMapping("/api/v1/smartedconnect.io")
@CrossOrigin(origins = {"*"})
@Slf4j
public class StudentController
{
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }




    @PostMapping("/students")
    public ResponseEntity<?> saveNewStudent(@Valid @RequestBody StudentDto studentDto){

        log.info("save new student to the database");
        try {
            StudentDto newStudentDto = studentService.saveNewStudent(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStudentDto);
        } catch (UniqueEmailException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD REQUEST", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }

    }


    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {

        try {
            StudentDto studentDto = studentService.getStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(studentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }


    @GetMapping("/students")
    public ResponseEntity<List<?>> getAllStudents() {
        List<StudentDto> studentDtoList = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentDtoList);
    }


    @PatchMapping("/students/{id}")
    public ResponseEntity<?> updatePartOfStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        try {
            StudentDto studentDto = studentService.updatePartOfStudentById(id, updatedStudent);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }


    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentById(
            @PathVariable("id") UUID id, @RequestBody StudentDto updatedStudent){

        try {
            StudentDto studentDto = studentService.updateStudentById(id, updatedStudent);
            return new ResponseEntity<>(studentDto, HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("id") UUID id){

        try {
            studentService.deleteStudentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @GetMapping("/students/email/{email}")
    public ResponseEntity<?> findStudentByEmail(@PathVariable("email") String email){

        try {
           StudentDto studentDto = studentService.findStudentByEmail(email);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @GetMapping("/students/first_name/{firstName}")
    public ResponseEntity<List<?>> findStudentsByFirstName(@PathVariable("firstName") String firstName){

        List<StudentDto> studentDtoList = studentService.findStudentsByFirstName(firstName);
        return  ResponseEntity.status(HttpStatus.OK).body(studentDtoList);
    }

    @GetMapping("/students/containing_first_name/{name}")
    public ResponseEntity<List<?>> findStudentsByFirstNameContaining(@PathVariable("name") String name){

        List<StudentDto> studentDtoList = studentService.findStudentsByFirstNameContaining(name);
        return  ResponseEntity.status(HttpStatus.OK).body(studentDtoList);
    }

    @GetMapping("/students/email_address/{email}")
    public ResponseEntity<?> getStudentByEmailAddress(@PathVariable("email") String email){

        log.info("get a student first and last name using their email address");
        try {
          StudentFirstAndLastNameDto  studentFirstAndLastNameDto = studentService.getStudentByEmailAddress(email);
          return  ResponseEntity.status(HttpStatus.OK).body(studentFirstAndLastNameDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT FOUND", e.getMessage()));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server down!!! please try again later");
        }
    }

    @GetMapping("/students/by_page_and_size")
    public ResponseEntity<Page<?>> findALlStudentsByPageAndSize(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Page<StudentDto> studentDtoPage = studentService.findALlStudentsByPageAndSize(page, size);
        return  ResponseEntity.status(HttpStatus.OK).body(studentDtoPage);
    }


}
