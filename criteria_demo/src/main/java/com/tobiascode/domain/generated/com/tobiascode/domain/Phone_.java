package com.tobiascode.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Phone.class)
public abstract class Phone_ {

	public static volatile SingularAttribute<Phone, String> number;
	public static volatile SingularAttribute<Phone, Long> id;
	public static volatile SingularAttribute<Phone, String> type;
	public static volatile SingularAttribute<Phone, Employee> employee;

}

