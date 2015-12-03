package com.tobiascode.domain;

import com.tobiascode.listeners.DebugListener;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@EntityListeners({DebugListener.class})
@Cacheable(false) // Don't cache this entity
public class Department {
    @EmbeddedId
    private DepartmentId departmentId;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumns({
            @JoinColumn(name = "Department_Id", referencedColumnName = "Id"),
            @JoinColumn(name = "Department_Country", referencedColumnName = "Country"),
    })
    private List<Employee> employees = new ArrayList<Employee>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name="road")),
            @AttributeOverride(name = "city", column = @Column(name="town"))
    })
    private Address address;

    public Department() {
        super();
    }

    public Department(DepartmentId departmentId, String name) {
        this.name = name;
        this.departmentId = departmentId;
    }

    public DepartmentId getDepartmentId(){ return departmentId;}

    public int getId() {
        return departmentId.getId();
    }

    public String getCountry() {
        return departmentId.getCountry();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
