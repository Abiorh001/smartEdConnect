package com.abiorh.smartEdConnect.student.service;

import com.abiorh.smartEdConnect.student.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.student.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.model.Student;
import com.abiorh.smartEdConnect.student.repository.StudentRepository;
import com.abiorh.smartEdConnect.student.response.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


    public StudentDto saveNewStudent(StudentDto studentDto){

        if(studentRepository.findByEmail(studentDto.getEmail()) != null) {
            try {
                throw new UniqueEmailException("The email address is already in use.");
            } catch (UniqueEmailException e) {
                throw new RuntimeException(e);
            }
        }

        Student student = modelMapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDto.class);

    }

    public StudentDto getStudentById(UUID id) {

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return modelMapper.map(student, StudentDto.class);
        } else {
            try {
                throw new ResourceNotFoundException("Student not found with id: " + id);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public List<StudentDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }


    public StudentDto updatePartOfStudentById(UUID id, StudentDto updatedStudent){

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            Student updatedEntity = studentRepository.save(existingStudent);
            return modelMapper.map(updatedEntity, StudentDto.class);
        } else {
            try {
                throw new ResourceNotFoundException("Student not found with id: " + id);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public StudentDto updateStudentById(UUID id, StudentDto updatedStudent){

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student existingStudent = student.get();
            modelMapper.map(updatedStudent, existingStudent);
            Student updatedEntity = studentRepository.save(existingStudent);
            return modelMapper.map(updatedEntity, StudentDto.class);
        } else {
            try {
                throw new ResourceNotFoundException("Student not found with id: " + id);
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }







}
