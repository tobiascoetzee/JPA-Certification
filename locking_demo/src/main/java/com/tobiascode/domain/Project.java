package com.tobiascode.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Inheritance
@DiscriminatorValue("P")
public class Project {
    @Id
    protected int id;

    @Version int version;

    protected String name;
    @ManyToMany
    protected Collection<Employee> employees = new ArrayList<Employee>();

    public int getId() {
        return id;
    }

    public void setId(int projectNo) {
        this.id = projectNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String projectName) {
        this.name = projectName;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (!getEmployees().contains(employee)) {
            getEmployees().add(employee);
        }
        if (!employee.getProjects().contains(this)) {
            employee.getProjects().add(this);
        }
    }

    public String toString() {
        return getClass().getSimpleName() +
                " no: " + getId() +
                ", name: " + getName();
    }
}
