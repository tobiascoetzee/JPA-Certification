package com.tobiascode.domain;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

@Entity
public class PartTimeEmployee extends Employee{
    public PartTimeEmployee() {
    }

    public PartTimeEmployee(int id, String country, String name, Department department) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.department = department;
    }

    @PrePersist
    private void PreChecks(){
        System.out.println("Doing ***prechecks*** part time before persisting employee");
    }
}
