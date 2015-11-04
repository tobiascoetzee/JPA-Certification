package com.tobiascode.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST)
    @OrderBy(value = "name DESC")
    private List<Employee> employees = new ArrayList<Employee>();

	@OneToMany(mappedBy="department")
	@MapKeyColumn(name="Cubicle_Id", nullable = true)
	private Map<String, Employee> employeesByCubicle;

    @ElementCollection
    @CollectionTable(name="Emp_Seniority")
    @MapKeyJoinColumn(name="Employee_Id")
    @Column(name="Seniority")
    private Map<Employee, Integer> seniorities;

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
}
