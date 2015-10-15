package com.tobiascode.server;

import com.tobiascode.domain.Department;
import com.tobiascode.domain.Employee;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ContainerTransactionBean {
    private Logger log = Logger.getLogger(this.getClass().getName());

    @PersistenceContext(unitName = "persistenceUnit")
    private EntityManager entityManager;

    public void linkEmployeeToDepartment(long employeeId, long departmentId) {
        log.info("Finding department and employee enities: " + employeeId + ":" + departmentId);
        Department department = entityManager.find(Department.class, departmentId);
        Employee employee = entityManager.find(Employee.class, employeeId);

        log.info("Linking employee to department");
        employee.setDepartment(department);
    }

    public void updateEmployee(Employee employee){
        Employee theEmployee = entityManager.merge(employee);
        theEmployee.setName("Admiral Stuart");
    }
}
