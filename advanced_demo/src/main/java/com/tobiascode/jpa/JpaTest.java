package com.tobiascode.jpa;

import javax.persistence.*;

import com.tobiascode.domain.*;

import java.util.HashMap;
import java.util.Map;

public class JpaTest {

    private EntityManager manager;

    public JpaTest(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        JpaTest test = new JpaTest(manager);

        // Set the static cache configuration
        manager.setProperty("javax.persistence.sharedCache.mode", "DISABLE_SELECTIVE");

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.createEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        EmployeeId employeeId = new EmployeeId("SA", 1);
        test.listEmployee(employeeId);

        manager.close();
        factory.close();

        System.out.println(".. done");
    }

    private void createEmployees() {
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
        if (numOfEmployees == 0) {
            DepartmentId departmentId = new DepartmentId("SA", 1);
            Department department = new Department(departmentId, "java");
            manager.persist(department);

            manager.persist(new Employee(1, "SA", "Jakab Gipsz", department));
            manager.persist(new Employee(2, "ZIM", "Captain Nemo", department));
            manager.persist(new PartTimeEmployee(3, "UK", "Sir Smith", department));
        }
    }

    private void listEmployee(EmployeeId employeeId) {
        // Set the dynamic cache configuration
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);

        Employee employee = manager.find(Employee.class, employeeId, properties);
        System.out.println(employee);
    }
}
