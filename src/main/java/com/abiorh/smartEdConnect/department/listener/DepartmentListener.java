package com.abiorh.smartEdConnect.department.listener;

import com.abiorh.smartEdConnect.department.entity.Department;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.ZonedDateTime;

public class DepartmentListener {

    @PrePersist
    public void prePersist(Department department){
        ZonedDateTime timeNow = ZonedDateTime.now();
        department.setCreatedAt(timeNow);
        department.setUpdatedAt(timeNow);
    }

    @PreUpdate
    public void preUpdate(Department department){
        ZonedDateTime timeNow = ZonedDateTime.now();
        department.setUpdatedAt(timeNow);
    }
}
