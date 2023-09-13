package com.abiorh.smartEdConnect.school.service;

import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueEmailException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueSchoolLicenceNumberException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueSchoolNameException;
import com.abiorh.smartEdConnect.school.dto.SchoolDto;
import com.abiorh.smartEdConnect.school.dto.SchoolUpdateDto;
import com.abiorh.smartEdConnect.school.entity.School;
import com.abiorh.smartEdConnect.school.repository.SchoolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    private SchoolRepository schoolRepository;
    private ModelMapper modelMapper;

    public SchoolService(SchoolRepository schoolRepository, ModelMapper modelMapper)
    {   this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
    }


    public SchoolDto saveNewSchool(SchoolDto schoolDto)
            throws UniqueEmailException, UniqueSchoolNameException, UniqueSchoolLicenceNumberException {

        if(schoolRepository.findSchoolBySchoolEmailAddress(schoolDto.getSchoolEmailAddress()).isPresent()){

            throw new UniqueEmailException("email address has been used");
        }

        if(schoolRepository.findSchoolBySchoolName(schoolDto.getSchoolName()).isPresent()){
            throw new UniqueSchoolNameException("School name has been registered before!");
        }

        if(schoolRepository
                .findSchoolBySchoolLicenceNumber(schoolDto.getSchoolLicenceNumber()).isPresent()){
            throw new UniqueSchoolLicenceNumberException("School licence number has been registered before!");
        }
        School school = modelMapper.map(schoolDto, School.class);
        School newSchool = schoolRepository.save(school);

        return modelMapper.map(newSchool, SchoolDto.class);
    }

    public SchoolDto getSchoolBySchoolId(UUID schoolId) throws ResourceNotFoundException {

        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if(optionalSchool.isEmpty()){
            throw new ResourceNotFoundException("school is not found with the schoolId: " + schoolId);
        }
        School existingSchool = optionalSchool.get();
        return modelMapper.map(existingSchool, SchoolDto.class);
    }


    public SchoolDto getSchoolBySchoolName(String schoolName) throws ResourceNotFoundException{

        Optional<School> optionalSchool = schoolRepository.findSchoolBySchoolName(schoolName);
        if(optionalSchool.isEmpty()){
            throw new ResourceNotFoundException("School is not found with the schoolName: " + schoolName);
        }
        School school = optionalSchool.get();
        return modelMapper.map(school, SchoolDto.class);
    }

    public SchoolUpdateDto updatePartOfSchoolBySchoolId
            (UUID schoolId, SchoolUpdateDto schoolUpdateDto) throws ResourceNotFoundException{

        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if(optionalSchool.isEmpty()){
            throw new ResourceNotFoundException("school is not found with the schoolId: " + schoolId);
        }
        School existingSchool = optionalSchool.get();
        existingSchool.setSchoolName(schoolUpdateDto.getSchoolName());
        existingSchool.setSchoolEmailAddress(schoolUpdateDto.getSchoolEmailAddress());
        existingSchool.setSchoolPhoneNumber(schoolUpdateDto.getSchoolPhoneNumber());
        School updatedSchool = schoolRepository.save(existingSchool);

        return modelMapper.map(updatedSchool, SchoolUpdateDto.class);
    }


    public void deleteSchoolBySchoolId(UUID schoolId) throws ResourceNotFoundException{

        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        if(optionalSchool.isEmpty()){
            throw new ResourceNotFoundException("school is not found with the schoolId: " + schoolId);
        }
        schoolRepository.deleteById(schoolId);
    }


    public List<SchoolDto> findAllSchools(){

        List<School> schools = schoolRepository.findAll();
        List<SchoolDto> schoolDtoList = schools.stream()
                                        .map(school -> modelMapper.map(school, SchoolDto.class))
                                        .collect(Collectors.toList());
        return schoolDtoList;
    }
}

