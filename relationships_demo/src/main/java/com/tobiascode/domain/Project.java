package com.tobiascode.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Collection<Employee> employees;

    public Project() {
    }

    public Project(String name, Collection<Employee> employees) {
        setName(name);
        setEmployees(employees);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Employee> projectEmployees) {
        employees = projectEmployees;
    }

    @Override
    public String toString() {
        String employeesOnProject = "";
        for (Employee next : employees) {
            employeesOnProject = employeesOnProject + next;
        }

        return "Project [id=" + id + ", name=" + name + ", employees=[" + employeesOnProject + "]";
    }
}
