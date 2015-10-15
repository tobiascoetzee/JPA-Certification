package com.tobiascode.server;

import com.tobiascode.domain.Employee;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Stateless
@LocalBean
public class ApplicationTransactionBean {
    @PersistenceUnit(unitName = "persistenceUnit")
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @PostConstruct
    private void init(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    public String listEmployees() {
        String employees = "Employees are: ";
        List<Employee> resultList = entityManager.createQuery("Select a From Employee a", Employee.class).getResultList();

        if (resultList == null) {
            return "No employees found";
        }

        for (Employee next : resultList) {
            employees = employees + "; " + next;
        }

        return employees;
    }
}
