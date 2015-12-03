package com.tobiascode.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Access(AccessType.FIELD)
public class DepartmentId implements Serializable {
    private String country;
    private int id;

    public DepartmentId(){}

    public DepartmentId(String country, int id)
    {
        this.country = country;
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object o) {
        return ((o instanceof DepartmentId) &&
                country.equals(((DepartmentId) o).getCountry()) &&
                id == ((DepartmentId) o).getId());
    }

    public int hashCode() {
        return country.hashCode() + id;
    }
}
