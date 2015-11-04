package com.tobiascode.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "dep")
@NamedNativeQuery(name = "Department.ListAll", query = "select * from dep", resultClass = Department.class)
@SqlResultSetMapping(name = "OtherDepartment",
        entities = {@EntityResult(entityClass = Department.class,
                fields = {
                        @FieldResult(name = "id", column = "identifier"),
                        @FieldResult(name = "name", column = "OtherDepartment")
                })
        })
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    private List<Employee> employees = new ArrayList<Employee>();

    public Department() {
        super();
    }

    public Department(String name) {
        this.name = name;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }
}
