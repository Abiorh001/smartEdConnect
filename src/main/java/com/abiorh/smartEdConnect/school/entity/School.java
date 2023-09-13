package com.abiorh.smartEdConnect.school.entity;

import com.abiorh.smartEdConnect.department.entity.Department;
import com.abiorh.smartEdConnect.school.listener.SchoolListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(SchoolListener.class)
public class School {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID schoolId;

    @Column(unique = true)
    private String schoolName;

    private String schoolPhoneNumber;

    @Column(unique = true)
    private String schoolEmailAddress;

    private Integer schoolYearOfEstablishment;

    @Column(unique = true)
    private String schoolLicenceNumber;

    private String schoolLicenceCertificateUrl;

    private String schoolLogoUrl;

    private String schoolStreetAddress;

    private String schoolCity;

    private String schoolState;

    private String schoolPostalCode;

    private String schoolCountry;

    @OneToMany(mappedBy = "school")
    private Set<Department> departments = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private ZonedDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private ZonedDateTime updatedAt;


}
