package com.tobiascode.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.tobiascode.domain.Employee;
import com.tobiascode.domain.Department;
import com.tobiascode.domain.Project;

public class JpaTest {

    private EntityManager manager;

    public JpaTest(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        JpaTest test = new JpaTest(manager);

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            //test.createEmployees();
            test.createEmployeesOnly();
            test.createProjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        test.listEmployees();
        test.listProjects();

        tx.begin();
        try {
            test.removeProjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        test.listEmployees();
        test.listProjects();

        manager.close();
        factory.close();

        System.out.println(".. done");
    }

    private void createProjects() {
        int numOfProjects = manager.createQuery("Select a From Project a", Project.class).getResultList().size();

        if (numOfProjects == 0) {
            Collection<Employee> employees = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
            manager.persist(new Project("Java9", employees));
            manager.persist(new Project("Scala3", employees));
        }
    }

    private void createEmployees() {
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
        if (numOfEmployees == 0) {
            Department department = new Department("java");
            manager.persist(department);

            manager.persist(new Employee("Jakab Gipsz", department));
            manager.persist(new Employee("Captain Nemo", department));
        }
    }

    private void createEmployeesOnly() {
        int numOfDepartments = manager.createQuery("Select a From Department a", Department.class).getResultList().size();
        if (numOfDepartments == 0) {
            Department department = new Department("java");

            manager.persist(new Employee("Jakab Gipsz", department));
            manager.persist(new Employee("Captain Nemo", department));
        }
    }

    private void removeProjects() {
        List<Project> resultList = manager.createQuery("Select a From Project a", Project.class).getResultList();
        System.out.println("num of projects:" + resultList.size());
        for (Project next : resultList) {
            manager.remove(next);
        }
    }

    private void listEmployees() {
        List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listProjects() {
        List<Project> resultList = manager.createQuery("Select a From Project a", Project.class).getResultList();
        System.out.println("num of projects:" + resultList.size());
        for (Project next : resultList) {
            System.out.println("next project: " + next);
        }
    }
}
