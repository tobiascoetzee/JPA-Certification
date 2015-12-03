package com.tobiascode.domain;

import javax.persistence.*;

@Entity
@IdClass(EmployeeId.class)
public class Employee {
    @Id
    protected int id;
    @Id
    protected String country;

    protected String name;

    @ManyToOne
    protected Department department;

    @Embedded
    private Address address;

    public Employee() {
    }

    public Employee(int id, String country, String name, Department department) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.department = department;
    }

    public Employee(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee [id=" + getId() + ", name=" + getName() + ", country=" + getCountry() + ", department="
                + department.getName() + ", country=" + department.getDepartmentId().getCountry() + "]";
    }

    @PrePersist
    private void PreChecks(){
        System.out.println("Doing prechecks before persisting employee");
    }
}
