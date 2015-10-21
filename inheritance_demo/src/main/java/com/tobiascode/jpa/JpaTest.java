package com.tobiascode.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.tobiascode.domain.ContractEmployee;
import com.tobiascode.domain.Employee;
import com.tobiascode.domain.FullTimeEmployee;
import com.tobiascode.domain.PartTimeEmployee;

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
            test.createEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        test.listEmployees();

        manager.close();
        factory.close();

        System.out.println(".. done");
    }

    private void createEmployees() {
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();

        if (numOfEmployees == 0) {
            System.out.println("Creating employees");

            ContractEmployee contractEmployee = new ContractEmployee();
            contractEmployee.setName("Mr Yellow");
            contractEmployee.setStartDate(new Date(2015,9,13));
            contractEmployee.setTerm(60);
            contractEmployee.setDailyRate(100);
//            contractEmployee.setId(1l);

            FullTimeEmployee fullTimeEmployee = new FullTimeEmployee();
            fullTimeEmployee.setName("Mr Blue");
            fullTimeEmployee.setStartDate(new Date(2013,8,1));
            fullTimeEmployee.setSalary(10000);
            fullTimeEmployee.setPension(100);
//            fullTimeEmployee.setId(2l);

            PartTimeEmployee partTimeEmployee = new PartTimeEmployee();
            partTimeEmployee.setName("Mr White");
            partTimeEmployee.setStartDate(new Date(2015,4,1));
            partTimeEmployee.setHourlyRate(100);
            partTimeEmployee.setVacation(15);
//            partTimeEmployee.setId(3l);

            manager.persist(contractEmployee);
            manager.persist(fullTimeEmployee);
            manager.persist(partTimeEmployee);
        }
    }

    private void listEmployees() {
        List<Employee> resultList = manager.createQuery("Select a From Employee a", Employee.class).getResultList();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }
}
