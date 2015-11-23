package com.tobiascode.jpa;

import com.tobiascode.domain.*;
import com.tobiascode.domain.Department_;
import com.tobiascode.domain.Employee_;
import com.tobiascode.domain.Project_;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public class JpaTest {

    private EntityManager manager;

    public JpaTest(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();

        JpaTest test = new JpaTest(manager);
        test.listEmployees();
        test.listEmployeeNames();
        test.listEmployeesProjects();
        test.listSelectedEmployees();
        test.listEvenMoreSelectedEmployees();
        test.listSpecificEmployees();
        test.listEvenMoreSelectedEmployeesWithParameters();
        test.listOrderedEmployees();
        test.listEmployeesPerDepartment();
        test.listEmployeesMetamodel();
        test.listSpecificEmployeesMetamodel();
        test.listEmployeesProjectsMetamodel();

        manager.close();
        factory.close();

        System.out.println(".. done");
    }

    private void listEmployees() {
        // SELECT e FROM Employee e
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot);

        List<Employee> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEmployeeNames() {
        // SELECT e.name FROM Employee e

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot.<String>get("name"));

        List<String> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees in QA:" + resultList.size());
        for (String next : resultList) {
            System.out.println("next employee name: " + next);
        }
    }

    private void listEmployeesProjects() {
        // SELECT p FROM Employee e JOIN e.projects p

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Join<Employee, Project> projectJoin = employeeRoot.join("projects");
        criteriaQuery.select(projectJoin);

        List<Project> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Project next : resultList) {
            System.out.println("next project: " + next);
        }
    }

    private void listSelectedEmployees() {
        // SELECT e.name FROM Employee e WHERE e.department.name = 'QA'

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot.<String>get("name"));
        criteriaQuery.where(criteriaBuilder.equal(employeeRoot.get("department").get("name"),"QA"));

        List<String> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (String next : resultList) {
            System.out.println("next employee name: " + next);
        }
    }

    private void listEvenMoreSelectedEmployees() {
        // SELECT e FROM Employee e WHERE e.department.name = 'QA' OR e.salaray >= 55000

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate qaDepartment = criteriaBuilder.equal(employeeRoot.get("department").get("name"),"QA");
        Predicate salary = criteriaBuilder.ge(employeeRoot.<Long>get("salary"), 55000L);

        criteriaQuery.select(employeeRoot);
        criteriaQuery.where(criteriaBuilder.or(qaDepartment,salary));

        List<Employee> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listSpecificEmployees() {
        // SELECT e FROM Employee e WHERE e.department.name = 'QA' AND e.salaray >= 55000

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate qaDepartment = criteriaBuilder.equal(employeeRoot.get("department").get("name"),"QA");
        Predicate salary = criteriaBuilder.ge(employeeRoot.<Long>get("salary"), 55000L);

        List<Predicate> andList = new ArrayList<Predicate>();
        andList.add(qaDepartment);
        andList.add(salary);

        criteriaQuery.select(employeeRoot);
        criteriaQuery.where(andList.toArray(new Predicate[0]));
        //criteriaQuery.where(qaDepartment,salary);

        List<Employee> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEvenMoreSelectedEmployeesWithParameters() {
        // SELECT e FROM Employee e WHERE e.department.name = :name

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        ParameterExpression<String> name = criteriaBuilder.parameter(String.class,"name");
        criteriaQuery.select(employeeRoot).where(criteriaBuilder.equal(employeeRoot.get("department").get("name"), name));

        TypedQuery<Employee> employeeTypedQuery = manager.createQuery(criteriaQuery);
        employeeTypedQuery.setParameter("name", "QA");
        List<Employee> resultList = employeeTypedQuery.getResultList();

        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listOrderedEmployees() {
        // SELECT e FROM Employee e order by e.name DESC

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.select(employeeRoot).orderBy(criteriaBuilder.desc(employeeRoot.get("name")));

        List<Employee> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEmployeesPerDepartment() {
        // SELECT e.department, count(e) FROM Employee e GROUP BY e.department

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery.multiselect(employeeRoot.get("department"),criteriaBuilder.count(employeeRoot));
        criteriaQuery.groupBy(employeeRoot.get("department"));

        List<Tuple> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Tuple next : resultList) {
            System.out.println("next department: " + next.get(0) + " - " + next.get(1));
        }
    }

    private void listEmployeesMetamodel() {
        // SELECT p FROM Employee e JOIN e.projects p

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);


        Metamodel metamodel = manager.getMetamodel();
        EntityType<Employee> employeeEntityType = metamodel.entity(Employee.class);
        CollectionAttribute<Employee, Project> projectCollectionAttribute = (CollectionAttribute<Employee, Project>) employeeEntityType.getCollection("projects");

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Join<Employee, Project> projectJoin = employeeRoot.join(projectCollectionAttribute, JoinType.INNER);
        criteriaQuery.select(projectJoin);

        List<Project> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Project next : resultList) {
            System.out.println("next project: " + next);
        }
    }

    private void listSpecificEmployeesMetamodel() {
        // SELECT e.name FROM Employee e WHERE e.department.name = 'QA' AND e.salaray >= 55000

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Predicate qaDepartment = criteriaBuilder.equal(employeeRoot.get(Employee_.department).get(Department_.name), "QA");
        Predicate salary = criteriaBuilder.greaterThanOrEqualTo(employeeRoot.get(Employee_.salary), 55000L);

        List<Predicate> andList = new ArrayList<Predicate>();
        andList.add(qaDepartment);
        andList.add(salary);

        criteriaQuery.select(employeeRoot).where(criteriaBuilder.and(andList.toArray(new Predicate[0])));

        List<Employee> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEmployeesProjectsMetamodel() {
        // SELECT p FROM Employee e JOIN e.projects p

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Join<Employee, Project> projectJoin = employeeRoot.join(Employee_.projects, JoinType.INNER);
        criteriaQuery.select(projectJoin.get(Project_.name));

        List<String> resultList = manager.createQuery(criteriaQuery).getResultList();
        System.out.println("num of employees:" + resultList.size());
        for (String next : resultList) {
            System.out.println("next project: " + next);
        }
    }
}
