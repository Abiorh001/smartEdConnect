package com.abiorh.smartEdConnect.student.repository;

import com.abiorh.smartEdConnect.student.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private  StudentRepository studentRepository;
    private Student student;


    @BeforeEach
    void setUp() {

        student = Student.builder()
                .firstName("adedayo")
                .lastName("mary")
                .email("lifeisgood@gmail.com")
                .build();
    }
    @Test
    @DisplayName("Junit test to save a new student")
    public void saveNewStudent(){
        Student newStudent = Student.builder()
                            .firstName("olamide")
                            .lastName("monday")
                            .email("good@gmail.com")
                            .build();

        Student saveStudent = studentRepository.save(newStudent);

        assertNotNull(saveStudent);
        assertEquals("olamide", saveStudent.getFirstName());
        assertEquals("monday", saveStudent.getLastName());
    }

    @Test
    public void getAllStudents(){
        Student newStudent1 = Student.builder()
                .firstName("saheed")
                .lastName("ishola")
                .email("godisgood@gmail.com")
                .build();

        studentRepository.save(student);
        studentRepository.save(newStudent1);

        List<Student> studentList = studentRepository.findAll();

        assertFalse(studentList.isEmpty());
        assertTrue(studentList.size() > 1);
    }

    @Test
    public void getStudentById(){
        studentRepository.save(student);
        Optional<Student> getStudent = studentRepository.findById(student.getId());

        assertTrue(getStudent.isPresent());
    }

    @Test
    public void updateStudentById(){
        studentRepository.save(student);
        Student savedStudent = studentRepository.findById(student.getId()).get();
        savedStudent.setFirstName("malik");
        savedStudent.setLastName("zahra");

        assertEquals("malik", savedStudent.getFirstName());
        assertEquals("zahra", savedStudent.getLastName());
    }

    @Test
    public void deleteStudentById(){
        studentRepository.save(student);
        studentRepository.deleteById(student.getId());
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());

        assertTrue(optionalStudent.isEmpty());
    }

    @Test
    void findByEmail() {
        studentRepository.save(student);
        Student getStudent = studentRepository.findByEmail(student.getEmail());

        assertEquals("adedayo", getStudent.getFirstName());
    }

    @Test
    void findStudentsByFirstName() {
        Student student1 = Student.builder()
                            .firstName("adedayo")
                            .lastName("ajani")
                            .email("lifegood@gmail.com")
                            .build();

        studentRepository.save(student);
        studentRepository.save(student1);
        List<Student> studentList = studentRepository.findStudentsByFirstName("adedayo");

        assertFalse(studentList.isEmpty());
        assertTrue(studentList.size() > 1);
    }

    @Test
    void findStudentsByFirstNameContaining() {
        Student student1 = Student.builder()
                        .firstName("adedayo")
                        .lastName("ajani")
                        .email("lifegood@gmail.com")
                        .build();

        studentRepository.save(student);
        studentRepository.save(student1);
        List<Student> studentList = studentRepository.findStudentsByFirstNameContaining("ad");

        assertFalse(studentList.isEmpty());
        assertTrue(studentList.size() > 1);

    }

    @Test
    void getFirstNameAndLastNameByEmailAddress() {

        studentRepository.save(student);
        List<Object[]> getStudent = studentRepository.getFirstNameAndLastNameByEmailAddress("lifeisgood@gmail.com");

        assertFalse(getStudent.isEmpty());
        assertEquals("adedayo", getStudent.get(0)[0]);
        assertEquals("mary", getStudent.get(0)[1]);
    }

    @Test
    void findAll() {
        Student student1 = Student.builder()
                .firstName("adedayo101")
                .lastName("aja")
                .email("lifegood123@gmail.com")
                .build();

        Student student2 = Student.builder()
                .firstName("ajadi")
                .lastName("habbeb")
                .email("none@gmail.com")
                .build();

        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);

        int page = 0;
        int size = 10;
        Page<Student> studentPage = studentRepository.findAll(PageRequest.of(page, size));

        assertFalse(studentPage.isEmpty());
        assertTrue(studentPage.hasContent());
        assertEquals(3, studentPage.getContent().size());

    }
}