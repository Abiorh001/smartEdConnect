package com.abiorh.smartEdConnect.student.service;

import com.abiorh.smartEdConnect.student.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.student.model.Student;
import com.abiorh.smartEdConnect.student.model.StudentProfile;
import com.abiorh.smartEdConnect.student.repository.StudentProfileRepository;
import com.abiorh.smartEdConnect.student.response.StudentAndStudentProfileDto;
import com.abiorh.smartEdConnect.student.response.StudentDto;
import com.abiorh.smartEdConnect.student.response.StudentProfileDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentProfileService {

    private final StudentService studentService;
    private final StudentProfileRepository studentProfileRepository;

    private final ModelMapper modelMapper;


    public StudentProfileService(
            StudentProfileRepository studentProfileRepository,
            ModelMapper modelMapper,
            StudentService studentService) {
        this.studentProfileRepository = studentProfileRepository;
        this.modelMapper = modelMapper;
        this.studentService = studentService;
    }

    public StudentProfileDto saveNewStudentProfile
            (UUID studentId, StudentProfileDto studentProfileDto) throws ResourceNotFoundException{

        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto != null){
            Student student = modelMapper.map(studentDto, Student.class);
            StudentProfile studentProfile = modelMapper.map(studentProfileDto, StudentProfile.class);

            studentProfile.setStudent(student);
            StudentProfile savedStudentProfile = studentProfileRepository.save(studentProfile);
            return modelMapper.map(savedStudentProfile, StudentProfileDto.class);
        } else
            throw new ResourceNotFoundException("Student is not found with id: " + studentId);
    }


    public StudentProfileDto getStudentProfile(UUID id) throws ResourceNotFoundException{

        Optional<StudentProfile> optionalStudentProfile = studentProfileRepository.findById(id);
        if(optionalStudentProfile.isPresent()){
            StudentProfile studentProfile = optionalStudentProfile.get();

            return modelMapper.map(studentProfile, StudentProfileDto.class);
        } else
            throw new ResourceNotFoundException("Student profile is not found with id " + id);
    }





}
