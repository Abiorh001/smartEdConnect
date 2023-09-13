package com.abiorh.smartEdConnect.student.service;

import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.student.entity.Student;
import com.abiorh.smartEdConnect.student.repository.StudentRepository;
import com.abiorh.smartEdConnect.student.dto.StudentDto;
import com.abiorh.smartEdConnect.student.dto.StudentFirstAndLastNameDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public StudentDto saveNewStudent(StudentDto studentDto) throws UniqueEmailException{

        if(studentRepository.findByEmail(studentDto.getEmail()) != null) {
            throw new UniqueEmailException("The email address is already in use.");
        }

        Student student = modelMapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDto.class);

    }

    public StudentDto getStudentById(UUID id) throws ResourceNotFoundException{

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return modelMapper.map(student, StudentDto.class);
        } else
            throw new ResourceNotFoundException("Student not found with id: " + id);
    }


    public List<StudentDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }


    public StudentDto updatePartOfStudentById(UUID id, StudentDto updatedStudent) throws ResourceNotFoundException{

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            Student updatedEntity = studentRepository.save(existingStudent);
            return modelMapper.map(updatedEntity, StudentDto.class);
        } else
            throw new ResourceNotFoundException("Student not found with id: " + id);

    }

    public StudentDto updateStudentById(UUID id, StudentDto updatedStudent) throws ResourceNotFoundException{

        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student existingStudent = student.get();
            modelMapper.map(updatedStudent, existingStudent);
            Student updatedEntity = studentRepository.save(existingStudent);
            return modelMapper.map(updatedEntity, StudentDto.class);
        } else
            throw new ResourceNotFoundException("Student not found with id: " + id);
    }



    public void deleteStudentById(UUID id) throws ResourceNotFoundException {

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            studentRepository.deleteById(id);
        } else
            throw new ResourceNotFoundException("Student not found with id: " + id);
    }

    public StudentDto findStudentByEmail(String email) throws ResourceNotFoundException{

        Student student = studentRepository.findByEmail(email);
        if(student != null){
            return modelMapper.map(student, StudentDto.class);
        } else
            throw new ResourceNotFoundException("Student not found with email address: " + email);
    }

    public List<StudentDto> findStudentsByFirstName(String firstName){

        List<Student> students = studentRepository.findStudentsByFirstName(firstName);
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }


    public List<StudentDto> findStudentsByFirstNameContaining(String name){

        List<Student> students = studentRepository.findStudentsByFirstNameContaining(name);
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }


    public StudentFirstAndLastNameDto getStudentByEmailAddress(String email)throws ResourceNotFoundException {

        List<Object[]> result = studentRepository.getFirstNameAndLastNameByEmailAddress(email);
        if (!result.isEmpty()) {
            Object[] data = result.get(0);
            String firstName = (String) data[0];
            String lastName = (String) data[1];
            return new StudentFirstAndLastNameDto(firstName, lastName);

        } else
            throw new ResourceNotFoundException("Student not found with email address: " + email);
    }


    public Page<StudentDto> findALlStudentsByPageAndSize(int page, int size){

        Pageable studentsPageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.findAll(studentsPageable);

        List<StudentDto> studentDtos = studentsPage.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
        return new PageImpl<>(studentDtos, studentsPageable, studentsPage.getTotalElements());
    }




}
