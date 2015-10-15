package com.tobiascode.client;

import com.tobiascode.domain.Department;
import com.tobiascode.domain.Employee;

import javax.persistence.EntityManager;
import java.util.logging.Logger;


public class DataInitialise {
    private Logger log = Logger.getLogger(this.getClass().getName());

    public void insertEmployees(EntityManager entityManager) {
        log.info("Checking to insert employees");
        int numOfEmployees = entityManager.createQuery("Select a From Employee a", Employee.class).getResultList().size();

        if (numOfEmployees == 0) {
            log.info("Inserting employees");
            Department department = entityManager.createQuery("Select a From Department a where a.name = 'java'", Department.class).getSingleResult();

            entityManager.persist(new Employee("Jakab Gipsz", department));
            entityManager.persist(new Employee("Captain Nemo", department));
        }
    }

    public void insertDepartment(EntityManager entityManager) {
        log.info("Checking to insert departments");
        int numOfDepartments = entityManager.createQuery("Select a From Department a", Department.class).getResultList().size();

        if (numOfDepartments == 0) {
            log.info("Inserting departments");
            entityManager.persist(new Department("java"));
            entityManager.persist(new Department("scala"));
            entityManager.persist(new Department("maven"));
        }
    }
}
