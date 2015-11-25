package com.tobiascode.client;

import com.tobiascode.server.EmployeeServiceBean;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Singleton
@Path("/")
public class TestController {
    @EJB
    private EmployeeServiceBean employeeService;

    @Path("/list")
    @GET
    public String listEmployees() {
        return employeeService.listEmployees();
    }

    @Path("/increase")
    @GET
    public void increaseSalary() {
        employeeService.increaseSalary(1L);
    }

    @Path("/test")
    @GET
    public String testService() {
        return "Up and running";
    }
}
