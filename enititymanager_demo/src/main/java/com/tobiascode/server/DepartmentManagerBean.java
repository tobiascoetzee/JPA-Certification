package com.tobiascode.server;

import com.tobiascode.domain.Department;
import com.tobiascode.domain.Employee;

import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.logging.Logger;

@Stateful
@LocalBean
public class DepartmentManagerBean {
    private Logger log = Logger.getLogger(this.getClass().getName());

    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private Department department;

    public void init(long departmentId) {
        department = entityManager.find(Department.class, departmentId);
    }

    public void changeName(String name) {
        log.info("Changing name from [" + department.getName() + "] to [" + name + "]");
        department.setName(name);
    }

    @Remove
    public void done() {

    }
}
