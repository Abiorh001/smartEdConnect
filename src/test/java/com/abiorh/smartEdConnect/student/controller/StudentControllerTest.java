package com.abiorh.smartEdConnect.student.controller;

import com.abiorh.smartEdConnect.student.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.student.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.model.Student;
import com.abiorh.smartEdConnect.student.response.StudentDto;
import com.abiorh.smartEdConnect.student.response.StudentFirstAndLastNameDto;
import com.abiorh.smartEdConnect.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() throws Exception {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
        closeable.close();
    }

    @Test
    public void saveNewStudent_ShouldReturnCreatedStatus() throws UniqueEmailException {
        // Arrange
        StudentDto studentDto = new StudentDto();
        when(studentService.saveNewStudent(studentDto)).thenReturn(studentDto);

        // Act
        ResponseEntity<StudentDto> response = studentController.saveNewStudent(studentDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void getStudentById_ShouldReturnStudentDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto studentDto = new StudentDto();
        try {
            when(studentService.getStudentById(id)).thenReturn(studentDto);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        ResponseEntity<StudentDto> response = studentController.getStudentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    public void getAllStudents_ShouldReturnListOfStudentDto() {
        // Arrange
        List<StudentDto> studentDtoList = new ArrayList<>();
        when(studentService.getAllStudents()).thenReturn(studentDtoList);

        // Act
        ResponseEntity<List<StudentDto>> response = studentController.getAllStudents();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDtoList, response.getBody());
    }

    @Test
    public void updatePartOfStudentById_ShouldReturnUpdatedStudentDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();
        StudentDto existingStudent = new StudentDto();
        try {
            when(studentService.updatePartOfStudentById(id, updatedStudent)).thenReturn(existingStudent);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        ResponseEntity<StudentDto> response = studentController.updatePartOfStudentById(id, updatedStudent);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(existingStudent, response.getBody());
    }

    @Test
    public void updateStudentById_ShouldReturnUpdatedStudentDto() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();
        StudentDto existingStudent = new StudentDto();
        try {
            when(studentService.updateStudentById(id, updatedStudent)).thenReturn(existingStudent);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        ResponseEntity<StudentDto> response = studentController.updateStudentById(id, updatedStudent);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(existingStudent, response.getBody());
    }

    @Test
    public void deleteStudentById_ShouldReturnNoContentStatus() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        ResponseEntity<Student> response = studentController.deleteStudentById(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        try {
            verify(studentService, times(1)).deleteStudentById(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void findStudentByEmail_ShouldReturnStudentDto() {
        // Arrange
        String email = "test@example.com";
        StudentDto studentDto = new StudentDto();
        try {
            when(studentService.findStudentByEmail(email)).thenReturn(studentDto);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        ResponseEntity<StudentDto> response = studentController.findStudentByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    public void findStudentsByFirstName_ShouldReturnListOfStudentDto() {
        // Arrange
        String firstName = "John";
        List<StudentDto> studentDtoList = new ArrayList<>();
        when(studentService.findStudentsByFirstName(firstName)).thenReturn(studentDtoList);

        // Act
        ResponseEntity<List<StudentDto>> response = studentController.findStudentsByFirstName(firstName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDtoList, response.getBody());
    }

    @Test
    public void findStudentsByFirstNameContaining_ShouldReturnListOfStudentDto() {
        // Arrange
        String name = "John";
        List<StudentDto> studentDtoList = new ArrayList<>();
        when(studentService.findStudentsByFirstNameContaining(name)).thenReturn(studentDtoList);

        // Act
        ResponseEntity<List<StudentDto>> response = studentController.findStudentsByFirstNameContaining(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDtoList, response.getBody());
    }

    @Test
    public void getStudentByEmailAddress_ShouldReturnStudentFirstAndLastNameDto() {
        // Arrange
        String email = "test@example.com";
        StudentFirstAndLastNameDto studentDto = new StudentFirstAndLastNameDto();
        try {
            when(studentService.getStudentByEmailAddress(email)).thenReturn(studentDto);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Act
        ResponseEntity<StudentFirstAndLastNameDto> response = studentController.getStudentByEmailAddress(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDto, response.getBody());
    }

    @Test
    public void findALlStudentsByPageAndSize_ShouldReturnPageOfStudentDto() {
        // Arrange
        int page = 0;
        int size = 10;
        Page<StudentDto> studentDtoPage = mock(Page.class);
        when(studentService.findALlStudentsByPageAndSize(page, size)).thenReturn(studentDtoPage);

        // Act
        ResponseEntity<Page<StudentDto>> response = studentController.findALlStudentsByPageAndSize(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentDtoPage, response.getBody());
    }
}
