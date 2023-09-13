package com.abiorh.smartEdConnect.school.listener;

import com.abiorh.smartEdConnect.school.entity.School;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.ZonedDateTime;

public class SchoolListener {

    @PrePersist
    public void prePersist(School school){
        ZonedDateTime timeNow = ZonedDateTime.now();
        school.setCreatedAt(timeNow);
        school.setUpdatedAt(timeNow);
    }

    @PreUpdate
    public void preUpdate(School school){
        ZonedDateTime timeNow = ZonedDateTime.now();
        school.setUpdatedAt(timeNow);
    }
}
