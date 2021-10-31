package by.general.controller;

import by.general.entity.Employee;
import by.general.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MyController {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @GetMapping("/index")
    public String getStart(Model model) {

        try {
            List<Employee> foundEmployees = employeeRepository.findAll();
            System.out.println("FOUND: ");
            System.out.println(foundEmployees);
            model.addAttribute("foundEmployees", foundEmployees);
            return "index";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/create-employee")
    public String getCreateEmployee(Model model) {

        try {
            Employee employeeToCreate = new Employee();
            model.addAttribute("employeeToCreate", employeeToCreate);
            return "create-employee";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/create-employee")
    public String createEmployee(Employee employee) {
        try {
            employeeRepository.create(employee);
            return "redirect:/index";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        try {
            employeeRepository.deleteById(id);
            return "redirect:/index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/update-employee/{id}")
    public String getUpdateEmployee(Model model, @PathVariable Integer id) {
        try {
            Employee employee = employeeRepository.findById(id);
            model.addAttribute("employee", employee);
            return "update-employee";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/update-employee")
    public String updateEmployee(Employee employee) {
        try {
            employeeRepository.update(employee);
            return "redirect:/index";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/single-employee/{id}")
    public String getSingleEmployee(Model model, @PathVariable int id) {
        try {
            Employee employee = employeeRepository.findById(id);
            model.addAttribute("employee", employee);
            return "single-employee";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }
}
