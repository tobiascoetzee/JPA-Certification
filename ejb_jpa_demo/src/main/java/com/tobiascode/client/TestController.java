package com.tobiascode.client;

import com.tobiascode.domain.Department;
import com.tobiascode.domain.Employee;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Singleton
@Path("/")
public class TestController {
    @PersistenceContext(name = "persistenceUnit")
    private EntityManager entityManager;

    @Path("/insert")
    @GET
    public String insertEmployees() {
        int numOfEmployees = entityManager.createQuery("Select a From Employee a", Employee.class).getResultList().size();

        if (numOfEmployees == 0) {
            Department department = new Department("java");
            entityManager.persist(department);

            entityManager.persist(new Employee("Jakab Gipsz", department));
            entityManager.persist(new Employee("Captain Nemo", department));
        }

        return "ok";
    }

    @Path("/list")
    @GET
    public String ListEmployees() {
        String employees = "Employees are: ";
        List<Employee> resultList = entityManager.createQuery("Select a From Employee a", Employee.class).getResultList();

        for (Employee next : resultList) {
            employees = employees + "; " + next;
        }

        return employees;
    }

    @Path("/test")
    @GET
    public String testService() {
        return "Up and running";
    }
}
