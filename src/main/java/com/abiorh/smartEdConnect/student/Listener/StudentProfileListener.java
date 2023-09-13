package com.abiorh.smartEdConnect.student.Listener;



import com.abiorh.smartEdConnect.student.entity.StudentProfile;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

public class StudentProfileListener {

    @PrePersist
    public void prePersist(StudentProfile studentProfile) {
        ZonedDateTime now = ZonedDateTime.now();
        studentProfile.setCreatedAt(now);
        studentProfile.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(StudentProfile studentProfile) {
        studentProfile.setUpdatedAt(ZonedDateTime.now());
    }
}

