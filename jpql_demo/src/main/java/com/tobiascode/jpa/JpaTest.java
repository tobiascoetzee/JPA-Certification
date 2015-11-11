package com.tobiascode.jpa;

import java.util.List;

import javax.persistence.*;

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

//		test.showSelects();
        test.showJoins();
//        test.showWheres();
//        test.showScalar();
//        test.showOrderBy();
//        test.showAggregates();
//        test.updateStatement();
//        test.deleteStatement();

		manager.close();
        factory.close();

		System.out.println(".. done");
	}

    // Selects
	private void showSelects() {
        //Single-Value Association Path
		List<Department> resultList = manager.createQuery("Select e.department From Employee e", Department.class).getResultList();

        System.out.println("*--- Select Single-Value Association Path ---*");
		for (Department next : resultList) {
			System.out.println("next department: " + next);
		}

        // State Field Path
        List<String> nameList = manager.createQuery("select e.name from Employee e", String.class).getResultList();

        System.out.println("*--- Select State Field Path ---*");
        for (String next : nameList) {
            System.out.println("next name: " + next);
        }

        // Collection-Valued Association Path
        List<Project> projectList = manager.createQuery("select p from Employee e join e.projects p", Project.class).getResultList();

        System.out.println("*--- Select Collection-Valued Association Path ---*");
        for (Project next : projectList) {
            System.out.println("next project: " + next);
        }
	}

    // Joins
    private void showJoins() {
        // Cartesian product
        List<Department> resultList = manager.createQuery("Select d From Employee e, Department d", Department.class).getResultList();

        System.out.println("*--- Cartesian Product ---*");
        for (Department next : resultList) {
            System.out.println("next department: " + next);
        }

        // Inner Joins
        List<Department> innerList = manager.createQuery("Select d From Employee e join e.department d", Department.class).getResultList();

        System.out.println("*--- Inner Join ---*");
        for (Department next : innerList) {
            System.out.println("next department: " + next);
        }

        List<Department> innerPathList = manager.createQuery("Select e.department From Employee e", Department.class).getResultList();

        System.out.println("*--- Inner Join Path ---*");
        for (Department next : innerPathList) {
            System.out.println("next department: " + next);
        }

        List<Department> innerWhereList = manager.createQuery("Select d From Employee e, Department d where d = e.department", Department.class).getResultList();

        System.out.println("*--- Inner Join Where ---*");
        for (Department next : innerWhereList) {
            System.out.println("next department: " + next);
        }

        // Outer Joins
        // Returns only employees with a phone number
        List<Employee> outervsinnerList = manager.createQuery("Select e From Employee e join e.phones p", Employee.class).getResultList();
        // Returns all employess, even without a phone number
        List<Employee> outerList = manager.createQuery("Select e From Employee e left outer join e.phones p", Employee.class).getResultList();

        System.out.println("*--- Outer Join ---*");
        for (Employee next : outerList) {
            System.out.println("next employee: " + next);
        }

        // Fetch Joins
        List<Employee> fetchList = manager.createQuery("Select e From Employee e join fetch e.address a", Employee.class).getResultList();

        System.out.println("*--- Fetch Joins ---*");
        for (Employee next : fetchList) {
            System.out.println("next address: " + next.getAddress());
        }
    }

    private void showWheres(){
        // Where with Path
        TypedQuery<Employee> query = manager.createQuery("Select e From Employee e where e.department.name = :name", Employee.class);
        query.setParameter("name","Engineering");
        List<Employee> resultList = query.getResultList();

        System.out.println("*--- Where ---*");
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }

        // Any, All, Some
        List<Employee> anyList = manager.createQuery("Select e From Employee e where e.department = any(select d from Department d where d.employees.size > 5)", Employee.class).getResultList();

        System.out.println("*--- Any Operator ---*");
        for (Employee next : anyList) {
            System.out.println("next employee: " + next);
        }

        // Is Empty
        // SELECT m FROM Employee m WHERE (SELECT COUNT(e) FROM Employee e WHERE e.manager = m) > 0
        List<Employee> isemptyList = manager.createQuery("Select e From Employee e where e.directs is not empty", Employee.class).getResultList();

        System.out.println("*--- Is Empty Operator ---*");
        for (Employee next : isemptyList) {
            System.out.println("next employee: " + next);
        }

        // Member of
        Project project = manager.find(Project.class, 1);
        // SELECT e FROM Employee e WHERE :project IN (SELECT p FROM e.projects p)
        TypedQuery<Employee> queryMember = manager.createQuery("Select e From Employee e where :project member of e.projects", Employee.class);
        queryMember.setParameter("project", project);
        List<Employee> memberOfList = query.getResultList();

        System.out.println("*--- Member Of Operator ---*");
        for (Employee next : memberOfList) {
            System.out.println("next employee in project " + project.getName() + ": " + next);
        }

        // Polymorphic
        List<Project> polyList1 = manager.createQuery("select p from Project p where type(p) = DesignProject", Project.class).getResultList();

        System.out.println("*--- Polymorphic Type ---*");
        for (Project next : polyList1) {
            System.out.println("next project: " + next);
        }

        List<Project> polyList2 = manager.createQuery("select p from Project p where treat(p as QualityProject ).qa_rating > 4", Project.class).getResultList();

        System.out.println("*--- Polymorphic Treat ---*");
        for (Project next : polyList2) {
            System.out.println("next project: " + next);
        }
    }

    private void showScalar(){
        // Scalar functions
        int projectCount = manager.createQuery("Select size(e.projects) From Employee e", Integer.class).getSingleResult();

        System.out.println("*--- Scalar functions ---*");
        System.out.println("number of projects: " + projectCount);

        List<String> firstTwoLettersList = manager.createQuery("Select substring(e.name,1,2) From Employee e", String.class).getResultList();

        for (String firstTwoLetters : firstTwoLettersList) {
            System.out.println("employee first two letters: " + firstTwoLetters);
        }

        // Case expressions
        List<String> projectList = manager.createQuery("select case when type(p) = DesignProject then 'Development' when type(p) = QualityProject then 'QA' else 'Non-Development' end from Project p", String.class).getResultList();

        System.out.println("*--- Case Expression ---*");
        for (String next : projectList) {
            System.out.println("next project: " + next);
        }

        // Coalesce expressions
        List<String> employeeList = manager.createQuery("select coalesce(e.manager.name, 'No Manager') from Employee e left join e.manager", String.class).getResultList();

        System.out.println("*--- Coalesce Expression ---*");
        for (String next : employeeList) {
            System.out.println("next employee: " + next);
        }

        // Nullif expressions
        List<String> countDepartments = manager.createQuery("select nullif(d.name,'QA') from Department d", String.class).getResultList();

        System.out.println("*--- NullIf Expression ---*");
        for (String next : countDepartments) {
            System.out.println("next department: " + next);
        }
    }

    private void showOrderBy(){
        //Order By
        List<Employee> resultList = manager.createQuery("Select e From Employee e order by e.name", Employee.class).getResultList();

        System.out.println("*--- Order By Asc ---*");
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }

        List<Employee> resultListDesc = manager.createQuery("Select e From Employee e order by e.name desc", Employee.class).getResultList();

        System.out.println("*--- Order By Desc ---*");
        for (Employee next : resultListDesc) {
            System.out.println("next employee: " + next);
        }
    }

    private void showAggregates(){
        // Aggregate functions
        double avergateSalary = manager.createQuery("Select avg(e.salary) From Employee e", Double.class).getSingleResult();

        System.out.println("*--- Aggregate functions ---*");
        System.out.println("average salary for employees: " + avergateSalary);

        // Group By
        List<Tuple> averageSalaryPerDepartment = manager.createQuery("Select e.department.name, avg(e.salary) From Employee e group by e.department.name", Tuple.class).getResultList();

        System.out.println("*--- Group By ---*");
        for (Tuple next : averageSalaryPerDepartment) {
            System.out.println("average for " + next.get(0) + ": " + next.get(1));
        }

        // Group By
        List<Tuple> averageSalaryPerDepartmentAbove = manager.createQuery("Select e.department.name, avg(e.salary) From Employee e group by e.department.name having avg(e.salary) >= 50000.0", Tuple.class).getResultList();

        System.out.println("*--- Having ---*");
        for (Tuple next : averageSalaryPerDepartmentAbove) {
            System.out.println("average for " + next.get(0) + ": " + next.get(1));
        }
    }

    private void updateStatement(){
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        int numberUpdated = manager.createQuery("update Employee e set e.salary = 56000 where e.salary = 55000").executeUpdate();

        System.out.println("Number of records updated = " + numberUpdated);

        transaction.rollback();
    }

    private void deleteStatement(){
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        int numberUpdated = manager.createQuery("delete from Employee e where e.salary = 54000").executeUpdate();

        System.out.println("Number of records deleted = " + numberUpdated);

        transaction.rollback();
    }
}
