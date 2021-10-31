package by.general.repository;

import by.general.entity.Employee;
import by.general.utils.ConnectionUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;


public class EmployeeRepositoryTest extends TestCase {

    private Connection con;

    @Test
    public void testCreate() throws Exception {
        con = ConnectionUtils.getConnection();
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = new Employee();
        employee.setEmployeeId(10);
        employee.setFirstName("Bob");
        employee.setLastName("Bobson");
        employee.setDepartmentId(1);
        employee.setJobTitle("Java programmer");
        employee.setGender("M");
        employee.setBirthDate(Date.valueOf("2020-02-02"));
        employeeRepository.create(employee);
        int id = employee.getEmployeeId();
        Assert.assertNotNull(id);
        Employee newEmployee = employeeRepository.findById(id);
        Assert.assertEquals("Bob", newEmployee.getFirstName());
        Assert.assertEquals("Bobson", newEmployee.getLastName());
        Assert.assertEquals("Java programmer", newEmployee.getJobTitle());
    }

    @Test
    public void testFindById() {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        Employee employee = employeeRepository.findById(1);
        Assert.assertEquals("Victor", employee.getFirstName());
        Assert.assertEquals("Hromau", employee.getLastName());
    }
}