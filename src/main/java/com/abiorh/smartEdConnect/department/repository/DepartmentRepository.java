package com.abiorh.smartEdConnect.department.repository;

import com.abiorh.smartEdConnect.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Department findDepartmentByDepartmentName(String departmentName);

    List<Department> findDepartmentBySchoolSchoolId(UUID schoolId);
}
