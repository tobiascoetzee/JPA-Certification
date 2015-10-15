package com.tobiascode.client;

import com.tobiascode.domain.Department;
import com.tobiascode.domain.Employee;
import com.tobiascode.server.ApplicationTransactionBean;
import com.tobiascode.server.ContainerTransactionBean;
import com.tobiascode.server.DepartmentManagerBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.*;
import javax.transaction.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Path("/")
@TransactionManagement(TransactionManagementType.BEAN)
public class TestController {
    private Logger log = Logger.getLogger(this.getClass().getName());

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private ContainerTransactionBean containerTransactionBean;

    @EJB
    private ApplicationTransactionBean applicationTransactionBean;

    @EJB
    private DepartmentManagerBean departmentManagerBean;

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        try {
            userTransaction.begin();

            initData();

            userTransaction.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (javax.transaction.RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        DataInitialise init = new DataInitialise();
        init.insertDepartment(entityManager);
        init.insertEmployees(entityManager);
    }

    private void initDepartmentManager() {
        Department department = entityManager.createQuery("Select a From Department a where a.name = 'scala'", Department.class).getSingleResult();
        departmentManagerBean.init(department.getId());
    }

    @Path("/list")
    @GET
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

    @Path("/listfrombean")
    @GET
    public String listEmployeesFromBean() {
        return applicationTransactionBean.listEmployees();
    }

    @Path("/link")
    @GET
    public String linkEmployees() {
        Department department = entityManager.createQuery("Select a From Department a where a.name = 'scala'", Department.class).getSingleResult();
        Employee employee = entityManager.createQuery("Select a From Employee a where a.name = 'Captain Nemo'", Employee.class).getSingleResult();

        containerTransactionBean.linkEmployeeToDepartment(employee.getId(), department.getId());

        return listEmployees();
    }

    @Path("/rename")
    @GET
    public String renameDepartment() {
        initDepartmentManager();

        departmentManagerBean.changeName("groovy");

        return listEmployees();
    }

    @Path("/done")
    @GET
    public String doneWithExtended() {
        departmentManagerBean.done();

        return listEmployees();
    }

    @Path("/employee")
    @GET
    public String listEmployee() {
        Employee employee = entityManager.createQuery("Select a From Employee a where a.name = 'Captain Nemo'", Employee.class).getSingleResult();

        containerTransactionBean.updateEmployee(employee);

        return listEmployees();
    }
}
