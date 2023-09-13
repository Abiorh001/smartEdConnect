package com.abiorh.smartEdConnect.student.service;

import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UnauthorizedAccessException;
import com.abiorh.smartEdConnect.student.entity.Student;
import com.abiorh.smartEdConnect.student.entity.StudentProfile;
import com.abiorh.smartEdConnect.student.repository.StudentProfileRepository;
import com.abiorh.smartEdConnect.student.dto.StudentAndStudentProfileDto;
import com.abiorh.smartEdConnect.student.dto.StudentDto;
import com.abiorh.smartEdConnect.student.dto.StudentProfileDto;
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


    public StudentAndStudentProfileDto getStudentWithStudentProfile
            (UUID studentId, UUID id) throws ResourceNotFoundException, UnauthorizedAccessException {

        StudentDto studentDto = studentService.getStudentById(studentId);
        if(studentDto == null){
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        Student student = modelMapper.map(studentDto, Student.class);

        Optional<StudentProfile> optionalStudentProfile= studentProfileRepository.findById(id);
        if(optionalStudentProfile.isEmpty()){
            throw new ResourceNotFoundException("Student profile not found with id: " + id);
        }
        StudentProfile studentProfile = optionalStudentProfile.get();
        if(!student.getId().equals(studentProfile.getStudent().getId())){
            throw new UnauthorizedAccessException("Unauthorized access to student profile.");
        }
        StudentProfileDto studentProfileDto = modelMapper.map(studentProfile, StudentProfileDto.class);
        StudentAndStudentProfileDto studentAndStudentProfileDto = new StudentAndStudentProfileDto();
        studentAndStudentProfileDto.setStudentDto(studentDto);
        studentAndStudentProfileDto.setStudentProfileDto(studentProfileDto);
        return studentAndStudentProfileDto;

    }


}
