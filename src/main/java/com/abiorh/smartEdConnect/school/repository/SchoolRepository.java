package com.abiorh.smartEdConnect.school.repository;

import com.abiorh.smartEdConnect.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {

    Optional<School> findSchoolBySchoolEmailAddress(String schoolEmailAddress);

    Optional<School> findSchoolBySchoolName(String schoolName);

    Optional<School> findSchoolBySchoolLicenceNumber(String schoolLicenceNumber);
}

