package com.tobiascode.server;

import com.tobiascode.domain.Employee;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmployeeServiceBean {
    @PersistenceContext(name = "persistenceUnit")
    private EntityManager entityManager;

    public String listEmployees(){
        String employees = "Employees are: ";
        List<Employee> resultList = entityManager.createQuery("Select a From Employee a", Employee.class).setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT).getResultList();

        for (Employee next : resultList) {
            //entityManager.lock(next, LockModeType.OPTIMISTIC);
            employees = employees + "; " + next;
        }

        return employees;
    }

    public void increaseSalary(long increaseAmount){
        List<Employee> resultList = entityManager.createQuery("Select a From Employee a", Employee.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();

        for (Employee next : resultList) {
            next.setSalary(next.getSalary() + increaseAmount);
        }
    }
}
