package com.tobiascode.jpa;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;

import com.tobiascode.domain.Employee;
import com.tobiascode.domain.Department;

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

//        test.listEmployeesJPQL();
//        test.listEmployeesNamedJPQL();
//        test.listEmployeesSQL();
//        test.listDepartmentsNamedSQL();
//        test.listDepartmentsSqlResultSetMapping();
        test.listEmployeesCriteria();

        manager.close();
        factory.close();

        System.out.println(".. done");
    }

    private void listEmployeesJPQL() {
        TypedQuery<Employee> employees = manager.createQuery("select e from Department d join d.employees e where d.name = :name", Employee.class);

        employees.setParameter("name", "java");

        List<Employee> resultList = employees.getResultList();

        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEmployeesNamedJPQL() {
        TypedQuery<Employee> employees = manager.createNamedQuery("Employee.ListAll", Employee.class);

        List<Employee> resultList = employees.getResultList();

        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listEmployeesSQL() {
        Query employees = manager.createNativeQuery("select e.* from dep d inner join emp e on d.id = e.department_id where d.name = ?1", Employee.class);

        employees.setParameter(1, "groovy");

        List<Employee> resultList = (List<Employee>) employees.getResultList();

        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void listDepartmentsNamedSQL() {
        TypedQuery<Department> departments = manager.createNamedQuery("Department.ListAll", Department.class);

        List<Department> resultList = departments.getResultList();

        System.out.println("num of departments:" + resultList.size());
        for (Department next : resultList) {
            System.out.println("next departments: " + next);
        }
    }

    private void listDepartmentsSqlResultSetMapping() {
        Query departments = manager.createNativeQuery("select id as identifier, name as departmentname from dep", "OtherDepartment");

        List<Department> resultList = departments.getResultList();

        System.out.println("num of departments:" + resultList.size());
        for (Department next : resultList) {
            System.out.println("next departments: " + next);
        }
    }

    private void listEmployeesCriteria() {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Department> departmentRoot = criteriaQuery.from(Department.class);
        Join<Department, Employee> employeeJoin = departmentRoot.join("employees");

        ParameterExpression<String> departmentName = criteriaBuilder.parameter(String.class, "name");
        criteriaQuery.select(employeeJoin).where(criteriaBuilder.equal(departmentRoot.get("name"), departmentName));

        TypedQuery<Employee> employees = manager.createQuery(criteriaQuery);

        employees.setParameter("name", "scala");

        List<Employee> resultList = employees.getResultList();

        System.out.println("num of employees:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
    }

    private void createEmployees() {
        int numOfEmployees = manager.createQuery("Select a From Employee a", Employee.class).getResultList().size();
        if (numOfEmployees == 0) {
            Department department = new Department("java");
            manager.persist(department);

            Department department2 = new Department("scala");
            manager.persist(department2);

            Department department3 = new Department("groovy");
            manager.persist(department3);

            manager.persist(new Employee("Mr White", department));
            manager.persist(new Employee("Mr Blue", department2));
            manager.persist(new Employee("Mr Black", department3));
            manager.persist(new Employee("Mr Yellow", department2));
            manager.persist(new Employee("Mr Red", department));
            manager.persist(new Employee("Mr Orange", department2));
        }
    }
}
