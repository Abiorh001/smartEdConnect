package com.abiorh.smartEdConnect.student.repository;

import com.abiorh.smartEdConnect.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>
{
    Student findByEmail(String email);
}
