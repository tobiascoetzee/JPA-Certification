package com.tobiascode.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, Address> address;
	public static volatile CollectionAttribute<Employee, Project> projects;
	public static volatile SingularAttribute<Employee, Employee> manager;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile CollectionAttribute<Employee, Phone> phones;
	public static volatile SingularAttribute<Employee, Integer> id;
	public static volatile CollectionAttribute<Employee, Employee> directs;
	public static volatile SingularAttribute<Employee, Long> salary;
	public static volatile SingularAttribute<Employee, Department> department;
	public static volatile SingularAttribute<Employee, Date> startDate;

}

