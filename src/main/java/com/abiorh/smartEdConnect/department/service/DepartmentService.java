package com.abiorh.smartEdConnect.department.service;

import com.abiorh.smartEdConnect.department.dto.DepartmentDto;
import com.abiorh.smartEdConnect.department.entity.Department;
import com.abiorh.smartEdConnect.department.repository.DepartmentRepository;
import com.abiorh.smartEdConnect.globalConfig.customException.ResourceNotFoundException;
import com.abiorh.smartEdConnect.globalConfig.customException.UnauthorizedAccessException;
import com.abiorh.smartEdConnect.globalConfig.customException.UniqueDepartmentNameException;
import com.abiorh.smartEdConnect.school.dto.SchoolDto;
import com.abiorh.smartEdConnect.school.entity.School;
import com.abiorh.smartEdConnect.school.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final SchoolService schoolService;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper, SchoolService schoolService) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
        this.schoolService = schoolService;
    }

    public DepartmentDto saveNewDepartment(DepartmentDto departmentDto, UUID schoolId)
            throws UniqueDepartmentNameException, ResourceNotFoundException {

        if(departmentRepository.findDepartmentByDepartmentName(departmentDto.getDepartmentName()) != null){
            throw new UniqueDepartmentNameException("department name already registered");
        }
        SchoolDto schoolDto = schoolService.getSchoolBySchoolId(schoolId);
        if(schoolDto == null){
            throw new ResourceNotFoundException("school is not found with schoolId: " + schoolId);
        }
        Department department = modelMapper.map(departmentDto, Department.class);
        School school = modelMapper.map(schoolDto, School.class);
        department.setSchool(school);
        Department newDepartment = departmentRepository.save(department);
        return modelMapper.map(newDepartment, DepartmentDto.class);
    }

    public DepartmentDto findDepartmentByDepartmentId(UUID schoolId, UUID departmentId)
            throws ResourceNotFoundException, UnauthorizedAccessException {

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if(optionalDepartment.isEmpty())
            throw new ResourceNotFoundException("department is not found with departmentId: " + departmentId);
        Department department = optionalDepartment.get();

        SchoolDto schoolDto = schoolService.getSchoolBySchoolId(schoolId);
        School school = modelMapper.map(schoolDto, School.class);
        if(!school.getSchoolId().equals(department.getSchool().getSchoolId())){
            throw new UnauthorizedAccessException("you are not authorized to access this page");
        }
        return modelMapper.map(department, DepartmentDto.class);
    }


    public DepartmentDto updateDepartmentByDepartmentId(
            UUID schoolId,
            UUID departmentId,
            DepartmentDto updatedDepartmentDto)
            throws ResourceNotFoundException, UniqueDepartmentNameException, UnauthorizedAccessException {

        // Check for a duplicate department name
        if (departmentRepository.findDepartmentByDepartmentName(updatedDepartmentDto.getDepartmentName()) != null) {
            throw new UniqueDepartmentNameException("Department name already registered");
        }

        // Load the existing department
        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (optionalDepartment.isEmpty()) {
            throw new ResourceNotFoundException("Department is not found with departmentId: " + departmentId);
        }
        Department department = optionalDepartment.get();

        // Check authorization based on schoolId
        SchoolDto schoolDto = schoolService.getSchoolBySchoolId(schoolId);
        School school = modelMapper.map(schoolDto, School.class);
        if (!school.getSchoolId().equals(department.getSchool().getSchoolId())) {
            throw new UnauthorizedAccessException("You are not authorized to access this page");
        }

        // Update department name
        department.setDepartmentName(updatedDepartmentDto.getDepartmentName());

        // Save the updated department
        Department updatedDepartment = departmentRepository.save(department);

        return modelMapper.map(updatedDepartment, DepartmentDto.class);
    }


    public List<DepartmentDto> findAllDepartments(UUID schoolId)
            throws ResourceNotFoundException {
        // Fetch departments associated with the given schoolId
        List<Department> departments = departmentRepository.findDepartmentBySchoolSchoolId(schoolId);

        // Perform authorization check if no departments are found
        if (departments.isEmpty()) {
            throw new ResourceNotFoundException("No departments found for school with ID: " + schoolId);
        }

        // Map departments to DTOs
        List<DepartmentDto> departmentDtoList = departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());

        return departmentDtoList;
    }


    public void deleteDepartmentByDepartmentId(UUID schoolId, UUID departmentId)
            throws ResourceNotFoundException, UnauthorizedAccessException{

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if(optionalDepartment.isEmpty()){
            throw new ResourceNotFoundException("department is not found with departmentId: " + departmentId);
        }
        Department department = optionalDepartment.get();
        SchoolDto schoolDto = schoolService.getSchoolBySchoolId(schoolId);
        School school = modelMapper.map(schoolDto, School.class);
        if(!school.getSchoolId().equals(department.getSchool().getSchoolId())){
            throw new UnauthorizedAccessException("you are not authorized to access this page");
        }
        departmentRepository.deleteById(departmentId);
    }
}
