package com.abiorh.smartEdConnect.student.repository;

import com.abiorh.smartEdConnect.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID>
{
    Student findByEmail(String email);

    List<Student> findStudentsByFirstName(String firstName);

    List<Student> findStudentsByFirstNameContaining(String name);


    @Query("SELECT s.firstName, s.lastName FROM Student s WHERE s.email = ?1 ")
    List<Object[]> getFirstNameAndLastNameByEmailAddress(String email);

    Page<Student> findAll(Pageable pageable);

}
