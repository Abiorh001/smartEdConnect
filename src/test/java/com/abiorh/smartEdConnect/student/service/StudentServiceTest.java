package com.abiorh.smartEdConnect.student.service;


import com.abiorh.smartEdConnect.student.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.student.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.model.Student;
import com.abiorh.smartEdConnect.student.repository.StudentRepository;
import com.abiorh.smartEdConnect.student.response.StudentDto;
import com.abiorh.smartEdConnect.student.response.StudentFirstAndLastNameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() throws Exception{
       AutoCloseable closeable = MockitoAnnotations.openMocks(this);
       closeable.close();
        studentService = new StudentService(studentRepository, modelMapper);
    }

    @Test
    void saveNewStudent_WithNonExistingEmail_ShouldReturnStudentDto() throws UniqueEmailException {
        // Arrange
        Student studentToSave = new Student();
        when(studentRepository.findByEmail(anyString())).thenReturn(null);
        when(modelMapper.map(any(StudentDto.class), eq(Student.class))).thenReturn(studentToSave);
        when(studentRepository.save(studentToSave)).thenReturn(studentToSave);
        when(modelMapper.map(studentToSave, StudentDto.class)).thenReturn(new StudentDto());

        // Act
        StudentDto savedStudent = studentService.saveNewStudent(new StudentDto());

        // Assert
        assertNotNull(savedStudent);
    }

    @Test
    void saveNewStudent_WithExistingEmail_ShouldThrowUniqueEmailException() {
        // Arrange
        String existingEmail = "existing@example.com";
        StudentDto studentDto = new StudentDto();
        studentDto.setEmail(existingEmail);

        when(studentRepository.findByEmail(existingEmail)).thenReturn(new Student()); // Mock an existing student with the same email

        // Act & Assert
        assertThrows(UniqueEmailException.class, () -> studentService.saveNewStudent(studentDto));
    }


    @Test
    void getStudentById_WithExistingId_ShouldReturnStudentDto() throws ResourceNotFoundException {
        // Arrange
        UUID id = UUID.randomUUID();
        Student student = new Student();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentDto.class)).thenReturn(new StudentDto());

        // Act
        StudentDto result = studentService.getStudentById(id);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getStudentById_WithNonExistingId_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(id));
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudentDto() {
        // Arrange
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentRepository.findAll()).thenReturn(students);
        when(modelMapper.map(any(Student.class), eq(StudentDto.class))).thenReturn(new StudentDto());

        // Act
        List<StudentDto> result = studentService.getAllStudents();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(students.size(), result.size());
    }

    @Test
    void updatePartOfStudentById_WithExistingId_ShouldReturnUpdatedStudentDto() throws ResourceNotFoundException {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();
        updatedStudent.setFirstName("UpdatedFirstName");
        updatedStudent.setLastName("UpdatedLastName");
        Student existingStudent = new Student();
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);
        when(modelMapper.map(existingStudent, StudentDto.class)).thenReturn(updatedStudent); // Corrected mapping

        // Act
        StudentDto result = studentService.updatePartOfStudentById(id, updatedStudent);

        // Assert
        assertNotNull(result);
        assertEquals(updatedStudent.getFirstName(), result.getFirstName());
        assertEquals(updatedStudent.getLastName(), result.getLastName());
    }


    @Test
    void updatePartOfStudentById_WithNonExistingId_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.updatePartOfStudentById(id, updatedStudent));
    }

    @Test
    void updateStudentById_WithExistingId_ShouldReturnUpdatedStudentDto() throws ResourceNotFoundException {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();
        Student existingStudent = new Student();
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);
        when(modelMapper.map(existingStudent, StudentDto.class)).thenReturn(new StudentDto());

        // Act
        StudentDto result = studentService.updateStudentById(id, updatedStudent);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateStudentById_WithNonExistingId_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentDto updatedStudent = new StudentDto();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.updateStudentById(id, updatedStudent));
    }

    @Test
    void deleteStudentById_WithExistingId_ShouldDeleteStudent() throws ResourceNotFoundException {
        // Arrange
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.of(new Student()));

        // Act
        studentService.deleteStudentById(id);

        // Assert
        verify(studentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteStudentById_WithNonExistingId_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudentById(id));
    }

    @Test
    void findStudentByEmail_WithExistingEmail_ShouldReturnStudentDto() throws ResourceNotFoundException {
        // Arrange
        String email = "test@example.com";
        when(studentRepository.findByEmail(email)).thenReturn(new Student());
        when(modelMapper.map(any(Student.class), eq(StudentDto.class))).thenReturn(new StudentDto());

        // Act
        StudentDto result = studentService.findStudentByEmail(email);

        // Assert
        assertNotNull(result);
    }

    @Test
    void findStudentByEmail_WithNonExistingEmail_ShouldThrowResourceNotFoundException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(studentRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.findStudentByEmail(email));
    }

    @Test
    void findStudentsByFirstName_WithExistingFirstName_ShouldReturnListOfStudentDto() {
        // Arrange
        String firstName = "John";
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentRepository.findStudentsByFirstName(firstName)).thenReturn(students);
        when(modelMapper.map(any(Student.class), eq(StudentDto.class))).thenReturn(new StudentDto());

        // Act
        List<StudentDto> result = studentService.findStudentsByFirstName(firstName);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(students.size(), result.size());
    }

    @Test
    void findStudentsByFirstName_WithNonExistingFirstName_ShouldReturnEmptyList() {
        // Arrange
        String firstName = "Nonexistent";
        when(studentRepository.findStudentsByFirstName(firstName)).thenReturn(new ArrayList<>());

        // Act
        List<StudentDto> result = studentService.findStudentsByFirstName(firstName);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findStudentsByFirstNameContaining_WithExistingName_ShouldReturnListOfStudentDto() {
        // Arrange
        String name = "John";
        List<Student> students = Arrays.asList(new Student(), new Student());
        when(studentRepository.findStudentsByFirstNameContaining(name)).thenReturn(students);
        when(modelMapper.map(any(Student.class), eq(StudentDto.class))).thenReturn(new StudentDto());

        // Act
        List<StudentDto> result = studentService.findStudentsByFirstNameContaining(name);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(students.size(), result.size());
    }

    @Test
    void findStudentsByFirstNameContaining_WithNonExistingName_ShouldReturnEmptyList() {
        // Arrange
        String name = "Nonexistent";
        when(studentRepository.findStudentsByFirstNameContaining(name)).thenReturn(new ArrayList<>());

        // Act
        List<StudentDto> result = studentService.findStudentsByFirstNameContaining(name);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getStudentByEmailAddress_WithExistingEmail_ShouldReturnStudentFirstAndLastNameDto() throws ResourceNotFoundException {
        // Arrange
        String email = "test@example.com";
        List<Object[]> result = new ArrayList<>();
        result.add(new Object[]{"John", "Doe"});
        when(studentRepository.getFirstNameAndLastNameByEmailAddress(email)).thenReturn(result);

        // Act
        StudentFirstAndLastNameDto dto = studentService.getStudentByEmailAddress(email);

        // Assert
        assertNotNull(dto);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    void getStudentByEmailAddress_WithNonExistingEmail_ShouldThrowResourceNotFoundException() {
        // Arrange
        String email = "nonexistent@example.com";
        when(studentRepository.getFirstNameAndLastNameByEmailAddress(email)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentByEmailAddress(email));
    }

    @Test
    void findALlStudentsByPageAndSize_ShouldReturnPageOfStudentDto() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Student> students = Arrays.asList(new Student(), new Student());
        Page<Student> studentPage = new PageImpl<>(students, pageable, students.size());
        when(studentRepository.findAll(pageable)).thenReturn(studentPage);
        when(modelMapper.map(any(Student.class), eq(StudentDto.class))).thenReturn(new StudentDto());

        // Act
        Page<StudentDto> result = studentService.findALlStudentsByPageAndSize(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(students.size(), result.getContent().size());
    }
}
