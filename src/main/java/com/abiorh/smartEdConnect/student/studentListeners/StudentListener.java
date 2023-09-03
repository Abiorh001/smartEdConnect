package com.abiorh.smartEdConnect.student.studentListeners;



import com.abiorh.smartEdConnect.student.model.Student;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

public class StudentListener {

    @PrePersist
    public void prePersist(Student student) {
        ZonedDateTime now = ZonedDateTime.now();
        student.setCreatedAt(now);
        student.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(Student student) {
        student.setUpdatedAt(ZonedDateTime.now());
    }
}

